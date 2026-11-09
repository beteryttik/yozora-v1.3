package ru.yozora.event;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.text.Text;
import ru.yozora.Yozora;
import ru.yozora.module.Module;

import java.util.ArrayList;
import java.util.List;

public class CommandHandler {
    public CommandHandler() {
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) ->
                dispatcher.register(
                        ClientCommandManager.literal("yozora")
                                .executes(this::listModules)
                                .then(ClientCommandManager.literal("toggle")
                                        .then(ClientCommandManager.argument("module", StringArgumentType.string())
                                                .suggests((ctx, builder) -> {
                                                    for (Module m : Yozora.modules().all()) {
                                                        builder.suggest(m.name());
                                                    }
                                                    return builder.buildFuture();
                                                })
                                                .executes(this::toggleModule)))));
    }

    private int listModules(CommandContext<FabricClientCommandSource> ctx) {
        List<String> lines = new ArrayList<>();
        lines.add("Yozora " + Yozora.version());
        for (Module m : Yozora.modules().all()) {
            lines.add((m.isEnabled() ? "[ON] " : "[OFF] ") + m.name() + " — " + m.description());
        }
        for (String line : lines) {
            ctx.getSource().sendFeedback(Text.literal(line));
        }
        return 1;
    }

    private int toggleModule(CommandContext<FabricClientCommandSource> ctx) {
        String name = StringArgumentType.getString(ctx, "module");
        Module m = Yozora.modules().byName(name);
        if (m == null) {
            ctx.getSource().sendError(Text.literal("Модуль не найден: " + name));
            return 0;
        }
        m.toggle();
        ctx.getSource().sendFeedback(Text.literal(m.name() + " -> " + (m.isEnabled() ? "ON" : "OFF")));
        return 1;
    }
}
