package net.dawn.dawnweapons;

import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;

public class DawnItemGroups {

    public static final ItemGroup WEAPONS = FabricItemGroupBuilder.create(Utils.makeIdentifier("weapons")).icon(() -> new ItemStack(Items.COBBLESTONE)).build();

}
