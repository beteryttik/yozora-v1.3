package ru.yozora.mixin;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.yozora.Yozora;
import ru.yozora.module.Module;
import ru.yozora.module.Setting;
import ru.yozora.modules.opt.BlockEntityCull;

@Mixin(BlockEntityRenderDispatcher.class)
public class BlockEntityRenderDispatcherMixin {

    @Inject(method = "render(Lnet/minecraft/block/entity/BlockEntity;FLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;)V",
            at = @At("HEAD"), cancellable = true)
    private void yozora$blockEntityCull(BlockEntity blockEntity, float tickDelta, MatrixStack matrices,
                                        VertexConsumerProvider vertexConsumers, CallbackInfo ci) {
        if (Yozora.modules() == null) return;
        Module module = Yozora.modules().byName("BlockEntityCull");
        if (module == null || !module.isEnabled()) return;
        Setting radius = module.setting("radius");
        double limit = radius == null ? 64 : radius.value();

        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null || blockEntity == null || client.world == null) return;
        if (blockEntity.getCachedState().hasBlockEntity() && blockEntity.getWorld() == null) return;

        double distance = client.player.squaredDistanceTo(
                blockEntity.getPos().getX() + 0.5,
                blockEntity.getPos().getY() + 0.5,
                blockEntity.getPos().getZ() + 0.5);
        if (distance > limit * limit) {
            ci.cancel();
        }
    }
}
