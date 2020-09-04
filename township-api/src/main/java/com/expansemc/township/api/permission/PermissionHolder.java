package com.expansemc.township.api.permission;

import org.spongepowered.api.util.Tristate;

public interface PermissionHolder {

    boolean supports(PermissionType type);

    Tristate getPermissionValue(Permission permission);
}