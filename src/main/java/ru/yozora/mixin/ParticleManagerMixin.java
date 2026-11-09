package ru.yozora.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.particle.ParticleEffect;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import ru.yozora.Yozora;
import ru.yozora.module.Module;
import ru.yozora.module.Setting;
import ru.yozora.modules.opt.ParticleCull;

@Mixin(ParticleManager.class)
public class ParticleManagerMixin {

    @Inject(method = "addParticle(Lnet/minecraft/particle/ParticleEffect;DDDDDD)Lnet/minecraft/client/particle/Particle;",
            at = @At("HEAD"), cancellable = true)
    private void yozora$particleCull(ParticleEffect parameters, double x, double y, double z,
                                     double velocityX, double velocityY, double velocityZ,
                                     CallbackInfoReturnable<Particle> cir) {
        if (Yozora.modules() == null) return;
        Module module = Yozora.modules().byName("ParticleCull");
        if (module == null || !module.isEnabled()) return;
        Setting radius = module.setting("radius");
        double limit = radius == null ? 48 : radius.value();

        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null) return;
        double dx = x - client.player.getX();
        double dy = y - client.player.getY();
        double dz = z - client.player.getZ();
        if (dx * dx + dy * dy + dz * dz > limit * limit) {
            cir.setReturnValue(null);
        }
    }
}
