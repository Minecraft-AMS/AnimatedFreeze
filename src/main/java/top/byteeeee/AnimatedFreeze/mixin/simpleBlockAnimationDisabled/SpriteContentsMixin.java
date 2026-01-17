package top.byteeeee.AnimatedFreeze.mixin.simpleBlockAnimationDisabled;

import org.spongepowered.asm.mixin.Mixin;
import top.byteeeee.AnimatedFreeze.utils.compat.DummyClass;
import top.byteeeee.annotationtoolbox.annotation.GameVersion;

@GameVersion(version = "Minecraft >= 1.21.11")
@Mixin(DummyClass.class)
public abstract class SpriteContentsMixin {}
