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

package top.byteeeee.AnimatedFreeze.screen;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Formatting;

import top.byteeeee.AnimatedFreeze.AnimatedFreezeClient;
import top.byteeeee.AnimatedFreeze.config.AnimatedFreezeConfig;
import top.byteeeee.AnimatedFreeze.helpers.AnimationDisableList;
import top.byteeeee.AnimatedFreeze.utils.Messenger;

import java.util.List;

@SuppressWarnings({"DuplicatedCode", "FieldCanBeLocal"})
@Environment(EnvType.CLIENT)
public class ListScreen extends Screen {
    private final Screen parentScreen;
    private int currentPage = 0;
    private ButtonWidget removeButton;
    private ButtonWidget itemButton;
    private ButtonWidget nextPageButton;
    private ButtonWidget prePageButton;
    private ButtonWidget backButton;
    private static final int ITEMS_PER_PAGE = 6;

    public ListScreen(Screen parent) {
        super(Messenger.s("AnimatedFreeze"));
        this.parentScreen = parent;
    }

    @Override
    protected void init() {
        super.init();
        this.updatePage();

        backButton = new ButtonWidget(
            (this.width - 100) / 2, this.height - 60, 100, 20,
            Messenger.s(Messenger.tr("animatedFreeze.ui.back").getString()), button -> {
            if (this.client != null) {
                this.client.openScreen(parentScreen);
            }
        });
        this.addButton(backButton);

        prePageButton = new ButtonWidget(
            this.width / 2 - 100, this.height - 60, 40, 20,
            Messenger.s("<"), button -> {
            if (currentPage > 0) {
                currentPage--;
                this.updatePage();
            }
        });
        this.addButton(prePageButton);

        nextPageButton = new ButtonWidget(
            this.width / 2 + 60, this.height - 60, 40, 20,
            Messenger.s(">"), button -> {
            if ((currentPage + 1) * ITEMS_PER_PAGE < AnimationDisableList.list.size()) {
                currentPage++;
                this.updatePage();
            }
        });
        this.addButton(nextPageButton);
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices);
        drawCenteredText(matrices, this.textRenderer, this.title, this.width / 2, 10, 0xFFFFFF);
        super.render(matrices, mouseX, mouseY, delta);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == 256 && this.client != null) {
            this.client.openScreen(parentScreen);
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    private void updatePage() {
        //#if MC>=11700
        //$$ this.clearChildren();
        //#else
        this.children.clear();
        this.buttons.clear();
        //#endif
        List<String> list = AnimationDisableList.list;
        int start = currentPage * ITEMS_PER_PAGE;
        int end = Math.min(start + ITEMS_PER_PAGE, list.size());
        int y = 20;
        for (int i = start; i < end; i++) {
            String item = list.get(i);
            int index = i;

            itemButton = new ButtonWidget(
                this.width / 2 - 100, y, 180, 20,
                Messenger.s(item), button -> {
                // do noting
            });
            this.addButton(itemButton);
            itemButton.active = false;

            removeButton = new ButtonWidget(
                this.width / 2 + 90, y, 20, 20,
                Messenger.s("-").formatted(Formatting.RED), button -> {
                AnimationDisableList.list.remove(index);
                AnimatedFreezeConfig.saveListToConfig(AnimationDisableList.list);
                AnimatedFreezeClient.minecraftClient.reloadResources();
                this.updatePage();
            });
            this.addButton(removeButton);

            y += 25;
        }

        backButton = new ButtonWidget(
            (this.width - 100) / 2, this.height - 60, 100, 20,
            Messenger.s(Messenger.tr("animatedFreeze.ui.back").getString()), button -> {
            if (this.client != null) {
                this.client.openScreen(parentScreen);
            }
        });
        this.addButton(backButton);

        prePageButton = new ButtonWidget(
            this.width / 2 - 100, this.height - 60, 40, 20,
            Messenger.s("<"), button -> {
            if (currentPage > 0) {
                currentPage--;
                this.updatePage();
            }
        });
        this.addButton(prePageButton);

        nextPageButton = new ButtonWidget(
            this.width / 2 + 60, this.height - 60, 40, 20,
            Messenger.s(">"), button -> {
            if ((currentPage + 1) * ITEMS_PER_PAGE < AnimationDisableList.list.size()) {
                currentPage++;
                this.updatePage();
            }
        });
        this.addButton(nextPageButton);
    }
}
