package ru.yozora.modules.hud;

import net.minecraft.client.MinecraftClient;
import ru.yozora.module.Category;
import ru.yozora.module.Module;
import ru.yozora.util.HudRenderer;

public class ClockHud extends Module {
    public ClockHud() {
        super("ClockHud", "Показывает реальное время", Category.HUD);
    }

    @Override
    public void onHud() {
        String text = HudRenderer.clockText();
        if (text == null) return;
        HudRenderer.draw(MinecraftClient.getInstance(), name(), text);
    }
}
