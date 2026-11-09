package ru.yozora.modules.hud;

import net.minecraft.client.MinecraftClient;
import ru.yozora.module.Category;
import ru.yozora.module.Module;
import ru.yozora.util.HudRenderer;

public class CoordinatesHud extends Module {
    public CoordinatesHud() {
        super("CoordinatesHud", "Показывает координаты игрока", Category.HUD);
    }

    @Override
    public void onHud() {
        String text = HudRenderer.coordinatesText(client);
        if (text == null) return;
        HudRenderer.draw(MinecraftClient.getInstance(), name(), text);
    }
}
