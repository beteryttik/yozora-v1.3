package ru.yozora.event;

import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;
import ru.yozora.Yozora;
import ru.yozora.modules.visual.CustomCrosshair;
import ru.yozora.util.HudRenderer;

public class HudManager implements HudRenderCallback {
    private final MinecraftClient client = MinecraftClient.getInstance();

    public HudManager() {
        HudRenderCallback.EVENT.register(this);
    }

    @Override
    public void onHudRender(DrawContext context, RenderTickCounter tickCounter) {
        HudRenderer.Drawing.ctx = context;
        HudRenderer.beginFrame();
        Yozora.modules().renderHud();

        Module crosshair = Yozora.modules().byName("CustomCrosshair");
        if (crosshair != null && crosshair.isEnabled()) {
            CustomCrosshair cc = (CustomCrosshair) crosshair;
            drawCrosshair(context, cc);
        }
        HudRenderer.Drawing.ctx = null;
    }

    private void drawCrosshair(DrawContext ctx, CustomCrosshair cc) {
        int sw = ctx.getScaledWindowWidth();
        int sh = ctx.getScaledWindowHeight();
        int cx = sw / 2;
        int cy = sh / 2;
        int size = (int) cc.setting("size").value();
        int thick = (int) cc.setting("thickness").value();
        int gap = (int) cc.setting("gap").value();
        int color = 0xFF7CFC9B;
        ctx.fill(cx - gap - size, cy - thick / 2, cx - gap, cy + thick / 2, color);
        ctx.fill(cx + gap, cy - thick / 2, cx + gap + size, cy + thick / 2, color);
        ctx.fill(cx - thick / 2, cy - gap - size, cx + thick / 2, cy - gap, color);
        ctx.fill(cx - thick / 2, cy + gap, cx + thick / 2, cy + gap + size, color);
    }
}
