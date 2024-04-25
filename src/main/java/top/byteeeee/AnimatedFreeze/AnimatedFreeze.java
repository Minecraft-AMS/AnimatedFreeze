/*
 * This file is part of the AnimatedFreeze project, licensed under the
 * GNU Lesser General Public License v3.0
 *
 * Copyright (C) 2024 1024_byteeeee and contributors
 *
 * AnimatedFreeze is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * AnimatedFreeze is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with AnimatedFreeze. If not, see <https://www.gnu.org/licenses/>.
 */

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
				ResourcePackActivationType.DEFAULT_ENABLED
			)
		);
	}
}
