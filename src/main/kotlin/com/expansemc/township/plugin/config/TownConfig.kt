package com.expansemc.township.plugin.config

import kotlinx.serialization.Serializable

@Serializable
data class TownConfig(
    private val open: Boolean = false
)