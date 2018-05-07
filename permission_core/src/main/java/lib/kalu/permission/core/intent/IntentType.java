package lib.kalu.permission.core.intent;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static lib.kalu.permission.core.intent.IntentType.ANDROID_SETTING;
import static lib.kalu.permission.core.intent.IntentType.PLATFRRM_SETTING;

/**
 * Created by kalu on 2017/12/17.
 */

@IntDef({PLATFRRM_SETTING, ANDROID_SETTING})
@Retention(RetentionPolicy.SOURCE)
public @interface IntentType {
    int PLATFRRM_SETTING = 1;
    int ANDROID_SETTING = 0;
}
