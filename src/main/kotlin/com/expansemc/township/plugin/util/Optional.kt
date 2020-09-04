package com.expansemc.township.plugin.util

import java.util.*

fun <T> Optional<T>.unwrap(): T? = this.orElse(null)