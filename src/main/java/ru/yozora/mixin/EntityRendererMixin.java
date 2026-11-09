package ru.yozora.mixin;

import net.minecraft.client.render.Frustum;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import ru.yozora.Yozora;
import ru.yozora.module.Module;
import ru.yozora.module.Setting;
import ru.yozora.modules.opt.EntityCull;

@Mixin(EntityRenderer.class)
public class EntityRendererMixin {

    @Inject(method = "shouldRender(Lnet/minecraft/entity/Entity;Lnet/minecraft/client/render/Frustum;DDD)Z",
            at = @At("HEAD"), cancellable = true)
    private void yozora$entityCull(Entity entity, Frustum frustum, double x, double y, double z,
                                   CallbackInfoReturnable<Boolean> cir) {
        if (Yozora.modules() == null) return;
        Module module = Yozora.modules().byName("EntityCull");
        if (module == null || !module.isEnabled()) return;
        Setting radius = module.setting("radius");
        double limit = radius == null ? 64 : radius.value();

        net.minecraft.client.MinecraftClient client = net.minecraft.client.MinecraftClient.getInstance();
        if (client.player == null) return;
        double distance = entity.squaredDistanceTo(client.player);
        if (distance > limit * limit) {
            cir.setReturnValue(false);
        }
    }
}
