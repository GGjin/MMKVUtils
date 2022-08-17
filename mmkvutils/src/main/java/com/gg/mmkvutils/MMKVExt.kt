package com.gg.mmkvutils

import android.os.Parcelable
import com.tencent.mmkv.MMKV
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 *  Create by GG on 2022/8/17
 *  mail is gg.jin.yu@gmail.com
 *
 *  name 存取值的键值
 *  default  默认值，非空，必传，用来获取存储数据的类型
 *  idName  表名，可不传，
 *  isMultiProgress 是否可以多进程访问，默认单进程访问
 */



class MMKVExt<T : Any>(
    private val key: String? = null, private val defaultValue: T? = null, idName: String, isMultiProgress: Boolean
) : ReadWriteProperty<Any, T?> {

    private val kv: MMKV = MMKV.mmkvWithID(idName, if (isMultiProgress) MMKV.MULTI_PROCESS_MODE else MMKV.SINGLE_PROCESS_MODE)!!


    override fun getValue(thisRef: Any, property: KProperty<*>): T? {
        return findMMKV(key, defaultValue)
    }


    override fun setValue(thisRef: Any, property: KProperty<*>, value: T?) {
        putMMKV(key, value)
    }


    private fun <T : Any> findMMKV(name: String?, default: T?): T? = with(kv) {
        val res: Any = when (default) {
            is Long -> decodeLong(name, default)
            is String -> decodeString(name, default) ?: ""
            is Int -> decodeInt(name, default)
            is Boolean -> decodeBool(name, default)
            is Float -> decodeFloat(name, default)
            else -> throw IllegalArgumentException("This type can be saved into Preferences")
        }
        res as T?
    }

    private fun <T> putMMKV(name: String?, value: T?) = with(kv) {
        when (value) {
            is Long -> encode(name, value)
            is String -> encode(name, value)
            is Int -> encode(name, value)
            is Boolean -> encode(name, value)
            is Float -> encode(name, value)
            else -> throw IllegalArgumentException("This type can be saved into Preferences")
        }
    }


}

