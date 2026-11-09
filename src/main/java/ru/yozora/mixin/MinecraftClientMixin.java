package ru.yozora.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderTickCounter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.yozora.Yozora;
import ru.yozora.module.Module;
import ru.yozora.modules.opt.FpsLimiter;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {
    private long yozora$lastFrame = System.nanoTime();

    @Inject(method = "render(Lnet/minecraft/client/render/RenderTickCounter;)V", at = @At("HEAD"))
    private void yozora$limitFps(RenderTickCounter tickCounter, CallbackInfo ci) {
        if (Yozora.modules() == null) return;
        Module module = Yozora.modules().byName("FpsLimiter");
        if (module == null || !module.isEnabled()) return;

        ru.yozora.module.Setting s = module.setting("maxFps");
        int maxFps = s == null ? 240 : (int) s.value();
        long targetFrame = 1_000_000_000L / maxFps;

        long now = System.nanoTime();
        long elapsed = now - yozora$lastFrame;
        while (elapsed < targetFrame) {
            Thread.onSpinWait();
            now = System.nanoTime();
            elapsed = now - yozora$lastFrame;
        }
        yozora$lastFrame = now;
    }
}
