package com.expansemc.township.plugin

import com.expansemc.township.plugin.command.Commands
import com.expansemc.township.plugin.storage.dao.ResidentDao
import com.expansemc.township.plugin.storage.table.Tables
import com.expansemc.township.plugin.storage.transaction
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.jetbrains.exposed.sql.Database
import org.spongepowered.api.Sponge
import org.spongepowered.api.command.Command
import org.spongepowered.api.event.Listener
import org.spongepowered.api.event.lifecycle.ConstructPluginEvent
import org.spongepowered.api.event.lifecycle.RegisterCommandEvent
import org.spongepowered.api.event.network.ServerSideConnectionEvent
import org.spongepowered.plugin.PluginContainer
import org.spongepowered.plugin.jvm.Plugin

@Plugin("township")
class Township {
    companion object {
        val LOGGER: Logger = LogManager.getLogger("township")

        lateinit var DATABASE: Database
            private set
    }

    private lateinit var container: PluginContainer

    @Listener
    fun onConstruct(event: ConstructPluginEvent) {
        LOGGER.info("Constructing plugin...")

        container = event.plugin

        DATABASE = Database.connect(Sponge.getSqlManager().getDataSource("jdbc:h2:mem:township;DB_CLOSE_DELAY=-1;"))
        Tables.initialize()
    }

    @Listener
    fun onRegisterCommand(event: RegisterCommandEvent<Command.Parameterized>) {
        LOGGER.info("Registering commands...")

        Commands.register(event, container)
    }

    @Listener
    fun onJoin(event: ServerSideConnectionEvent.Join) {
        DATABASE.transaction {
            if (ResidentDao.findByPlayerId(event.player.uniqueId) == null) {
                ResidentDao.new {
                    this.playerId = event.player.uniqueId
                    this.name = event.player.name
                    this.town = null
                }

                LOGGER.info("Loaded new user into resident table: ${event.player.name}")
            }
        }
    }
}