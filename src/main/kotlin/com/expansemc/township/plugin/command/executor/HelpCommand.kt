package com.expansemc.township.plugin.command.executor

import com.expansemc.township.plugin.util.TextUI
import com.expansemc.township.plugin.util.unwrap
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.TextComponent
import net.kyori.adventure.text.format.NamedTextColor
import org.spongepowered.api.command.Command
import org.spongepowered.api.command.CommandCause
import org.spongepowered.api.command.CommandExecutor
import org.spongepowered.api.command.CommandResult
import org.spongepowered.api.command.parameter.CommandContext
import org.spongepowered.api.command.parameter.Parameter
import org.spongepowered.api.service.pagination.PaginationList

class HelpCommand(
    private val title: String,
    private val baseAlias: String? = null,
) : CommandExecutor {

    override fun execute(context: CommandContext): CommandResult {
        val command: Command.Parameterized = context.executedCommand.unwrap()
            ?: return CommandResult.error(TextComponent.of("Unknown command being executed."))

        val pagination: PaginationList = TextUI.pagination(title) {
            command.subcommands()
                .sortedBy(this@HelpCommand::getAlias)
                .mapNotNullTo(destination = this) { sub: Parameter.Subcommand ->
                val subAlias: String = sub.aliases.maxByOrNull { it.length } ?: return@mapNotNullTo null
                val alias: String = if (baseAlias != null) "$baseAlias $subAlias" else subAlias
                val usage: TextComponent = getUsage(sub.command, context.cause)

                TextComponent.builder("- /", NamedTextColor.GRAY)
                    .append("$alias ", NamedTextColor.WHITE)
                    .append(usage)
                    .build()
            }
        }

        pagination.sendTo(context.cause.audience)
        return CommandResult.success()
    }

    private fun getUsage(command: Command.Parameterized, cause: CommandCause): TextComponent =
        when {
            command.parameters().isNotEmpty() -> {
                // We have parameters to get the usage of.
                val params: List<Component> = command.parameters()
                    .mapNotNull { parameter: Parameter ->
                        val usage: Component = (parameter as? Parameter.Value<*>)?.getUsage(cause) ?: return@mapNotNull null
                        when {
                            parameter.isOptional -> usage
                            else -> TextComponent.builder("<").append(usage).append(">").build()
                        }
                    }

                TextComponent.join(TextComponent.space(), params).color(NamedTextColor.YELLOW)
            }
            command.subcommands().isNotEmpty() -> {
                // We have subcommands to create usage from.
                val subcommands: List<TextComponent> = command.subcommands()
                    .sortedBy(this::getAlias)
                    .mapNotNull { subcommand: Parameter.Subcommand ->
                        val alias: String = getAlias(subcommand) ?: return@mapNotNull null
                        TextComponent.of(alias)
                    }

                TextComponent.join(TextComponent.of(" | "), subcommands).color(NamedTextColor.AQUA)
            }
            else -> {
                // Nothing to create usage from.
                TextComponent.empty()
            }
        }

    private fun getAlias(subcommand: Parameter.Subcommand): String? =
        subcommand.aliases.maxByOrNull { it.length }
}