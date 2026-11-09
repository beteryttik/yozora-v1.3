package ru.yozora.modules.hud;

import net.minecraft.client.MinecraftClient;
import ru.yozora.module.Category;
import ru.yozora.module.Module;
import ru.yozora.util.HudRenderer;

public class ArmorHud extends Module {
    public ArmorHud() {
        super("ArmorHud", "Показывает прочность брони", Category.HUD);
    }

    @Override
    public void onHud() {
        String text = HudRenderer.armorText(client);
        if (text == null) return;
        HudRenderer.draw(MinecraftClient.getInstance(), name(), text);
    }
}
