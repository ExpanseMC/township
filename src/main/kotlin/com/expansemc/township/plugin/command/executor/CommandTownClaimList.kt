package com.expansemc.township.plugin.command.executor

import com.expansemc.township.plugin.storage.dao.TownClaimDao
import com.expansemc.township.plugin.storage.dao.TownDao
import com.expansemc.township.plugin.util.TextUI
import net.kyori.adventure.text.Component
import org.jetbrains.exposed.sql.SizedIterable

object CommandTownClaimList : AbstractTownPropertyListCommand<TownClaimDao>() {

    override val titleName: String = "Claims"

    override fun getValues(town: TownDao): SizedIterable<TownClaimDao> = town.claims

    override fun toComponent(value: TownClaimDao): Component = TextUI.townClaimInfo(value.chunkX, value.chunkZ)
}