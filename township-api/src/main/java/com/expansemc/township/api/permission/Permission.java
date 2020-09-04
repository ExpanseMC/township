package com.expansemc.township.api.permission;

import org.spongepowered.api.NamedCatalogType;
import org.spongepowered.api.ResourceKey;
import org.spongepowered.api.util.NamedCatalogBuilder;
import org.spongepowered.api.util.annotation.CatalogedBy;

@CatalogedBy(Permissions.class)
public interface Permission extends NamedCatalogType {

    @Override
    ResourceKey getKey();

    @Override
    String getName();

    PermissionType getType();

    interface Builder extends NamedCatalogBuilder<Permission, Builder> {

        @Override
        Builder key(ResourceKey key);

        @Override
        Builder name(String name);

        Builder type(PermissionType type);

        @Override
        Builder reset();

        @Override
        Permission build() throws IllegalStateException;
    }
}