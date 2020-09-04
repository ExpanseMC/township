package com.expansemc.township.plugin.util

import java.lang.ref.WeakReference
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

fun <T: Any> lazyWeak(getter: () -> T): LazyWeak<T> = LazyWeak(getter)

class LazyWeak<T : Any>(private val getter: () -> T): ReadOnlyProperty<Any?, T> {

    private var ref: WeakReference<T>? = null

    fun get(): T {
        var ref: WeakReference<T>? = this.ref
        if (ref == null) {
            ref = WeakReference(getter())
            this.ref = ref
        }

        val value: T? = ref.get()
        if (value != null) {
            return value
        }

        val newValue: T = getter()
        this.ref = WeakReference(newValue)
        return newValue
    }

    override fun getValue(thisRef: Any?, property: KProperty<*>): T = this.get()
}