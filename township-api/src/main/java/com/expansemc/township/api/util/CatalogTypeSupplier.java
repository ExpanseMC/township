package com.expansemc.township.api.util;

import com.expansemc.township.api.TownshipAPI;
import org.spongepowered.api.CatalogType;
import org.spongepowered.api.ResourceKey;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.registry.UnknownTypeException;

import java.util.function.Supplier;

public final class CatalogTypeSupplier {

    public static <T extends CatalogType> Supplier<T> provide(Class<T> type, String keyValue) throws UnknownTypeException {
        ResourceKey key = TownshipAPI.get().createKey(keyValue);
        return () -> Sponge.getRegistry().getCatalogRegistry().get(type, key)
                .orElseThrow(() -> new UnknownTypeException(key.getFormatted()));
    }

    private CatalogTypeSupplier() {
    }
}