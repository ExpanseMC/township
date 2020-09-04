package com.expansemc.township.plugin.config

import kotlinx.serialization.Serializable

@Serializable
data class Config(
    private val town: TownConfig
)