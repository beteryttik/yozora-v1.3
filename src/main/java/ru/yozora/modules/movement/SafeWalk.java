package ru.yozora.modules.movement;

import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import ru.yozora.module.Category;
import ru.yozora.module.Module;

public class SafeWalk extends Module {
    public SafeWalk() {
        super("SafeWalk", "Не даёт сорваться с обрыва при ходьбе", Category.MOVEMENT);
    }

    public boolean shouldSneak() {
        return isEnabled();
    }

    @Override
    public void onTick() {
        if (!isEnabled()) return;
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null || client.world == null) return;
        if (!client.player.isOnGround() || client.player.isSprinting()) return;

        Vec3d velocity = client.player.getVelocity();
        if (velocity.horizontalLengthSquared() < 1.0E-5) return;

        double nextX = client.player.getX() + velocity.x * 2;
        double nextZ = client.player.getZ() + velocity.z * 2;
        BlockPos below = BlockPos.ofFloored(nextX, client.player.getY() - 0.6, nextZ);
        if (client.world.isAir(below)) {
            client.options.sneakKey.setPressed(true);
        }
    }
}
