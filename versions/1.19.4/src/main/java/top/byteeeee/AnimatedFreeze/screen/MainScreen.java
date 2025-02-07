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
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.util.math.MatrixStack;
//#if MC>=12000
//$$ import net.minecraft.client.gui.DrawContext;
//#endif
import net.minecraft.util.Formatting;

import top.byteeeee.AnimatedFreeze.AnimatedFreezeClient;
import top.byteeeee.AnimatedFreeze.config.AnimatedFreezeConfig;
import top.byteeeee.AnimatedFreeze.helpers.AnimationDisableList;
import top.byteeeee.AnimatedFreeze.utils.Messenger;

@SuppressWarnings("FieldCanBeLocal")
@Environment(EnvType.CLIENT)
public class MainScreen extends Screen {
    private TextFieldWidget inputField;
    private ButtonWidget addButton;
    private ButtonWidget subtractButton;
    private ButtonWidget viewListButton;

    public MainScreen() {
        super(Messenger.s("AnimatedFreeze"));
    }

    @Override
    protected void init() {
        inputField = new TextFieldWidget(
            this.textRenderer, this.width / 2 - 100, this.height / 2 - 10, 150, 20,
            Messenger.s(Messenger.tr("animatedFreeze.ui.input").getString()));
        inputField.setMaxLength(50);
        this.addSelectableChild(inputField);

        addButton = ButtonWidget.builder(Messenger.s("+").formatted(Formatting.GREEN), addButton -> {
            AnimationDisableList.list.add(inputField.getText());
            AnimatedFreezeConfig.saveListToConfig(AnimationDisableList.list);
            AnimatedFreezeClient.minecraftClient.reloadResources();
        }).dimensions(this.width / 2 + 60, this.height / 2 - 10, 20, 20).build();
        this.addDrawableChild(addButton);

        subtractButton = ButtonWidget.builder(Messenger.s("-").formatted(Formatting.RED), addButton -> {
            AnimationDisableList.list.remove(inputField.getText());
            AnimatedFreezeConfig.saveListToConfig(AnimationDisableList.list);
            AnimatedFreezeClient.minecraftClient.reloadResources();
        }).dimensions(this.width / 2 + 90, this.height / 2 - 10, 20, 20).build();
        this.addDrawableChild(subtractButton);

        viewListButton = ButtonWidget.builder(Messenger.s(Messenger.tr("animatedFreeze.ui.viewList").getString()), addButton -> {
            if (this.client != null) {
                this.client.setScreen(new ListScreen(this));
            }
        }).dimensions(this.width / 2 - 40, this.height / 2 + 20, 80, 20).build();
        this.addDrawableChild(viewListButton);
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
        inputField.render(matrices, mouseX, mouseY, delta);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == 256) {
            this.close();
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }
}
