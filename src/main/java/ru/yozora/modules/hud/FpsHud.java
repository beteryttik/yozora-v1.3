package ru.yozora.modules.hud;

import net.minecraft.client.MinecraftClient;
import ru.yozora.module.Category;
import ru.yozora.module.Module;
import ru.yozora.util.HudRenderer;

public class FpsHud extends Module {
    public FpsHud() {
        super("FpsHud", "Показывает FPS с цветовой индикацией", Category.HUD);
    }

    @Override
    public void onHud() {
        String text = HudRenderer.fpsText(client);
        if (text == null) return;
        HudRenderer.draw(MinecraftClient.getInstance(), name(), text);
    }
}
