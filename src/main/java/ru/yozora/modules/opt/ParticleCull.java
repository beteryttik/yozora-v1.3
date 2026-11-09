package ru.yozora.modules.opt;

import ru.yozora.module.Category;
import ru.yozora.module.Module;
import ru.yozora.module.Setting;

public class ParticleCull extends Module {
    public ParticleCull() {
        super("ParticleCull", "Частицы дальше радиуса не создаются", Category.OPTIMIZATION);
        register(new Setting("radius", 48, 16, 128));
    }
}
