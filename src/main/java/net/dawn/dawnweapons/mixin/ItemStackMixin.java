package net.dawn.dawnweapons.mixin;

import net.dawn.dawnweapons.registry.ItemRegistry;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemStack.class)
public class ItemStackMixin {

    @Inject(method = "<init>(Lnet/minecraft/item/ItemConvertible;I)V", at = @At("TAIL"))
    public void constructor(ItemConvertible item, int count, CallbackInfo ci) {
        if(item != null && item.asItem() == ItemRegistry.getItem("dawn_base_sword")) {
            // Random NBT if I want :)
        }
    }
}
