package net.dawn.dawnweapons.registry;

import net.dawn.dawnweapons.DawnItemGroups;
import net.dawn.dawnweapons.DawnWeapons;
import net.dawn.dawnweapons.Utils;
import net.dawn.dawnweapons.item.DawnBaseSword;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.ToolMaterials;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.lwjgl.system.CallbackI;

import java.util.HashMap;
import java.util.Map;

public class ItemRegistry {

    private static final HashMap<String, Item> ITEMS = new HashMap<>();

    public static Item getItem(String id) {
        return ITEMS.getOrDefault(id, Items.AIR);
    }

    private static void loadItems() {
        ITEMS.put("dawn_base_sword", new DawnBaseSword(ToolMaterials.IRON, 2, -2f, new FabricItemSettings().group(DawnItemGroups.WEAPONS)));
    }

    private static void registerItems() {

        for(Map.Entry<String, Item> itemEntry : ITEMS.entrySet()) {
            Registry.register(Registry.ITEM, Utils.makeIdentifier(itemEntry.getKey()), itemEntry.getValue());
        }
    }

    public static void init() {
        loadItems();
        registerItems();
    }
}
