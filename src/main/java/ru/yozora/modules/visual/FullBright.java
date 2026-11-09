package ru.yozora.modules.visual;

import net.minecraft.client.MinecraftClient;
import ru.yozora.module.Category;
import ru.yozora.module.Module;
import ru.yozora.module.Setting;

public class FullBright extends Module {
    private double previous = -1;

    public FullBright() {
        super("FullBright", "Поднимает гамму до максимума", Category.VISUAL);
        register(new Setting("gamma", 16, 8, 16));
    }

    @Override
    protected void onEnable() {
        previous = MinecraftClient.getInstance().options.getGamma().getValue();
        Setting s = setting("gamma");
        MinecraftClient.getInstance().options.getGamma().setValue(s == null ? 16 : s.value());
    }

    @Override
    protected void onDisable() {
        if (previous > 0) {
            MinecraftClient.getInstance().options.getGamma().setValue(previous);
        }
    }
}
