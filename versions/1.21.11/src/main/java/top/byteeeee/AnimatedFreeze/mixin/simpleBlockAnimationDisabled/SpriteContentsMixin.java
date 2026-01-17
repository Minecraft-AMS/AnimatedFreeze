/*
 * This file is part of the AnimatedFreeze project, licensed under the
 * GNU Lesser General Public License v3.0
 *
 * Copyright (C) 2026 1024_byteeeee and contributors
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

package top.byteeeee.AnimatedFreeze.mixin.simpleBlockAnimationDisabled;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;

import net.minecraft.client.texture.SpriteContents;
import net.minecraft.util.Identifier;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

import top.byteeeee.AnimatedFreeze.helpers.AnimationDisableList;
import top.byteeeee.annotationtoolbox.annotation.GameVersion;

@GameVersion(version = "Minecraft >= 1.21.11")
@Mixin(SpriteContents.class)
public abstract class SpriteContentsMixin {
    @Shadow
    @Final
    Identifier id;

    @ModifyReturnValue(method = "getFrameCount", at = @At("RETURN"))
    private int noAnimation(int original) {
        for (String block : AnimationDisableList.list) {
            if (this.id.getPath().endsWith(block)) {
                return 0;
            }
        }

        return original;
    }
}
