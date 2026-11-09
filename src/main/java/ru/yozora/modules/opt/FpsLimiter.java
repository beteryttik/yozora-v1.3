package ru.yozora.modules.opt;

import ru.yozora.module.Category;
import ru.yozora.module.Module;
import ru.yozora.module.Setting;

public class FpsLimiter extends Module {
    public FpsLimiter() {
        super("FpsLimiter", "Ограничивает FPS для стабильного фреймтайма", Category.OPTIMIZATION);
        register(new Setting("maxFps", 240, 30, 500));
    }
}
