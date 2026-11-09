package ru.yozora.modules.hud;

import net.minecraft.client.MinecraftClient;
import ru.yozora.module.Category;
import ru.yozora.module.Module;
import ru.yozora.util.HudRenderer;

public class SpeedHud extends Module {
    public SpeedHud() {
        super("SpeedHud", "Показывает скорость в м/с", Category.HUD);
    }

    @Override
    public void onHud() {
        String text = HudRenderer.speedText(client);
        if (text == null) return;
        HudRenderer.draw(MinecraftClient.getInstance(), name(), text);
    }
}
