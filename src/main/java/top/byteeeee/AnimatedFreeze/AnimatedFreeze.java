package top.byteeeee.AnimatedFreeze;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;

import net.minecraft.util.Identifier;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AnimatedFreeze implements ModInitializer {
	public static final String modName = "AnimatedFreeze";
	public static final Logger LOGGER = LogManager.getLogger(modName);

	@Override
	public void onInitialize() {
		LOGGER.info(modName + " " + "loaded!");

		FabricLoader.getInstance().getModContainer("animatedfreeze").ifPresent(
				modContainer ->
				ResourceManagerHelper.registerBuiltinResourcePack(
						new Identifier("animatedfreeze:animatedfreeze"),
						modContainer,
						ResourcePackActivationType.NORMAL
				)
		);
	}
}
