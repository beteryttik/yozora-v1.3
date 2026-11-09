package ru.yozora.modules.opt;

import ru.yozora.module.Category;
import ru.yozora.module.Module;
import ru.yozora.module.Setting;

public class BlockEntityCull extends Module {
    public BlockEntityCull() {
        super("BlockEntityCull", "Сундуки/печи дальше радиуса не рендерятся", Category.OPTIMIZATION);
        register(new Setting("radius", 64, 32, 128));
    }
}
