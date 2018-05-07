package lib.kalu.permission.processor;

import com.google.auto.service.AutoService;

import java.io.IOException;
import java.io.Writer;
import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

import lib.kalu.permission.annotation.*;

/**
 * description: 注解拦截处理, compile 'com.google.auto.service:auto-service:1.0-rc4'
 * created by kalu on 2017/12/16 15:41
 */
@AutoService(Processor.class)
public final class AnnotationProcessor extends AbstractProcessor {

    private final HashMap<String, AnnotationGenerate> map = new HashMap<>();
    private Elements mElements;
    private Filer mFiler;

    /**********************************************************************************************/

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        this.mElements = processingEnv.getElementUtils();
        this.mFiler = processingEnv.getFiler();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        map.clear();
        if (!isAnnotatedWithClass(roundEnv, PermissionMulitSync.class)) return false;
        if (!isAnnotatedWithMethod(roundEnv, PermissionDenied.class)) return false;
        if (!isAnnotatedWithMethod(roundEnv, PermissionSucc.class)) return false;
        if (!isAnnotatedWithMethod(roundEnv, PermissionFail.class)) return false;
        if (!isAnnotatedWithMethod(roundEnv, PermissionAgain.class)) return false;

        Writer writer = null;
        for (AnnotationGenerate info : map.values()) {
            try {
                JavaFileObject file = mFiler.createSourceFile(info.getProxyName(), info.getElement());
                writer = file.openWriter();
                writer.write(info.generateClassName());
                writer.flush();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (writer != null) {
                    try {
                        writer.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        return true;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> set = new HashSet<>(16);
        set.add(PermissionSucc.class.getCanonicalName());
        set.add(PermissionFail.class.getCanonicalName());
        set.add(PermissionAgain.class.getCanonicalName());
        set.add(PermissionMulitSync.class.getCanonicalName());
        set.add(PermissionDenied.class.getName());

        return set;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    /**********************************************************************************************/

    private boolean isAnnotatedWithClass(RoundEnvironment roundEnv, Class<? extends Annotation> clazz) {
        Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(clazz);
        for (Element element : elements) {
            if (isValid(element)) {
                return false;
            }
            TypeElement typeElement = (TypeElement) element;
            String typeName = typeElement.getQualifiedName().toString();
            AnnotationGenerate info = map.get(typeName);
            if (info == null) {
                info = new AnnotationGenerate(mElements, typeElement);
                map.put(typeName, info);
            }

            Annotation annotation = element.getAnnotation(clazz);
            if (annotation instanceof PermissionMulitSync) {
                String[] permissions = ((PermissionMulitSync) annotation).permission();
                int[] value = ((PermissionMulitSync) annotation).value();
                if (permissions.length != value.length) {
                    error(element, "permissions's length not equals value's length");
                    return false;
                }
                info.syncPermissions.put(value, permissions);
            } else {
                error(element, "%s not support.", element);
                return false;
            }
        }

        return true;
    }

    private boolean isAnnotatedWithMethod(RoundEnvironment roundEnv, Class<? extends Annotation> clazz) {
        Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(clazz);
        for (Element element : elements) {
            if (isValid(element)) {
                return false;
            }
            ExecutableElement method = (ExecutableElement) element;
            TypeElement typeElement = (TypeElement) method.getEnclosingElement();
            String typeName = typeElement.getQualifiedName().toString();
            AnnotationGenerate info = map.get(typeName);
            if (info == null) {
                info = new AnnotationGenerate(mElements, typeElement);
                map.put(typeName, info);
            }

            int size = method.getParameters().size();
            Annotation annotation = method.getAnnotation(clazz);
            String methodName = method.getSimpleName().toString();
            if (annotation instanceof PermissionSucc) {
                int[] value = ((PermissionSucc) annotation).value();
                if (value.length > 1 || size == 1) {
                    info.grantedMap.put(methodName, value);
                } else {
                    info.singleGrantMap.put(value[0], methodName);
                }
            } else if (annotation instanceof PermissionFail) {
                int[] value = ((PermissionFail) annotation).value();
                if (value.length > 1 || size == 1) {
                    info.deniedMap.put(methodName, value);
                } else {
                    info.singleDeniedMap.put(value[0], methodName);
                }
            } else if (annotation instanceof PermissionAgain) {
                int[] value = ((PermissionAgain) annotation).value();
                if (value.length > 1 || size == 1) {
                    info.rationaleMap.put(methodName, value);
                } else {
                    info.singleRationaleMap.put(value[0], methodName);
                }
            }else if (annotation instanceof PermissionDenied) {
                int[] value = ((PermissionDenied) annotation).value();
                if (value.length > 1 || size == 2) {
                    info.nonRationaleMap.put(methodName, value);
                } else {
                    info.singleNonRationaleMap.put(value[0], methodName);
                }
            } else {
                error(method, "%s not support.", method);
                return false;
            }
        }

        return true;
    }

    private boolean isValid(Element element) {
        if (element.getModifiers().contains(Modifier.ABSTRACT) || element.getModifiers().contains
                (Modifier.PRIVATE)) {
            error(element, "%s must could not be abstract or private");
            return true;
        }
        return false;
    }

    private void error(Element element, String message, Object... args) {
        if (args.length > 0) {
            message = String.format(message, args);
        }
        processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, message, element);
    }
}
