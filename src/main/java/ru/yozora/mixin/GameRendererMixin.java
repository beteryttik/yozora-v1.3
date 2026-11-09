package ru.yozora.mixin;

import net.minecraft.client.render.Camera;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import ru.yozora.Yozora;
import ru.yozora.module.Module;
import ru.yozora.modules.visual.NoHurtCam;
import ru.yozora.modules.visual.Zoom;

@Mixin(GameRenderer.class)
public class GameRendererMixin {

    @Inject(method = "tiltViewWhenHurt(Lnet/minecraft/client/util/math/MatrixStack;F)V", at = @At("HEAD"), cancellable = true)
    private void yozora$noHurtCam(MatrixStack matrices, float tickDelta, CallbackInfo ci) {
        if (Yozora.modules() == null) return;
        Module module = Yozora.modules().byName("NoHurtCam");
        if (module != null && module.isEnabled()) {
            ci.cancel();
        }
    }

    @Inject(method = "getFov(Lnet/minecraft/client/render/Camera;FZ)D", at = @At("RETURN"), cancellable = true)
    private void yozora$zoom(Camera camera, float tickDelta, boolean useFovSetting,
                             CallbackInfoReturnable<Double> cir) {
        if (Yozora.modules() == null) return;
        Module module = Yozora.modules().byName("Zoom");
        if (module instanceof Zoom zoom && zoom.zooming()) {
            cir.setReturnValue(cir.getReturnValueD() / zoom.factor());
        }
    }
}
