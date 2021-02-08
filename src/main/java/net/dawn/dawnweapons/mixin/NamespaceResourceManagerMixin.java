package net.dawn.dawnweapons.mixin;

import net.dawn.dawnweapons.DawnWeapons;
import net.dawn.dawnweapons.DawnWeaponsClient;
import net.minecraft.resource.NamespaceResourceManager;
import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourceImpl;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

@Mixin(NamespaceResourceManager.class)
public class NamespaceResourceManagerMixin {

    @Inject(method = "getResource(Lnet/minecraft/util/Identifier;)Lnet/minecraft/resource/Resource;", at = @At("HEAD"), cancellable = true)
    public void getResource(Identifier id, CallbackInfoReturnable<Resource> cir) {
        String[] segments = id.getPath().split("/");
        String path = segments[segments.length - 1];

        if (!id.getNamespace().equals(DawnWeapons.MOD_ID) || !path.endsWith(".png"))
        {
            return;
        }

        String pathWithoutExtension = path.split("\\.")[0];
        segments = pathWithoutExtension.split("_");
        int maxI = DawnWeaponsClient.getMaterialFromPathIndex(pathWithoutExtension);
        if (maxI >= segments.length)
        {
            return;
        }
        StringBuilder material = new StringBuilder();
        int i = 0;
        for (; i < maxI; ++i)
        {
            if(i > 0)
            {
                material.append("_");
            }
            material.append(segments[i]);
        }
        StringBuilder part = new StringBuilder();
        for(; i < maxI; ++i)
        {
            part.append("_").append(segments[i]);
        }
        part = new StringBuilder(part.substring(1));
        if(!DawnWeaponsClient.MATERIALS.contains(material.toString()))
        {
            return;
        }
        File texture = new File("config/dawnweapons/textures/" + path);
        File metadata = new File("config/dawnweapons/textures/" + path + ".mcmeta");
        if(texture.exists()) {
            try {
                cir.setReturnValue(new ResourceImpl(DawnWeapons.MOD_ID, id ,new FileInputStream(texture), metadata.exists() ? new FileInputStream(metadata) : null));
            } catch (FileNotFoundException e)
            {
                File templatePalette = new File("config/dawnweapons/templates/template_pallete.png");
                File palette = new File("config/dawnweapons/palettes/" + material + ".png");
                File template = new File("config/dawnweapons/templates/" + part + ".png");
                cir.setReturnValue(new ResourceImpl(DawnWeapons.MOD_ID, id, DawnWeaponsClient.recolor(template, templatePalette, palette, pathWithoutExtension), null));
            }
        }
    }
}
