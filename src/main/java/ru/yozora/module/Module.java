package ru.yozora.module;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class Module {
    private final String name;
    private final String description;
    private final Category category;
    private boolean enabled;
    private final List<Setting> settings = new ArrayList<>();

    protected Module(String name, String description, Category category) {
        this.name = name;
        this.description = description;
        this.category = category;
    }

    public String name() { return name; }
    public String description() { return description; }
    public Category category() { return category; }
    public boolean isEnabled() { return enabled; }

    public void setEnabled(boolean state) {
        if (enabled == state) return;
        enabled = state;
        if (state) onEnable(); else onDisable();
        ru.yozora.Yozora.save();
    }

    public void toggle() {
        setEnabled(!enabled);
    }

    protected void onEnable() {}
    protected void onDisable() {}
    public void onTick() {}
    public void onHud() {}

    protected void register(Setting setting) {
        settings.add(setting);
    }

    public List<Setting> settings() {
        return Collections.unmodifiableList(settings);
    }

    public Setting setting(String name) {
        for (Setting s : settings) if (s.name().equalsIgnoreCase(name)) return s;
        return null;
    }
}
