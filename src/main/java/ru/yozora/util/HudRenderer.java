package ru.yozora.util;

import it.unimi.dsi.fastutil.Pair;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public final class HudRenderer {
    private static final int BG = 0x90101018;
    private static final int BORDER = 0xFFFF6FA5;
    private static final int TEXT = 0xFFF0F0F8;
    private static final int PINK = 0xFFFF8FC0;
    private static final int GREEN = 0xFF7CFC9B;
    private static final int YELLOW = 0xFFFFE066;
    private static final int RED = 0xFFFF6B6B;

    private static final List<Runnable> rows = new ArrayList<>();
    private static float fps = 0;
    private static long lastFpsTime = System.nanoTime();
    private static int frames = 0;

    private HudRenderer() {}

    public static void beginFrame() {
        frames++;
        long now = System.nanoTime();
        if (now - lastFpsTime >= 1_000_000_000L) {
            fps = frames * 1_000_000_000L / (float) (now - lastFpsTime);
            frames = 0;
            lastFpsTime = now;
        }
        rows.clear();
    }

    private static int claimRow() {
        int index = rows.size();
        rows.add(() -> {});
        return index;
    }

    public static void draw(MinecraftClient client, String name, String value) {
        int row = claimRow();
        MatrixStack matrices = null;
        var context = Drawing.ctx;
        if (context == null) return;
        var tr = client.textRenderer;
        String label = name + ": ";
        int padding = 3;
        int h = 10 + padding * 2;
        int y = 4 + row * (h + 3);
        int w = tr.getWidth(label + value) + padding * 2;
        context.fill(4, y, 4 + w, y + h, BG);
        context.fill(4, y, 6, y + h, BORDER);
        int color = colorFor(name, value);
        context.drawText(tr, label, 4 + padding, y + padding, PINK, true);
        context.drawText(tr, value, 4 + padding + tr.getWidth(label), y + padding, color, true);
    }

    private static int colorFor(String name, String value) {
        if (name.equals("Fps")) {
            float f = fps;
            if (f >= 100) return GREEN;
            if (f >= 60) return YELLOW;
            return RED;
        }
        return TEXT;
    }

    public static String fpsText(MinecraftClient c) {
        if (c.world == null) return null;
        return String.format("%.0f", fps);
    }

    public static String coordinatesText(MinecraftClient c) {
        if (c.player == null) return null;
        return String.format("%.1f / %.1f / %.1f", c.player.getX(), c.player.getY(), c.player.getZ());
    }

    public static String pingText(MinecraftClient c) {
        if (c.player == null || c.getNetworkHandler() == null) return null;
        PlayerListEntry entry = c.getNetworkHandler().getPlayerListEntry(c.player.getUuid());
        if (entry == null) return null;
        return entry.getLatency() + " ms";
    }

    public static String keystrokesText(MinecraftClient c) {
        if (c.player == null) return null;
        StringBuilder sb = new StringBuilder();
        sb.append(c.options.forwardKey.isPressed() ? "W" : "w");
        sb.append(c.options.leftKey.isPressed() ? "A" : "a");
        sb.append(c.options.backKey.isPressed() ? "S" : "s");
        sb.append(c.options.rightKey.isPressed() ? "D" : "d");
        if (c.options.jumpKey.isPressed()) sb.append(" +SPC");
        return sb.toString();
    }

    public static String directionText(MinecraftClient c) {
        if (c.player == null) return null;
        return c.player.getHorizontalFacing().asString().toUpperCase();
    }

    public static String speedText(MinecraftClient c) {
        if (c.player == null) return null;
        double v = c.player.getVelocity().horizontalLength() * 20;
        return String.format("%.2f m/s", v);
    }

    public static String serverText(MinecraftClient c) {
        var entry = c.getCurrentServerEntry();
        return entry == null ? "Singleplayer" : entry.address;
    }

    public static String clockText() {
        return LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
    }

    public static String armorText(MinecraftClient c) {
        if (c.player == null) return null;
        int best = 100;
        boolean any = false;
        for (ItemStack stack : c.player.getInventory().getArmorStacks()) {
            if (stack.isEmpty() || stack.getMaxDamage() <= 0) continue;
            any = true;
            int pct = (int) Math.round(100.0 * (stack.getMaxDamage() - stack.getDamage()) / stack.getMaxDamage());
            best = Math.min(best, pct);
        }
        if (!any) return null;
        return best + "%";
    }

    // Мост для передачи DrawContext (упрощённый, без внешних зависимостей)
    public static final class Drawing {
        public static net.minecraft.client.gui.DrawContext ctx;
    }
}
