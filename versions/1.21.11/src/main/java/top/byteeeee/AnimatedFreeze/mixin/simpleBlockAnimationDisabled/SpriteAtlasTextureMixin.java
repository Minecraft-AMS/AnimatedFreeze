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

///*
// * This file is part of the AnimatedFreeze project, licensed under the
// * GNU Lesser General Public License v3.0
// *
// * Copyright (C) 2024 1024_byteeeee and contributors
// *
// * AnimatedFreeze is free software: you can redistribute it and/or modify
// * it under the terms of the GNU Lesser General Public License as published by
// * the Free Software Foundation, either version 3 of the License, or
// * (at your option) any later version.
// *
// * AnimatedFreeze is distributed in the hope that it will be useful,
// * but WITHOUT ANY WARRANTY; without even the implied warranty of
// * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
// * GNU Lesser General Public License for more details.
// *
// * You should have received a copy of the GNU Lesser General Public License
// * along with AnimatedFreeze. If not, see <https://www.gnu.org/licenses/>.
// */

package top.byteeeee.AnimatedFreeze.mixin.simpleBlockAnimationDisabled;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;

import com.mojang.blaze3d.buffers.GpuBufferSlice;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.texture.SpriteContents;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import top.byteeeee.AnimatedFreeze.helpers.AnimationDisableList;

@Environment(EnvType.CLIENT)
@Mixin(SpriteAtlasTexture.class)
public abstract class SpriteAtlasTextureMixin {
    @WrapOperation(
        method = "create",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/texture/Sprite;createAnimator(Lcom/mojang/blaze3d/buffers/GpuBufferSlice;I)Lnet/minecraft/client/texture/SpriteContents$Animator;"
        )
    )
    private SpriteContents.Animator noAnimation(Sprite sprite, GpuBufferSlice gpuBufferSlice, int i, Operation<SpriteContents.Animator> original) {
        for (String block : AnimationDisableList.list) {
            if (sprite.getContents().getId().getPath().endsWith(block)) {
                return null;
            }
        }

        return original.call(sprite, gpuBufferSlice, i);
    }
}

