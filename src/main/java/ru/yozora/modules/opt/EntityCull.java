package ru.yozora.modules.opt;

import ru.yozora.module.Category;
import ru.yozora.module.Module;
import ru.yozora.module.Setting;

public class EntityCull extends Module {
    public EntityCull() {
        super("EntityCull", "Сущности дальше радиуса не рендерятся", Category.OPTIMIZATION);
        register(new Setting("radius", 64, 32, 128));
    }
}
