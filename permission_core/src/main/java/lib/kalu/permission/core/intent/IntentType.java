package lib.kalu.permission.core.intent;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static lib.kalu.permission.core.intent.IntentType.GOOGLE_SETTING;
import static lib.kalu.permission.core.intent.IntentType.SYSTEM_SETTING;

@IntDef({SYSTEM_SETTING, GOOGLE_SETTING})
@Retention(RetentionPolicy.SOURCE)
public @interface IntentType {
    int SYSTEM_SETTING = 1;
    int GOOGLE_SETTING = 0;
}
