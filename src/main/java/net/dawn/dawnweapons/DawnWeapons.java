package net.dawn.dawnweapons;

import net.dawn.dawnweapons.registry.ItemRegistry;
import net.fabricmc.api.ModInitializer;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DawnWeapons implements ModInitializer {

	public static final String MOD_ID = "dawnweapons";
	public static final String NAME = "DawnWeapons";
	public static final Logger LOGGER = LogManager.getLogger();

	@Override
	public void onInitialize()
	{
		LOGGER.info("[DawnWeapons] is loading.");
		ItemRegistry.init();
		LOGGER.info("[DawnWeapons] is instantiated.");
	}

	/*public static String createItemModelJson(String id, String type)
	{
		if("generated".equals(type) || "handheld".equals(type))
		{
			String e = "{\n" +
					"	\"parent\": \"item/" + type + "\", \n" +
					"	\"textures\": {" +
					"		\"layer0\": \"dawnweapons:" + id + "\"\n" +
					"	}\n" +
					"}";
			System.out.println(e);
			return e;
		}
		else if ("block".equals(type))
		{
			return "{\n" +
					"	\"parent\": \"dawnweapons:block/" + id + "\"\n" +
					"}";
		}
		else
		{
			return "";
		}
	}*/
}
