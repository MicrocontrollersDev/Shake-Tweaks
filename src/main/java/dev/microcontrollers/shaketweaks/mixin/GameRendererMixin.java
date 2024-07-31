package dev.microcontrollers.shaketweaks.mixin;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import dev.microcontrollers.shaketweaks.config.ShakeTweaksConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.FilledMapItem;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(GameRenderer.class)
public class GameRendererMixin {
    @WrapWithCondition(method = "renderWorld", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/GameRenderer;bobView(Lnet/minecraft/client/util/math/MatrixStack;F)V"))
    private boolean disableScreenBobbing(GameRenderer instance, MatrixStack matrices, float tickDelta) {
        return !ShakeTweaksConfig.CONFIG.instance().disableScreenBobbing;
    }

    @WrapWithCondition(method = "renderHand", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/GameRenderer;bobView(Lnet/minecraft/client/util/math/MatrixStack;F)V"))
    private boolean disableHandBobbing(GameRenderer instance, MatrixStack matrices, float tickDelta) {
        if (ShakeTweaksConfig.CONFIG.instance().disableHandBobbing) return false;
        if (ShakeTweaksConfig.CONFIG.instance().disableMapBobbing) {
            ClientPlayerEntity entity = MinecraftClient.getInstance().player;
            if (entity != null) {
                ItemStack mainHand = entity.getMainHandStack();
                ItemStack offHand = entity.getOffHandStack();
                if (mainHand != null && mainHand.getItem() instanceof FilledMapItem) return false;
                return offHand == null || !(offHand.getItem() instanceof FilledMapItem);
            }
        }
        return true;
    }

    @WrapWithCondition(method = "renderHand", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/GameRenderer;tiltViewWhenHurt(Lnet/minecraft/client/util/math/MatrixStack;F)V"))
    private boolean disableHandDamageTilt(GameRenderer instance, MatrixStack matrices, float tickDelta) {
        return !ShakeTweaksConfig.CONFIG.instance().disableHandDamage;
    }

    @WrapWithCondition(method = "renderWorld", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/GameRenderer;tiltViewWhenHurt(Lnet/minecraft/client/util/math/MatrixStack;F)V"))
    private boolean disableScreenDamageTilt(GameRenderer instance, MatrixStack matrices, float tickDelta) {
        return !ShakeTweaksConfig.CONFIG.instance().disableScreenDamage;
    }

}
