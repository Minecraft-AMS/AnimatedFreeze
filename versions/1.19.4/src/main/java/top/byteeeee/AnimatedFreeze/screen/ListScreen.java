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
//#if MC>=12000
//$$ import net.minecraft.client.gui.DrawContext;
//#endif
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

        backButton = ButtonWidget.builder(Messenger.s(Messenger.tr("animatedFreeze.ui.back").getString()), addButton -> {
            if (this.client != null) {
                this.client.setScreen(parentScreen);
            }
        }).dimensions((this.width - 100) / 2, this.height - 60, 100, 20).build();
        this.addDrawableChild(backButton);

        prePageButton = ButtonWidget.builder(Messenger.s("<"), addButton -> {
            if (currentPage > 0) {
                currentPage--;
                this.updatePage();
            }
        }).dimensions(this.width / 2 - 100, this.height - 60, 40, 20).build();
        this.addDrawableChild(prePageButton);

        nextPageButton = ButtonWidget.builder(Messenger.s(">"), addButton -> {
            if ((currentPage + 1) * ITEMS_PER_PAGE < AnimationDisableList.list.size()) {
                currentPage++;
                this.updatePage();
            }
        }).dimensions(this.width / 2 + 60, this.height - 60, 40, 20).build();
        this.addDrawableChild(nextPageButton);
    }

    @Override
    public void render(
        //#if MC>=12001
        //$$ DrawContext matrices,
        //#else
        MatrixStack matrices,
        //#endif
        int mouseX, int mouseY, float delta
    ) {
        //#if MC>=12001
        //$$ this.renderBackground(matrices, mouseX, mouseY, delta);
        //#else
        this.renderBackground(matrices);
        //#endif
        super.render(matrices, mouseX, mouseY, delta);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == 256 && this.client != null) {
            this.client.setScreen(parentScreen);
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    private void updatePage() {
        this.clearChildren();
        List<String> list = AnimationDisableList.list;
        int start = currentPage * ITEMS_PER_PAGE;
        int end = Math.min(start + ITEMS_PER_PAGE, list.size());
        int y = 20;
        for (int i = start; i < end; i++) {
            String item = list.get(i);
            int index = i;
            itemButton = ButtonWidget.builder(Messenger.s(item), addButton -> {
                // do nothing
            }).dimensions(this.width / 2 - 100, y, 180, 20).build();
            this.addDrawableChild(itemButton);
            itemButton.active = false;

            removeButton = ButtonWidget.builder(Messenger.s("-").formatted(Formatting.RED), addButton -> {
                AnimationDisableList.list.remove(index);
                AnimatedFreezeConfig.saveListToConfig(AnimationDisableList.list);
                AnimatedFreezeClient.minecraftClient.reloadResources();
                this.updatePage();
            }).dimensions(this.width / 2 + 90, y, 20, 20).build();
            this.addDrawableChild(removeButton);

            y += 25;
        }

        backButton = ButtonWidget.builder(Messenger.s(Messenger.tr("animatedFreeze.ui.back").getString()), addButton -> {
            if (this.client != null) {
                this.client.setScreen(parentScreen);
            }
        }).dimensions((this.width - 100) / 2, this.height - 60, 100, 20).build();
        this.addDrawableChild(backButton);

        prePageButton = ButtonWidget.builder(Messenger.s("<"), addButton -> {
            if (currentPage > 0) {
                currentPage--;
                this.updatePage();
            }
        }).dimensions(this.width / 2 - 100, this.height - 60, 40, 20).build();
        this.addDrawableChild(prePageButton);

        nextPageButton = ButtonWidget.builder(Messenger.s(">"), addButton -> {
            if ((currentPage + 1) * ITEMS_PER_PAGE < AnimationDisableList.list.size()) {
                currentPage++;
                this.updatePage();
            }
        }).dimensions(this.width / 2 + 60, this.height - 60, 40, 20).build();
        this.addDrawableChild(nextPageButton);
    }
}
