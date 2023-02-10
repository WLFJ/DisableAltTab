package pub.chara.disablealttab.hook

import android.view.KeyEvent
import android.view.KeyEvent.*
import com.github.kyuubiran.ezxhelper.utils.*
import de.robv.android.xposed.XposedBridge


object AltTabHook : BaseHook() {
    override fun init() {
        try {
            //disable alt-tab
            //this works for any android version
            findMethod("com.android.server.policy.PhoneWindowManager") {
                name == "interceptKeyBeforeDispatching"
            }.hookBefore { param ->
                run {
                    val arg1: KeyEvent = param.args[1] as KeyEvent
                    val kc = arg1.keyCode
                    // XposedBridge.log("[at]: key pressed: " + KeyEvent.keyCodeToString(kc))
                    if ((arg1.isAltPressed && kc == KEYCODE_TAB) || arg1.isMetaPressed || kc == KEYCODE_META_LEFT) {
                        param.result = 0L
                    }
                }
            }
            XposedBridge.log("DisableAltTab: AltTabHook success!")
        } catch (e: Throwable) {
            XposedBridge.log("DisableAltTab: AltTabHook failed!")
            XposedBridge.log(e)
        }
    }
}