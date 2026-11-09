package ru.yozora.modules.opt;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.CloudRenderMode;
import ru.yozora.module.Category;
import ru.yozora.module.Module;

public class NoClouds extends Module {
    private CloudRenderMode previous;

    public NoClouds() {
        super("NoClouds", "Отключает слой облаков без перерисовки", Category.OPTIMIZATION);
    }

    @Override
    protected void onEnable() {
        previous = MinecraftClient.getInstance().options.getCloudsType().getValue();
        MinecraftClient.getInstance().options.getCloudsType().setValue(CloudRenderMode.OFF);
    }

    @Override
    protected void onDisable() {
        if (previous != null) {
            MinecraftClient.getInstance().options.getCloudsType().setValue(previous);
        }
    }
}
