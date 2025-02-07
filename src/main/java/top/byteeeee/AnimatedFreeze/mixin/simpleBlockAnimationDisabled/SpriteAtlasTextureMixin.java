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

package top.byteeeee.AnimatedFreeze.mixin.simpleBlockAnimationDisabled;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.SpriteAtlasTexture;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import top.byteeeee.AnimatedFreeze.helpers.AnimationDisableList;

import top.byteeeee.annotationtoolbox.annotation.GameVersion;

@Environment(EnvType.CLIENT)
@GameVersion(
    version = "Minecraft == 1.16.5",
    desc = "Version differences: 1.17.1 - 1.18.2, 1.19.4 - ?.?.?"
)
@Mixin(SpriteAtlasTexture.class)
public abstract class SpriteAtlasTextureMixin {
    @WrapOperation(
        method = "upload",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/texture/Sprite;isAnimated()Z"
        )
    )
    private boolean noAnimation(Sprite sprite, Operation<Boolean> original) {
        for (String block : AnimationDisableList.list) {
            if (sprite.getId().getPath().endsWith(block)) {
                return false;
            }
        }
        return original.call(sprite);
    }
}
