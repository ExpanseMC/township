package com.expansemc.township.api.permission;

import com.expansemc.township.api.util.CatalogTypeSupplier;

import java.util.function.Supplier;

public final class PermissionTypes {

    public static final Supplier<PermissionType> CLAIM = CatalogTypeSupplier.provide(PermissionType.class, "claim");

    public static final Supplier<PermissionType> TOWN = CatalogTypeSupplier.provide(PermissionType.class, "town");

    private PermissionTypes() {
    }
}