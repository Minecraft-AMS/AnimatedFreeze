/*
 * This file is part of the Kaleidoscope project, licensed under the
 * GNU Lesser General Public License v3.0
 *
 * Copyright (C) 2024 1024_byteeeee and contributors
 *
 * Kaleidoscope is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Kaleidoscope is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Kaleidoscope. If not, see <https://www.gnu.org/licenses/>.
 */

package top.byteeeee.AnimatedFreeze.commands.animatedFreezeCommand;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.command.v1.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v1.FabricClientCommandSource;

import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.client.network.ClientPlayerEntity;

import top.byteeeee.AnimatedFreeze.AnimatedFreezeClient;
import top.byteeeee.AnimatedFreeze.config.AnimatedFreezeConfig;
import top.byteeeee.AnimatedFreeze.helpers.AnimationDisableList;
import top.byteeeee.AnimatedFreeze.utils.MessageTextEventUtils.ClickEventUtil;
import top.byteeeee.AnimatedFreeze.utils.MessageTextEventUtils.HoverEventUtil;
import top.byteeeee.AnimatedFreeze.utils.Messenger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Environment(EnvType.CLIENT)
public class AnimatedCommand {
    private static final String MSG_HEAD = "<AnimatedFreeze> ";
    private static final List<String> EXTRA_SUGGESTIONS = new ArrayList<>();

    public static void register(CommandDispatcher<FabricClientCommandSource> dispatcher) {
        dispatcher.register(
            ClientCommandManager.literal("animatedFreeze")
            // add
            .then(ClientCommandManager.literal("add")
            .then(suggestions(StringArgumentType.string())
            .executes(context -> add(
                context.getSource().getPlayer(),
                StringArgumentType.getString(context, "block")
            ))))
            // remove
            .then(ClientCommandManager.literal("remove")
            .then(suggestions(StringArgumentType.string())
            .executes(context -> remove(
                context.getSource().getPlayer(),
                StringArgumentType.getString(context, "block")
            ))))
            // removeAll
            .then(ClientCommandManager.literal("removeAll")
            .executes(context -> removeAll(context.getSource().getPlayer())))
            // list
            .then(ClientCommandManager.literal("list")
            .executes(context -> list(context.getSource().getPlayer())))
            // help
            .then(ClientCommandManager.literal("help")
            .executes(context -> help(context.getSource().getPlayer())))
        );
    }

    private static RequiredArgumentBuilder<FabricClientCommandSource, String> suggestions(StringArgumentType type) {
        return
            ClientCommandManager.argument("block", type).suggests(
                (context, builder) -> CompletableFuture.supplyAsync(
                    () -> {
                        for (Identifier id : Registry.BLOCK.getIds()) {
                            builder.suggest(id.toString().replace("minecraft:", ""));
                        }
                        EXTRA_SUGGESTIONS.forEach(builder::suggest);
                        return builder.build();
                    }
                )
            );
    }

    private static int add(ClientPlayerEntity player, String blockName) {
        if (!AnimationDisableList.list.contains(blockName)) {
            AnimationDisableList.list.add(blockName);
            afterRunShouldDo();
            player.sendMessage(
                Messenger.s(
                    Messenger.tr("animatedFreeze.command.animatedFreeze.add.success", MSG_HEAD, blockName).getString()
                ).formatted(Formatting.GREEN), false
            );
            return 1;
        } else {
            player.sendMessage(
                Messenger.s(
                    Messenger.tr("animatedFreeze.command.animatedFreeze.add.fail", MSG_HEAD, blockName).getString()
                ).formatted(Formatting.YELLOW), false
            );
            return 0;
        }
    }

