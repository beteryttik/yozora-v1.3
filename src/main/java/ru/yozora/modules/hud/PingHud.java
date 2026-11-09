package ru.yozora.modules.hud;

import net.minecraft.client.MinecraftClient;
import ru.yozora.module.Category;
import ru.yozora.module.Module;
import ru.yozora.util.HudRenderer;

public class PingHud extends Module {
    public PingHud() {
        super("PingHud", "Показывает пинг до сервера", Category.HUD);
    }

    @Override
    public void onHud() {
        String text = HudRenderer.pingText(client);
        if (text == null) return;
        HudRenderer.draw(MinecraftClient.getInstance(), name(), text);
    }
}
