package ru.yozora;

import net.fabricmc.api.ClientModInitializer;

public class YozoraClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        Yozora.init();
    }
}
