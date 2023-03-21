package pub.chara.disablealttab.hook

import com.github.kyuubiran.ezxhelper.utils.*
import de.robv.android.xposed.XposedBridge

private val SCALES = floatArrayOf(0.5F, 0.85F, 1.0F, 1.15F)
private const val CLZ_NAME = "com.motorola.taskbar.settings.DisplayDensityUtils"

object ReadyForResloutionHook : BaseHook() {
    override fun init() {
        try {
            findMethod(CLZ_NAME) {
                name == "getValues"
            }.hookBefore { param ->
                run {
                    param.result = SCALES
                }
            }
            XposedBridge.log("ReadyForSesolutionHook: Hook [getValues] success!")
        } catch (e: Throwable) {
            XposedBridge.log("ReadyForSesolutionHook: Hook [getValues] failed!")
            XposedBridge.log(e)
        }

        try {
            findMethod(CLZ_NAME) {
                name == "getDefaultDisplayDensity"
            }.hookBefore { param ->
                run {
                    param.result = 180
                }
            }
            XposedBridge.log("ReadyForSesolutionHook: Hook [getDefaultDisplayDensity] success!")
        } catch (e: Throwable) {
            XposedBridge.log("ReadyForSesolutionHook: Hook [getDefaultDisplayDensity] failed!")
            XposedBridge.log(e)
        }

        try {
            findMethod(CLZ_NAME) {
                name == "getEntries"
            }.hookBefore { param ->
                run {
                    param.result = Array(4) {i -> SCALES[i].toString()}
                }
            }
            XposedBridge.log("ReadyForSesolutionHook: Hook [getEntries] success!")
        } catch (e: Throwable) {
            XposedBridge.log("ReadyForSesolutionHook: Hook [getEntries] failed!")
            XposedBridge.log(e)
        }
    }
}