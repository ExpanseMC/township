package com.expansemc.township.plugin.util

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.TextComponent
import net.kyori.adventure.text.event.ClickEvent
import net.kyori.adventure.text.format.NamedTextColor
import org.spongepowered.api.service.pagination.PaginationList
import java.time.Instant

object TextUI {

    private val PADDING: TextComponent = TextComponent.of('-', NamedTextColor.GOLD)

    fun pagination(title: String, contents: Iterable<Component>): PaginationList =
        PaginationList.builder()
            .title(TextComponent.of(title, NamedTextColor.DARK_GREEN))
            .padding(PADDING)
            .contents(contents)
            .build()

    fun pagination(title: String, vararg contents: Component): PaginationList =
        PaginationList.builder()
            .title(TextComponent.of(title, NamedTextColor.DARK_GREEN))
            .padding(PADDING)
            .contents(*contents)
            .build()

    inline fun pagination(title: String, builder: MutableList<Component>.() -> Unit): PaginationList =
        pagination(title, @OptIn(ExperimentalStdlibApi::class) buildList(builder))

    fun property(name: String, value: String): TextComponent =
        TextComponent.builder("$name: ", NamedTextColor.GRAY)
            .append(value, NamedTextColor.WHITE)
            .build()

    fun timestampProperty(name: String, value: Instant): TextComponent =
        TextComponent.builder("$name: ", NamedTextColor.GRAY)
            .append(DateFormatters.format(value), NamedTextColor.YELLOW)
            .build()

    fun boolProperty(name: String, value: Boolean): TextComponent =
        TextComponent.builder("$name? ", NamedTextColor.GRAY)
            .append(value.toString(), NamedTextColor.WHITE)
            .build()

    fun clickableProperty(name: String, value: String, hover: String, command: String): TextComponent =
        TextComponent.builder("$name: ", NamedTextColor.GRAY)
            .append(clickable(value, hover, command))
            .build()

    private fun clickable(text: String, hover: String, command: String): TextComponent =
        TextComponent.builder("[$text]", NamedTextColor.AQUA)
            .clickEvent(ClickEvent.runCommand(command))
            .hoverEvent(TextComponent.of(hover, NamedTextColor.AQUA))
            .build()

    fun townInfo(name: String): TextComponent =
        clickable(
            text = name,
            hover = "Click here to see town information.",
            command = "/township:town info $name"
        )

    fun townClaimInfo(chunkX: Int, chunkZ: Int): TextComponent =
        clickable(
            text = "$chunkX, $chunkZ",
            hover = "Click here to see town claim information.",
            command = "/township:town claim info $chunkX $chunkZ"
        )

    fun townRoleInfo(name: String): TextComponent =
        clickable(
            text = name,
            hover = "Click here to see town role information.",
            command = "/township:town role info $name"
        )

    fun townWarpInfo(name: String): TextComponent =
        clickable(
            text = name,
            hover = "Click here to see town warp information.",
            command = "/township:town warp info $name"
        )

    fun residentInfo(name: String): TextComponent =
        clickable(
            text = name,
            hover = "Click here to see resident information.",
            command = "/township:resident info $name"
        )

    fun command(base: String, args: String): TextComponent =
        TextComponent.builder()
            .append("- /", NamedTextColor.GRAY)
            .append("$base ", NamedTextColor.WHITE)
            .append(args, NamedTextColor.YELLOW)
            .build()

    fun command(base: String): TextComponent =
        TextComponent.builder()
            .append("- /", NamedTextColor.GRAY)
            .append("$base ", NamedTextColor.WHITE)
            .build()
}