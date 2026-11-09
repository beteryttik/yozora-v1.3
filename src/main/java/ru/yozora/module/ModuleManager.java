package ru.yozora.module;

import net.minecraft.client.MinecraftClient;
import ru.yozora.modules.hud.*;
import ru.yozora.modules.movement.SafeWalk;
import ru.yozora.modules.opt.*;
import ru.yozora.modules.visual.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ModuleManager {
    private final List<Module> modules = new ArrayList<>();

    public void init() {
        // Оптимизация
        add(new ParticleCull());
        add(new EntityCull());
        add(new BlockEntityCull());
        add(new NoClouds());
        add(new FpsLimiter());
        // Визуал
        add(new Zoom());
        add(new FullBright());
        add(new NoHurtCam());
        add(new CustomCrosshair());
        // HUD
        add(new FpsHud());
        add(new CoordinatesHud());
        add(new PingHud());
        add(new KeystrokesHud());
        add(new DirectionHud());
        add(new SpeedHud());
        add(new ServerHud());
        add(new ClockHud());
        add(new ArmorHud());
        // Движение
        add(new SafeWalk());

        for (Module m : modules) if (m.category() == Category.HUD) m.setEnabled(true);
    }

    private void add(Module m) {
        modules.add(m);
    }

    public List<Module> all() {
        return Collections.unmodifiableList(modules);
    }

    public List<Module> byCategory(Category category) {
        List<Module> out = new ArrayList<>();
        for (Module m : modules) if (m.category() == category) out.add(m);
        return out;
    }

    public Module byName(String name) {
        for (Module m : modules) if (m.name().equalsIgnoreCase(name)) return m;
        return null;
    }

    public void tick() {
        for (Module m : modules) {
            if (m.isEnabled()) m.onTick();
        }
    }

    public void renderHud() {
        for (Module m : modules) {
            if (m.isEnabled() && m.category() == Category.HUD) m.onHud();
        }
    }
}
