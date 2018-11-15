package zs.xmx.lib_utils.utils.sp

import android.content.Context
import android.content.SharedPreferences
import zs.xmx.lib_utils.utils.GlobalUtils
import kotlin.reflect.KProperty

/*
 * @创建者     默小铭
 * @博客       http://blog.csdn.net/u012792686
 * @创建时间   2018/11/9 16:45
 * @本类描述	  Kotlin  SPUtils
 *
 * @使用
 * 1.在一个Kotlin扩展类定义一个方法
 * object DelegatesExt {
    fun <T> preference(name: String, default: T) = Preference(name, default)
}

2.定义对象: var mGuideEnable by DelegatesExt.preference("guide", false)
3.再用mGuideEnabe=xx 赋值, 当前 mGuideEnable 取值
 *
 */
class SPUtils_kotlin<T>(val name: String, val default: T) {
    val prefs: SharedPreferences by lazy { GlobalUtils.getApp().getSharedPreferences("SP", Context.MODE_PRIVATE) }

    operator fun getValue(thisRef: Any?, property: KProperty<*>): T = getSharedPreferences(name, default)

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) = putSharedPreferences(name, value)


    private fun putSharedPreferences(name: String, value: T) = with(prefs.edit()) {
        when (value) {
            is Int -> putInt(name, value)
            is Float -> putFloat(name, value)
            is Long -> putLong(name, value)
            is Boolean -> putBoolean(name, value)
            is String -> putString(name, value)
            else -> throw IllegalArgumentException("SharedPreference can't be save this type")
        }.apply()
    }

    private fun getSharedPreferences(name: String, default: T): T = with(prefs) {
        val res: Any = when (default) {
            is Int -> getInt(name, default)
            is Float -> getFloat(name, default)
            is Long -> getLong(name, default)
            is Boolean -> getBoolean(name, default)
            is String -> getString(name, default)
            else -> throw IllegalArgumentException("SharedPreference can't be get this type")
        }
        return res as T
    }


}