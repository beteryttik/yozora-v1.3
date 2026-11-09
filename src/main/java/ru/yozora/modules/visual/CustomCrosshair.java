package ru.yozora.modules.visual;

import ru.yozora.module.Category;
import ru.yozora.module.Module;
import ru.yozora.module.Setting;

public class CustomCrosshair extends Module {
    public CustomCrosshair() {
        super("CustomCrosshair", "Свой стильный прицел поверх игры", Category.VISUAL);
        register(new Setting("size", 6, 2, 20));
        register(new Setting("thickness", 1, 1, 4));
        register(new Setting("gap", 3, 0, 10));
    }
}
