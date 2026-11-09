package ru.yozora.modules.hud;

import net.minecraft.client.MinecraftClient;
import ru.yozora.module.Category;
import ru.yozora.module.Module;
import ru.yozora.util.HudRenderer;

public class KeystrokesHud extends Module {
    public KeystrokesHud() {
        super("KeystrokesHud", "Показывает нажатия клавиш WASD/Space", Category.HUD);
    }

    @Override
    public void onHud() {
        String text = HudRenderer.keystrokesText(client);
        if (text == null) return;
        HudRenderer.draw(MinecraftClient.getInstance(), name(), text);
    }
}
