package lib.kalu.permission.processor;

import java.util.HashMap;
import java.util.Map;

import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;

/**
 * description: Java代码自动生成
 * created by kalu on 2017/12/16 15:39
 */
final class AnnotationGenerate {

    private final String NAME = "$$OnAnnotationChangeListener";

    private final String packageName;
    private final TypeElement element;
    private final String proxyName;
    // methodName -> requestCodes
    final Map<String, int[]> grantedMap = new HashMap<>();
    final Map<String, int[]> deniedMap = new HashMap<>();
    final Map<String, int[]> rationaleMap = new HashMap<>();
    final Map<String, int[]> nonRationaleMap = new HashMap<>();
    // requestCode -> methodName
    final Map<Integer, String> singleGrantMap = new HashMap<>();
    final Map<Integer, String> singleDeniedMap = new HashMap<>();
    final Map<Integer, String> singleRationaleMap = new HashMap<>();
    final Map<Integer, String> singleNonRationaleMap = new HashMap<>();
    // sync request
    final Map<int[], String[]> syncPermissions = new HashMap<>(1);

    private int firstRequestCode;
    private String firstRequestPermission;

    AnnotationGenerate(Elements elements, TypeElement typeElement) {
        this.element = typeElement;
        packageName = elements.getPackageOf(typeElement).getQualifiedName().toString();
        String className = getClassName(typeElement, packageName);
        proxyName = className + NAME;
    }

    String getProxyName() {
        return proxyName;
    }

    TypeElement getElement() {
        return element;
    }

    String generateClassName() {

        final StringBuilder builder = new StringBuilder();
        builder.append("package ").append(packageName).append(";\n\n")
                .append("import lib.kalu.permission.core.PermissionManager;\n")
                .append("import lib.kalu.permission.core.listener.OnAnnotationChangeListener;\n")
                .append("import android.content.Intent;\n\n")
                .append("public class ").append(proxyName).append(" implements ").append
                ("OnAnnotationChangeListener").append
                ("<").append(element.getSimpleName()).append("> {\n");

        // 申请权限成功回调
        generateSuccMethod(builder);
        // 申请权限失败回调
        generateFailMethod(builder);
        // 第二次提醒, 用户没有点击拒绝提示, 系统默认提示框
        generateAgainMethod(builder);
        // 第二次提醒, 用户已经点击拒绝提示, 默认调转权限设置页面, 国产机型, 需要做适配
        generateDeniedMethod(builder);
        // 异步多个请求
        generateRequestSyncMethod(builder);
        builder.append("}\n");

        return builder.toString();
    }

    private void generateRequestSyncMethod(StringBuilder builder) {
        if (builder == null) {
            return;
        }

        builder.append("@Override\n").append("public void requestSync(").append
                (element.getSimpleName()).append(" object) {\n")
                .append("PermissionManager.requestPermission(object, \"").append(firstRequestPermission)
                .append("\", ").append(firstRequestCode).append(");\n")
                .append("}");
    }

    private void generateDeniedMethod(StringBuilder builder) {
        if (builder == null) {
            return;
        }

        builder.append("@Override\n").append("public void onDenied(").append(element.getSimpleName())
                .append(" object, int code, Intent intent) {\n")
                .append("switch(code) {");

        for (Map.Entry<String, int[]> entry : nonRationaleMap.entrySet()) {
            String methodName = entry.getKey();
            int[] values = entry.getValue();
            for (int value : values) {
                builder.append("case ").append(value).append(":\n");
                if (singleNonRationaleMap.containsKey(value)) {
                    singleNonRationaleMap.remove(value);
                }
            }
            builder.append("{").append("object.").append(methodName).append("(code, intent);break;}\n");
        }

        for (Map.Entry<Integer, String> entry : singleNonRationaleMap.entrySet()) {
            Integer integer = entry.getKey();
            builder.append("case ").append(integer).append(":{ object.").append(entry.getValue()).append
                    ("(intent);break;}");
        }

        builder.append("default:\nbreak;\n").append("}\n}\n\n");
    }

