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

package top.byteeeee.AnimatedFreeze.mixin.changeSettingsValue;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import net.minecraft.client.MinecraftClient;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import top.byteeeee.AnimatedFreeze.AnimatedFreezeSetting;

import java.util.concurrent.CompletableFuture;

@Environment(EnvType.CLIENT)
@Mixin(value = MinecraftClient.class, priority = 1688)
public abstract class MinecraftClientMixin {
    @Inject(
        //#if MC>=12002
        //$$ method = "reloadResources(ZLnet/minecraft/client/MinecraftClient$LoadingContext;)Ljava/util/concurrent/CompletableFuture;",
        //#elseif MC>=11800
        //$$ method = "reloadResources(Z)Ljava/util/concurrent/CompletableFuture;",
        //#else
        method = "reloadResources",
        //#endif
        at = @At("HEAD")
    )
    private void reloadResources(CallbackInfoReturnable<CompletableFuture<Void>> cir) {
        AnimatedFreezeSetting.changeValue();
    }
}
