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

package top.byteeeee.AnimatedFreeze.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;

import top.byteeeee.AnimatedFreeze.AnimatedFreezeClient;
import top.byteeeee.AnimatedFreeze.helpers.AnimationDisableList;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

@Environment(EnvType.CLIENT)
public class AnimatedFreezeConfig {
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private static final Path CONFIG_PATH = FabricLoader.getInstance().getConfigDir().resolve("AnimatedFreeze");
    private static final String CONFIG_FILE = CONFIG_PATH.resolve("BlockList.json").toString();

    public static void saveListToConfig(Object object) {
        createConfigPath();
        try (Writer writer = new FileWriter(CONFIG_FILE)) {
            gson.toJson(object, writer);
        } catch (IOException e) {
            AnimatedFreezeClient.LOGGER.warn("Failed to save configuration" + e);
        }
    }

    public static void loadListFromConfig() {
        createConfigPath();
        try (BufferedReader reader = new BufferedReader(new FileReader(CONFIG_FILE))) {
            String[] array = gson.fromJson(reader, String[].class);
            if (array != null) {
                AnimationDisableList.list.addAll(Arrays.asList(array));
            }
        } catch (IOException e) {
            AnimatedFreezeClient.LOGGER.warn("Failed to load configuration", e);
        }
    }

    private static void createConfigPath() {
        try {
            if (!Files.exists(CONFIG_PATH)) {
                Files.createDirectories(CONFIG_PATH);
                Files.createFile(CONFIG_PATH.resolve("BlockList.json"));
            }
        } catch (IOException e) {
            AnimatedFreezeClient.LOGGER.warn("Failed to creat configuration" + e);
        }
    }
}
