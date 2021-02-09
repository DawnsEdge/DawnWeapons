package net.dawn.dawnweapons.mixin;

import net.dawn.dawnweapons.DawnWeapons;
import net.dawn.dawnweapons.Utils;
import net.minecraft.client.render.model.ModelLoader;
import net.minecraft.client.render.model.json.JsonUnbakedModel;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ModelLoader.class)
public class ModelLoaderMixin
{

    @Inject(method = "loadModelFromJson", at = @At(value = "HEAD"), cancellable = true)
    public void loadModelFromJson(Identifier id, CallbackInfoReturnable<JsonUnbakedModel> cir) {
        if(!id.getNamespace().equals(DawnWeapons.MOD_ID)) {
            return;
        }
        String path = id.getPath().split("/")[1];
        String[] segments = path.split("_");
        String json = Utils.createModelJson(id.getPath(), "minecraft:item/handheld");
        JsonUnbakedModel model = JsonUnbakedModel.deserialize(json);
        model.id = id.toString();
        cir.setReturnValue(model);
        cir.cancel();
    }


}
