package com.expansemc.township.plugin.command.executor

import com.expansemc.township.plugin.storage.dao.TownDao
import com.expansemc.township.plugin.storage.dao.TownRoleDao
import com.expansemc.township.plugin.storage.table.TownRoleTable
import com.expansemc.township.plugin.util.TextUI
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.TextComponent
import net.kyori.adventure.text.format.NamedTextColor
import org.jetbrains.exposed.sql.SizedIterable
import org.jetbrains.exposed.sql.SortOrder

object CommandTownRoleList : AbstractTownPropertyListCommand<TownRoleDao>() {

    override val titleName: String = "Roles"

    override fun getValues(town: TownDao): SizedIterable<TownRoleDao> =
        town.roles.orderBy(TownRoleTable.priority to SortOrder.DESC)

    override fun toComponent(value: TownRoleDao): Component =
        TextComponent.of("${value.priority} - ", NamedTextColor.GRAY)
            .append(TextUI.townRoleInfo(value.name))
}