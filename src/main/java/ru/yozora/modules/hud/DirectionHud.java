package ru.yozora.modules.hud;

import net.minecraft.client.MinecraftClient;
import ru.yozora.module.Category;
import ru.yozora.module.Module;
import ru.yozora.util.HudRenderer;

public class DirectionHud extends Module {
    public DirectionHud() {
        super("DirectionHud", "Показывает направление взгляда", Category.HUD);
    }

    @Override
    public void onHud() {
        String text = HudRenderer.directionText(client);
        if (text == null) return;
        HudRenderer.draw(MinecraftClient.getInstance(), name(), text);
    }
}
