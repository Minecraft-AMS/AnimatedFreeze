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

import net.minecraft.text.*;

import top.byteeeee.AnimatedFreeze.utils.compat.LiteralTextUtil;
import top.byteeeee.AnimatedFreeze.utils.compat.TranslatableTextUtil;

@Environment(EnvType.CLIENT)
public class Messenger {
    public static BaseText s(Object text) {
        return LiteralTextUtil.compatText(text.toString());
    }

    public static BaseText tr(String key, Object... args) {
        return TranslatableTextUtil.compatText(key, args);
    }

    public static BaseText endl() {
        return s("\n");
    }
}
