package net.dawn.dawnweapons;

import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.util.Identifier;

public class Utils {

    public static Identifier makeIdentifier(String path)
    {
        return new Identifier(DawnWeapons.MOD_ID, path);
    }

    public static ModelIdentifier inventoryModelID(String id) {
        return new ModelIdentifier(makeIdentifier(id), "inventory");
    }

    public static boolean isGeneratedWeapon(String path) {
        return path.startsWith("dawn_base_");
    }

    public static String createModelJson(String path, String parent) {
        String[] segments = path.split("/");
        path = segments[segments.length - 1];
        return "{\n" +
                "  \"parent\": \"" + parent + "\",\n" +
                "  \"textures\": {\n" +
                "    \"layer0\": \"" + DawnWeapons.MOD_ID + ":item/" + path + "\"\n" +
                "  }\n" +
                "}";
    }

}
