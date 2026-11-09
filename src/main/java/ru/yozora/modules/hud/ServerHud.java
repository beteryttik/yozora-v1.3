package ru.yozora.modules.hud;

import net.minecraft.client.MinecraftClient;
import ru.yozora.module.Category;
import ru.yozora.module.Module;
import ru.yozora.util.HudRenderer;

public class ServerHud extends Module {
    public ServerHud() {
        super("ServerHud", "Показывает текущий сервер", Category.HUD);
    }

    @Override
    public void onHud() {
        String text = HudRenderer.serverText(client);
        if (text == null) return;
        HudRenderer.draw(MinecraftClient.getInstance(), name(), text);
    }
}
