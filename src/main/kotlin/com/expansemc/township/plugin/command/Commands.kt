package com.expansemc.township.plugin.command

import com.expansemc.township.plugin.command.executor.*
import org.spongepowered.api.command.Command
import org.spongepowered.api.event.lifecycle.RegisterCommandEvent
import org.spongepowered.plugin.PluginContainer

object Commands {

    fun register(event: RegisterCommandEvent<Command.Parameterized>, plugin: PluginContainer) {
        event.register(plugin, resident, "resident", "res")
        event.register(plugin, town, "town", "t")
    }

    val resident: Command.Parameterized = command {
        setPermission("township.resident.base")

        child("info", "i") {
            setPermission("township.resident.info.base")
            setExecutor(CommandResidentInfo)

            parameters(Parameters.residentOrSelf.parameter)
        }

        child("list", "l") {
            setPermission("township.resident.list.base")
            setExecutor(CommandResidentList)
        }

        child("friend", "f") {
            setPermission("township.resident.friend.base")

            child("add") {
                setPermission("township.resident.friend.add.base")
                setExecutor(CommandResidentFriendAdd)

                parameters(Parameters.friend.parameter)
            }

            child("clear") {
                setPermission("township.resident.friend.clear.base")
                setExecutor(CommandResidentFriendClear)
            }

            child("list", "l") {
                setPermission("township.resident.friend.list.base")
                setExecutor(CommandResidentFriendList)

                parameters(Parameters.residentOrSelf.parameter)
            }

            child("remove") {
                setPermission("township.resident.friend.remove.base")
                setExecutor(CommandResidentFriendRemove)

                parameters(Parameters.friend.parameter)
            }
        }
    }

    val town: Command.Parameterized = command {
        setPermission("township.town.base")
        setExecutor(CommandTown)

        child("claim", "c") {
            setPermission("township.town.claim.base")

            child("add") {
                setPermission("township.town.claim.add.base")
                setExecutor(CommandTownClaimAdd)
            }

            child("list", "l") {
                setPermission("township.town.claim.list.base")
                setExecutor(CommandTownClaimList)
            }
        }

        child("create") {
            setPermission("township.town.create.base")
            setExecutor(CommandTownCreate)

            setExecutionRequirements(ExecutionRequirements::isPlayer)
            parameters(Parameters.name)
        }

        child("delete") {
            setPermission("township.town.delete.base")
            setExecutor(CommandTownDelete)

            setExecutionRequirements(ExecutionRequirements::isPlayer)
        }

        child("here", "h") {
            setPermission("township.town.here.base")
            setExecutor(CommandTownHere)
        }

        child("info", "i") {
            setPermission("township.town.info.base")
            setExecutor(CommandTownInfo)

            parameters(Parameters.townOrOwn.parameter)
        }

        child("join") {
            setPermission("township.town.join.base")
            setExecutor(CommandTownJoin)

            parameters(Parameters.town.parameter)
        }

        child("leave") {
            setPermission("township.town.leave.base")
            setExecutor(CommandTownLeave)
        }

        child("list", "l") {
            setPermission("township.town.list.base")
            setExecutor(CommandTownList)
        }

        child("residents", "res") {
            setPermission("township.town.residents.base")
            setExecutor(CommandTownResidents)

            parameters(Parameters.townOrOwn.parameter)
        }

        child("role", "r") {
            setPermission("township.town.role.base")

            child("list", "l") {
                setPermission("township.town.role.list.base")
                setExecutor(CommandTownRoleList)
            }
        }

        child("set") {
            setPermission("township.town.set.base")

            child("open") {
                setPermission("township.town.set.open.base")
                setExecutor(CommandTownSetOpen)

                parameters(Parameters.open)
            }
        }

        child("warp", "w") {
            setPermission("township.town.warp.base")

            child("add") {
                setPermission("township.town.warp.add.base")
                setExecutor(CommandTownWarpAdd)

                parameters(Parameters.name)
            }

            child("list", "l") {
                setPermission("township.town.warp.list.base")
                setExecutor(CommandTownWarpList)
            }

            child("teleport", "tp") {
                setPermission("township.town.warp.teleport.base")
                setExecutor(CommandTownWarpTeleport)

                setExecutionRequirements(ExecutionRequirements::isPlayer)
                parameters(Parameters.townWarp.parameter)
            }
        }
    }
}