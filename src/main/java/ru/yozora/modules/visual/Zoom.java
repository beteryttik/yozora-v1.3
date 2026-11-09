package ru.yozora.modules.visual;

import ru.yozora.event.KeyInputHandler;
import ru.yozora.module.Category;
import ru.yozora.module.Module;
import ru.yozora.module.Setting;

public class Zoom extends Module {
    public Zoom() {
        super("Zoom", "Зум на удержании клавиши C", Category.VISUAL);
        register(new Setting("factor", 4, 2, 10));
    }

    public boolean zooming() {
        return KeyInputHandler.isZoomHeld();
    }

    public double factor() {
        Setting s = setting("factor");
        return s == null ? 4 : s.value();
    }
}
