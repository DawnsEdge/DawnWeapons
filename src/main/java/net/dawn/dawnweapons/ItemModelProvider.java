package net.dawn.dawnweapons;

import net.fabricmc.fabric.api.client.model.ModelProviderContext;
import net.fabricmc.fabric.api.client.model.ModelProviderException;
import net.fabricmc.fabric.api.client.model.ModelVariantProvider;
import net.minecraft.client.render.model.UnbakedModel;
import net.minecraft.client.util.ModelIdentifier;
import org.jetbrains.annotations.Nullable;

public class ItemModelProvider implements ModelVariantProvider {

    @Override
    public @Nullable UnbakedModel loadModelVariant(ModelIdentifier modelIdentifier, ModelProviderContext modelProviderContext) throws ModelProviderException {
        if (modelIdentifier.getNamespace().equals(DawnWeapons.MOD_ID)) {
            return new ItemBakedModel(modelIdentifier);
        }
        return null;
    }
}
