package ru.yozora;

import ru.yozora.config.ConfigManager;
import ru.yozora.event.CommandHandler;
import ru.yozora.event.HudManager;
import ru.yozora.event.KeyInputHandler;
import ru.yozora.module.ModuleManager;

public final class Yozora {
    private static final String NAME = "Yozora";
    private static final String VERSION = "1.3.0";

    private static ModuleManager modules;
    private static ConfigManager config;

    public static void init() {
        modules = new ModuleManager();
        config = new ConfigManager("yozora.json");
        modules.init();
        config.load(modules);
        new HudManager();
        new KeyInputHandler();
        new CommandHandler();
    }

    public static ModuleManager modules() { return modules; }
    public static ConfigManager config() { return config; }
    public static String name() { return NAME; }
    public static String version() { return VERSION; }

    public static void save() {
        if (config != null && modules != null) config.save(modules);
    }
}
