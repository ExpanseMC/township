package com.expansemc.township.plugin.command

import org.spongepowered.api.command.Command

fun command(build: Command.Builder.() -> Unit): Command.Parameterized =
    Command.builder().apply(build).build()

fun Command.Builder.child(vararg keys: String, build: Command.Builder.() -> Unit) {
    this.child(Command.builder().apply(build).build(), *keys)
}