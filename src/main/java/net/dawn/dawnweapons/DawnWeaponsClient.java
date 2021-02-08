package net.dawn.dawnweapons;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.model.ModelLoadingRegistry;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.util.Identifier;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;

public class DawnWeaponsClient implements ClientModInitializer {

    public static final HashSet<String> MATERIALS = new HashSet<>();
    public static final HashSet<String> RENDERING_PARTS = new HashSet<>();

    @Override
    public void onInitializeClient() {
        MATERIALS.add("wood");
        MATERIALS.add("stone");
        MATERIALS.add("iron");

        ModelLoadingRegistry.INSTANCE.registerModelProvider((manager, out) ->
        {
            for (String material : this.MATERIALS)
            {
                RENDERING_PARTS.add(material + "_blade");
                out.accept(inventoryModelID(material + "_blade"));
            }
        });
        ModelLoadingRegistry.INSTANCE.registerVariantProvider(rm -> new ItemModelProvider());
    }

    public static ModelIdentifier inventoryModelID(String id)
    {
        return new ModelIdentifier(new Identifier(DawnWeapons.MOD_ID, id), "inventory");
    }

    public static int getMaterialFromPathIndex(String path) {
        String material = "";
        String[] segments = path.split("_");
        int i = 0;
        while (!MATERIALS.contains(material) && i < segments.length) {
            if (i > 0) {
                material += "_";
            }
            material += segments[i];
            ++i;
        }
        if (i == segments.length) {
            return i;
        }
        int j = i;
        do {
            material += "_" + segments[j];
            ++j;
        } while (!MATERIALS.contains(material) && j < segments.length);
        return j == segments.length ? i : j;
    }

    public static InputStream recolor(File template, File templatePalette, File palette, String textureName) {
        BufferedImage templateImage;
        BufferedImage paletteImage;
        BufferedImage templatePaletteImage;
        try {
            templateImage = ImageIO.read(template);
            paletteImage = ImageIO.read(palette);
            templatePaletteImage = ImageIO.read(templatePalette);
        } catch (IOException e) {
            DawnWeapons.LOGGER.warn("Error while creating texture " + textureName);
            e.printStackTrace();
            return null;
        }
        ArrayList<Integer> templateColors = new ArrayList<>();
        for (int x = 0; x < templatePaletteImage.getWidth(); ++x) {
            templateColors.add(templatePaletteImage.getRGB(x, 0));
        }
        ArrayList<Integer> paletteColors = new ArrayList<>();
        for (int x = 0; x < paletteImage.getWidth(); ++x) {
            paletteColors.add(paletteImage.getRGB(x, 0));
        }

        for (int y = 0; y < templateImage.getHeight(); ++y){
            for (int x = 0; x < templateImage.getWidth(); ++x){
                for (int i = 0; i < templateColors.size(); ++i) {
                    if (templateImage.getRGB(x, y) == templateColors.get(i)) {
                        templateImage.setRGB(x, y, paletteColors.get(i));
                        break;
                    }
                }
            }
        }

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            ImageIO.write(templateImage, "PNG", os);
            return new ByteArrayInputStream(os.toByteArray());
        } catch (IOException e) {
            DawnWeapons.LOGGER.warn("Error while creating texture " + textureName);
            e.printStackTrace();
        }
        return null;
    }
}
