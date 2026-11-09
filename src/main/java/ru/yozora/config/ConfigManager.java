package ru.yozora.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import net.fabricmc.loader.api.FabricLoader;
import ru.yozora.module.Module;
import ru.yozora.module.ModuleManager;
import ru.yozora.module.Setting;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ConfigManager {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private final Path file;

    public ConfigManager(String name) {
        this.file = FabricLoader.getInstance().getConfigDir().resolve(name);
    }

    public void save(ModuleManager manager) {
        JsonObject root = new JsonObject();
        JsonObject mods = new JsonObject();
        for (Module m : manager.all()) {
            JsonObject entry = new JsonObject();
            entry.addProperty("enabled", m.isEnabled());
            JsonObject values = new JsonObject();
            for (Setting s : m.settings()) values.addProperty(s.name(), s.value());
            entry.add("settings", values);
            mods.add(m.name(), entry);
        }
        root.add("modules", mods);
        try {
            Files.createDirectories(file.getParent());
            Files.writeString(file, GSON.toJson(root));
        } catch (IOException ignored) {
        }
    }

    public void load(ModuleManager manager) {
        if (!Files.exists(file)) return;
        try {
            JsonObject root = GSON.parse(Files.readString(file)).getAsJsonObject();
            if (!root.has("modules")) return;
            JsonObject mods = root.getAsJsonObject("modules");
            for (Module m : manager.all()) {
                if (!mods.has(m.name())) continue;
                JsonObject entry = mods.getAsJsonObject(m.name());
                if (entry.has("enabled")) m.setEnabled(entry.get("enabled").getAsBoolean());
                if (entry.has("settings")) {
                    JsonObject values = entry.getAsJsonObject("settings");
                    for (Setting s : m.settings()) {
                        if (values.has(s.name())) s.set(values.get(s.name()).getAsDouble());
                    }
                }
            }
        } catch (Exception ignored) {
        }
    }
}
