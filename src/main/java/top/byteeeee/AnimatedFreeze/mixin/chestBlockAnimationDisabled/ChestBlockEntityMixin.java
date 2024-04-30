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

package top.byteeeee.AnimatedFreeze.mixin.chestBlockAnimationDisabled;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.block.entity.TrappedChestBlockEntity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import top.byteeeee.AnimatedFreeze.helpers.AnimationDisableList;

@Environment(EnvType.CLIENT)
@Mixin(ChestBlockEntity.class)
public abstract class ChestBlockEntityMixin {
    @ModifyReturnValue(method = "getAnimationProgress", at = @At("RETURN"))
    private float getAnimationProgress(float original) {
        ChestBlockEntity chestBlockEntity = (ChestBlockEntity) (Object) this;
        boolean isTrappedChest = chestBlockEntity instanceof TrappedChestBlockEntity;
        if (AnimationDisableList.list.contains("chest") && !isTrappedChest) {
            return 0.0F;
        } else if (AnimationDisableList.list.contains("trapped_chest") && isTrappedChest) {
            return 0.0F;
        } else {
            return original;
        }
    }
}
