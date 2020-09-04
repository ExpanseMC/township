package com.expansemc.township.api.permission;

import org.spongepowered.api.NamedCatalogType;
import org.spongepowered.api.ResourceKey;
import org.spongepowered.api.util.NamedCatalogBuilder;

public interface PermissionType extends NamedCatalogType {

    @Override
    ResourceKey getKey();

    @Override
    String getName();

    interface Builder extends NamedCatalogBuilder<PermissionType, Builder> {

        @Override
        Builder name(String name);

        @Override
        Builder key(ResourceKey key);

        @Override
        Builder reset();

        @Override
        PermissionType build() throws IllegalStateException;
    }
}