package com.expansemc.township.api;

import org.spongepowered.api.ResourceKey;
import org.spongepowered.api.Sponge;
import org.spongepowered.plugin.PluginContainer;

public interface TownshipAPI {

    static TownshipAPI get() {
        return Sponge.getRegistry().getFactoryRegistry().provideFactory(TownshipAPI.class);
    }

    PluginContainer getImplementation();

    default ResourceKey createKey(String value) {
        return ResourceKey.of(this.getImplementation(), value);
    }
}