    private static int remove(ClientPlayerEntity player, String blockName) {
        if (AnimationDisableList.list.contains(blockName)) {
            AnimationDisableList.list.remove(blockName);
            afterRunShouldDo();
            player.sendMessage(
                Messenger.s(
                    Messenger.tr("animatedFreeze.command.animatedFreeze.remove.success", MSG_HEAD, blockName).getString()
                ).formatted(Formatting.LIGHT_PURPLE), false
            );
            return 1;
        } else {
            player.sendMessage(
                Messenger.s(
                    Messenger.tr("animatedFreeze.command.animatedFreeze.remove.fail", MSG_HEAD, blockName).getString()
                ).formatted(Formatting.YELLOW), false
            );
            return 0;
        }
    }

    private static int removeAll(ClientPlayerEntity player) {
        if (!AnimationDisableList.list.isEmpty()) {
            AnimationDisableList.list.clear();
            afterRunShouldDo();
            player.sendMessage(
                Messenger.s(
                    Messenger.tr("animatedFreeze.command.animatedFreeze.removeAll.success", MSG_HEAD).getString()
                ).formatted(Formatting.LIGHT_PURPLE), false
            );
            return 1;
        } else {
            player.sendMessage(
                Messenger.s(
                    Messenger.tr("animatedFreeze.command.animatedFreeze.removeAll.fail", MSG_HEAD)
                ).formatted(Formatting.YELLOW), false
            );
            return 0;
        }
    }

    private static int list(ClientPlayerEntity player) {
        final String LINE = "-------------------------------";
        player.sendMessage(
            Messenger.s(
                Messenger.tr("animatedFreeze.command.animatedFreeze.list.head", Messenger.endl(), LINE).getString()
            ).formatted(Formatting.AQUA), false
        );
        for (String blockName : AnimationDisableList.list) {
            player.sendMessage(Messenger.s(blockName).formatted(Formatting.DARK_AQUA), false);
        }
        return 1;
    }

    private static int help(ClientPlayerEntity player) {
        final String commandHelpDoc = Messenger.tr("animatedFreeze.command.animatedFreeze.help.doc").getString();
        final String commandHelpExtra = Messenger.tr("animatedFreeze.command.animatedFreeze.help.extra").getString();
        player.sendMessage(Messenger.s(commandHelpDoc).formatted(Formatting.BLUE).append(urlButton()), false);
        player.sendMessage(Messenger.s(commandHelpExtra).formatted(Formatting.BLUE), false);
        return 1;
    }

    private static Text urlButton() {
        final String commandHelpUrl = Messenger.tr("animatedFreeze.command.animatedFreeze.help.url").getString();
        return
            Messenger.s(commandHelpUrl).setStyle(
                Style.EMPTY.withColor(Formatting.DARK_BLUE).withUnderline(true)
                .withClickEvent(ClickEventUtil.event(ClickEventUtil.OPEN_URL, commandHelpUrl))
                .withHoverEvent(HoverEventUtil.event(HoverEventUtil.SHOW_TEXT, Messenger.s("GOTO").formatted(Formatting.YELLOW)))
            );
    }

    private static void afterRunShouldDo() {
        AnimatedFreezeConfig.saveListToConfig(AnimationDisableList.list);
        AnimatedFreezeClient.minecraftClient.reloadResources();
    }

    static {
        EXTRA_SUGGESTIONS.add("water_still");
        EXTRA_SUGGESTIONS.add("water_flow");
        EXTRA_SUGGESTIONS.add("lava_still");
        EXTRA_SUGGESTIONS.add("lava_flow");
        EXTRA_SUGGESTIONS.add("fire_0");
        EXTRA_SUGGESTIONS.add("fire_1");
        EXTRA_SUGGESTIONS.add("soul_fire_0");
        EXTRA_SUGGESTIONS.add("soul_fire_1");
        EXTRA_SUGGESTIONS.add("campfire_fire");
        EXTRA_SUGGESTIONS.add("campfire_log_lit");
        EXTRA_SUGGESTIONS.add("soul_campfire_fire");
        EXTRA_SUGGESTIONS.add("soul_campfire_log_lit");
        EXTRA_SUGGESTIONS.add("tall_seagrass_bottom");
        EXTRA_SUGGESTIONS.add("tall_seagrass_top");
    }
}
