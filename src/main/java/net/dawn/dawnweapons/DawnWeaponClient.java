package net.dawn.dawnweapons;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.model.ModelLoadingRegistry;

import java.util.HashSet;

public class DawnWeaponClient implements ClientModInitializer {

    public static final HashSet<String> SWORD_PARTS = new HashSet<String>(){{
       add("pommel");
       add("grip");
       add("cross_guard");
       add("blade");
    }};
    public static final HashSet<String> RENDERING_PARTS = new HashSet<>();

    @Override
    public void onInitializeClient() {
        ModelLoadingRegistry.INSTANCE.registerModelProvider((manager, out) -> {
            for(String part : SWORD_PARTS) {
                String id;

                id = part;
                RENDERING_PARTS.add(id);
                out.accept(Utils.inventoryModelID(id));
            }
        });

        ModelLoadingRegistry.INSTANCE.registerVariantProvider(rm -> new ItemModelProvider());
    }
}
