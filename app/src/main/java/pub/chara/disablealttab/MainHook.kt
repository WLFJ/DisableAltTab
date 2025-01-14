package pub.chara.disablealttab

import com.github.kyuubiran.ezxhelper.init.EzXHelperInit
import com.github.kyuubiran.ezxhelper.utils.Log
import com.github.kyuubiran.ezxhelper.utils.Log.logexIfThrow
import de.robv.android.xposed.IXposedHookLoadPackage
import de.robv.android.xposed.callbacks.XC_LoadPackage
import pub.chara.disablealttab.hook.*

private const val PACKAGE_NAME_HOOKED = "android"
private const val READY_FOR_NAME_HOOKED = "com.motorola.systemui.desk"

private const val TAG = "DisableAltTab"
private const val READY_FOR_TAG = "ReadyFor"

class MainHook : IXposedHookLoadPackage {
    override fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam) {
        if (lpparam.packageName == PACKAGE_NAME_HOOKED) {
            // Init EzXHelper
            EzXHelperInit.initHandleLoadPackage(lpparam)
            EzXHelperInit.setLogTag(TAG)
            EzXHelperInit.setToastTag(TAG)
            initHooks(AltTabHook)
        } else if(lpparam.packageName == READY_FOR_NAME_HOOKED) {
            EzXHelperInit.initHandleLoadPackage(lpparam)
            EzXHelperInit.setLogTag(READY_FOR_TAG)
            EzXHelperInit.setToastTag(READY_FOR_TAG)
            initHooks(ReadyForResloutionHook)
        }
    }

    private fun initHooks(vararg hook: BaseHook) {
        hook.forEach {
            runCatching {
                if (it.isInit) return@forEach
                it.init()
                it.isInit = true
                Log.i("Inited hook: ${it.javaClass.simpleName}")
            }.logexIfThrow("Failed init hook: ${it.javaClass.simpleName}")
        }
    }
}
