package ru.yozora.event;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;
import ru.yozora.Yozora;
import ru.yozora.gui.ClickGuiScreen;
import ru.yozora.module.Module;

public class KeyInputHandler {
    private static KeyBinding guiKey;
    private static KeyBinding zoomKey;
    private static boolean zoomHeld;

    public KeyInputHandler() {
        guiKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.yozora.gui", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_RIGHT_SHIFT, "category.yozora"));
        zoomKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.yozora.zoom", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_C, "category.yozora"));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (guiKey.wasPressed()) {
                client.setScreen(new ClickGuiScreen());
            }
            zoomHeld = zoomKey.isPressed();

            if (client.world != null && client.player != null) {
                Yozora.modules().tick();
            }
        });
    }

    public static boolean isZoomHeld() {
        return zoomHeld;
    }
}
