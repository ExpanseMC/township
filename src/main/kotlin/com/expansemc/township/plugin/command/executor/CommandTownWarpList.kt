package com.expansemc.township.plugin.command.executor

import com.expansemc.township.plugin.storage.dao.TownDao
import com.expansemc.township.plugin.storage.dao.TownWarpDao
import com.expansemc.township.plugin.util.TextUI
import net.kyori.adventure.text.Component
import org.jetbrains.exposed.sql.SizedIterable

object CommandTownWarpList : AbstractTownPropertyListCommand<TownWarpDao>() {

    override val titleName: String = "Warps"

    override fun getValues(town: TownDao): SizedIterable<TownWarpDao> = town.warps

    override fun toComponent(value: TownWarpDao): Component = TextUI.townWarpInfo(value.name)
}