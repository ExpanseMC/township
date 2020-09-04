package com.expansemc.township.plugin.command

import com.expansemc.township.plugin.util.unwrap
import net.kyori.adventure.text.TextComponent
import org.spongepowered.api.command.exception.CommandException
import org.spongepowered.api.command.parameter.CommandContext
import org.spongepowered.api.command.parameter.Parameter
import java.util.*

fun interface Transformer<T, R> {
    fun transform(context: CommandContext, value: T): R
}

data class PermissionTransformer<T, R>(
    private val transformer: Transformer<T?, R>,
    private val permissionHasValue: String? = null,
    private val permissionNoValue: String? = null
) : Transformer<T?, R> {
    override fun transform(context: CommandContext, value: T?): R {
        if (value != null && !context.hasPermission(this.permissionHasValue)) {
            throw CommandException(TextComponent.of("You cannot specify the argument."))
        } else if (value == null && !context.hasPermission(this.permissionNoValue)) {
            throw CommandException(TextComponent.of("You must specify the argument."))
        }
        return this.transformer.transform(context, value)
    }
}

data class CustomParameter<T, R>(
    val parameter: Parameter.Value<T>,
        val transformer: Transformer<T, R>
)

infix fun <T, R> Parameter.Value<T>.map(transformer: Transformer<T, R>): CustomParameter<T, R> =
    CustomParameter(this, transformer)

infix fun <T : Any, R> CommandContext.requireOne(custom: CustomParameter<T, R>): R =
    custom.transformer.transform(this, this.requireOne(custom.parameter))

@JvmName("requireOneOptional")
infix fun <T, R> CommandContext.requireOne(custom: CustomParameter<T?, R>): R =
    custom.transformer.transform(this, this.getOne(custom.parameter).unwrap())

infix fun <T, R> CommandContext.getOne(custom: CustomParameter<T, R>): Optional<R> =
    this.getOne(custom.parameter).map { custom.transformer.transform(this, it) }