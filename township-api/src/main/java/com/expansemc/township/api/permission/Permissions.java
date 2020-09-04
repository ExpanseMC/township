package com.expansemc.township.api.permission;

import com.expansemc.township.api.util.CatalogTypeSupplier;

import java.util.function.Supplier;

public final class Permissions {

    public static final Supplier<Permission> INVITE_RESIDENTS = CatalogTypeSupplier.provide(Permission.class, "invite_residents");

    public static final Supplier<Permission> KICK_RESIDENTS = CatalogTypeSupplier.provide(Permission.class, "kick_residents");

    public static final Supplier<Permission> MANAGE_TOWN_CLAIMS = CatalogTypeSupplier.provide(Permission.class, "manage_town_claims");

    /**
     * Residents with this permission can create new roles and edit/delete roles lower than their highest-priority role.
     *
     * Permission type: {@link PermissionTypes#TOWN}
     */
    public static final Supplier<Permission> MANAGE_TOWN_ROLES = CatalogTypeSupplier.provide(Permission.class, "manage_town_roles");

    /**
     * Residents with this permission can change the roles of other residents whose highest-priority role is lower than this resident's.
     */
    public static final Supplier<Permission> MANAGE_RESIDENT_TOWN_ROLES = CatalogTypeSupplier.provide(Permission.class, "manage_resident_town_roles");

    public static final Supplier<Permission> TOWN_DEPUTY = CatalogTypeSupplier.provide(Permission.class, "town_deputy");

    private Permissions() {
    }
}