    private void generateAgainMethod(StringBuilder builder) {
        if (null == builder) return;

        builder.append("@Override\n").append("public void onAgain(").append(element.getSimpleName())
                .append(" object, int code) {\n")
                .append("switch(code) {");
        for (Map.Entry<String, int[]> entry : rationaleMap.entrySet()) {
            String methodName = entry.getKey();
            int[] ints = entry.getValue();
            for (int requestCode : ints) {
                builder.append("case ").append(requestCode).append(":\n{");
                builder.append("object.").append(methodName).append("(").append(requestCode).append(");\n");
                builder.append("break;}\n");
                if (singleRationaleMap.containsKey(requestCode)) {
                    singleRationaleMap.remove(requestCode);
                }
            }
        }

        for (Map.Entry<Integer, String> entry : singleRationaleMap.entrySet()) {
            int requestCode = entry.getKey();
            builder.append("case ").append(requestCode).append(": {\n")
                    .append("object.").append(entry.getValue()).append("();\nbreak;\n}");
        }

        builder.append("default:\nbreak;\n").append("}\n}\n\n");
    }

    /**
     * 申请权限失败
     */
    private void generateFailMethod(StringBuilder builder) {
        if (builder == null) {
            return;
        }

        builder.append("@Override\n").append("public void onFail(").append(element.getSimpleName())
                .append(" object, int code) {\n")
                .append("switch(code) {");
        for (Map.Entry<String, int[]> entry : deniedMap.entrySet()) {
            String methodName = entry.getKey();
            int[] ints = entry.getValue();
            for (int requestCode : ints) {
                builder.append("case ").append(requestCode).append(":\n{");
                builder.append("object.").append(methodName).append("(").append(requestCode).append(");\n");
                // judge whether need write request permission method
                addSyncRequestPermissionMethod(builder, requestCode);
                builder.append("break;}\n");
                if (singleDeniedMap.containsKey(requestCode)) {
                    singleDeniedMap.remove(requestCode);
                }
            }
        }

        for (Map.Entry<Integer, String> entry : singleDeniedMap.entrySet()) {
            int requestCode = entry.getKey();
            builder.append("case ").append(requestCode).append(": {\n")
                    .append("object.").append(entry.getValue()).append("();\nbreak;\n}");
        }

        builder.append("default:\nbreak;\n").append("}\n}\n\n");
    }

    /**
     * 申请权限成功
     */
    private void generateSuccMethod(StringBuilder builder) {
        if (builder == null) {
            return;
        }

        builder.append("@Override\n").append("public void onSucc(").append(element.getSimpleName())
                .append(" object, int code) {\n")
                .append("switch(code) {");
        for (Map.Entry<String, int[]> entry : grantedMap.entrySet()) {
            String methodName = entry.getKey();
            int[] ints = entry.getValue();
            for (int requestCode : ints) {
                builder.append("case ").append(requestCode).append(":\n{");
                builder.append("object.").append(methodName).append("(").append(requestCode).append(");\n");
                // judge whether need write request permission method
                addSyncRequestPermissionMethod(builder, requestCode);
                builder.append("break;}\n");
                if (singleGrantMap.containsKey(requestCode)) {
                    singleGrantMap.remove(requestCode);
                }
            }
        }

        for (Map.Entry<Integer, String> entry : singleGrantMap.entrySet()) {
            int requestCode = entry.getKey();
            builder.append("case ").append(requestCode).append(": {\n")
                    .append("object.").append(entry.getValue()).append("();\nbreak;\n}");
        }

        builder.append("default:\nbreak;\n").append("}\n}\n\n");
    }

    private void addSyncRequestPermissionMethod(StringBuilder builder, int targetRequestCode) {
        // syncPermissions size is 1
        for (Map.Entry<int[], String[]> entry : syncPermissions.entrySet()) {
            int[] requestCodes = entry.getKey();
            String[] permissions = entry.getValue();
            int length = requestCodes.length;
            // when syncRequestPermission size is 1
            if (length == 1) {
                firstRequestCode = requestCodes[0];
                firstRequestPermission = permissions[0];
            } else {
                // when syncRequestPermission size bigger than 1
                for (int i = 0; i < length - 1; i++) {
                    if (i == 0) {
                        firstRequestCode = requestCodes[0];
                        firstRequestPermission = permissions[0];
                    }
                    if (requestCodes[i] == targetRequestCode) {
                        builder.append("PermissionManager.requestPermission(object,\"").append(permissions[i +
                                1]).append("\",").append(requestCodes[i + 1]).append(");\n");
                    }
                }
            }
        }
    }

    private String getClassName(TypeElement element, String packageName) {
        int packageLen = packageName.length() + 1;
        return element.getQualifiedName().toString().substring(packageLen)
                .replace('.', '$');
    }
}
