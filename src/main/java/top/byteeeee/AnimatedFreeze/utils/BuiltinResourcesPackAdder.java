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

package top.byteeeee.AnimatedFreeze.utils;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class BuiltinResourcesPackAdder {
    public static void add() {
        FabricLoader.getInstance().getModContainer("animatedfreeze").ifPresent(BuiltinResourcesPackAdder::register);
    }

    private static void register(ModContainer modContainer) {
        ResourceManagerHelper.registerBuiltinResourcePack(new Identifier("af_mod:chest_optimization"), modContainer, ResourcePackActivationType.DEFAULT_ENABLED);
        ResourceManagerHelper.registerBuiltinResourcePack(new Identifier("af_mod:sea_lantern_freeze"), modContainer, ResourcePackActivationType.DEFAULT_ENABLED);
        ResourceManagerHelper.registerBuiltinResourcePack(new Identifier("af_mod:seagrass_freeze"), modContainer, ResourcePackActivationType.DEFAULT_ENABLED);
        ResourceManagerHelper.registerBuiltinResourcePack(new Identifier("af_mod:kelp_freeze"), modContainer, ResourcePackActivationType.DEFAULT_ENABLED);
    }
}
