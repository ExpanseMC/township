package com.expansemc.township.api.town;

import com.expansemc.township.api.resident.Resident;
import org.spongepowered.api.world.ServerLocation;
import org.spongepowered.math.vector.Vector3i;

import java.time.Instant;
import java.util.Collection;
import java.util.Optional;

public interface Town {

    int getId();

    String getName();

    void setName(String name);

    Resident getOwner();

    void setOwner(Resident owner);

    boolean isOpen();

    void setOpen(boolean open);

    Instant getCreatedAt();

    Collection<TownClaim> getClaims();

    Optional<TownClaim> getClaim(Vector3i chunkPosition);

    default Optional<TownClaim> getClaim(ServerLocation location) {
        return this.getClaim(location.getChunkPosition());
    }

    Collection<Resident> getResidents();

    Collection<TownRole> getRoles();

    Optional<TownRole> getRole(String name);

    Collection<TownWarp> getWarps();

    Optional<TownWarp> getWarp(String name);
}