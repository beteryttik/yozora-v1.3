package ru.yozora.gui;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.Text;
import ru.yozora.Yozora;
import ru.yozora.module.Category;
import ru.yozora.module.Module;

import java.util.ArrayList;
import java.util.List;

public class ClickGuiScreen extends Screen {
    private static final int PANEL_X = 20;
    private static final int PANEL_Y = 30;
    private static final int PANEL_W = 260;
    private static final int SIDEBAR_W = 92;
    private static final int HEADER = 22;

    private Category selected = Category.OPTIMIZATION;
    private String query = "";
    private TextFieldWidget search;

    public ClickGuiScreen() {
        super(Text.literal("Yozora Client"));
    }

    @Override
    protected void init() {
        search = new TextFieldWidget(this.textRenderer, PANEL_X + 8, PANEL_Y + HEADER + 4, PANEL_W - SIDEBAR_W - 16, 14, Text.literal("Поиск"));
        search.setChangedListener(s -> query = s);
        addDrawableChild(search);
        setInitialFocus(search);
    }

    @Override
    public void render(DrawContext ctx, int mouseX, int mouseY, float delta) {
        renderBackground(ctx, mouseX, mouseY, delta);
        // Панель
        ctx.fill(PANEL_X, PANEL_Y, PANEL_X + PANEL_W, PANEL_Y + 240, 0xE010101A);
        ctx.fill(PANEL_X, PANEL_Y, PANEL_X + PANEL_W, PANEL_Y + 2, 0xFFFF6FA5);
        ctx.drawText(this.textRenderer, Text.literal("Yozora"), PANEL_X + 8, PANEL_Y + 8, 0xFFFF8FC0, true);
        ctx.drawText(this.textRenderer, Text.literal(Yozora.version()), PANEL_X + PANEL_W - 40, PANEL_Y + 8, 0xFF888899, true);

        // Сайдбар категорий
        int sy = PANEL_Y + HEADER;
        for (Category category : Category.values()) {
            boolean active = category == selected;
            ctx.fill(PANEL_X, sy, PANEL_X + SIDEBAR_W, sy + 20, active ? 0x30FF6FA5 : 0x20101018);
            ctx.drawText(this.textRenderer, Text.literal(category.title()),
                    PANEL_X + 6, sy + 6, active ? 0xFFFF8FC0 : 0xFFAAAAAA, false);
            sy += 20;
        }
        ctx.fill(PANEL_X + SIDEBAR_W, PANEL_Y, PANEL_X + SIDEBAR_W + 1, PANEL_Y + 240, 0x40FF6FA5);

        // Список модулей с поиском
        int mx = PANEL_X + SIDEBAR_W + 8;
        int my = PANEL_Y + HEADER + 24;
        for (Module module : visibleModules()) {
            int color = module.isEnabled() ? 0xFF7CFC9B : 0xFFDD5566;
            ctx.fill(mx, my, PANEL_X + PANEL_W - 8, my + 18, module.isEnabled() ? 0x30204030 : 0x20FFFFFF);
            ctx.drawText(this.textRenderer, Text.literal(module.name()), mx + 5, my + 5, color, false);
            my += 20;
            if (my > PANEL_Y + 210) break;
        }

        if (query.isEmpty()) {
            // подсказка
        }
        super.render(ctx, mouseX, mouseY, delta);
    }

    private List<Module> visibleModules() {
        List<Module> out = new ArrayList<>();
        for (Module m : Yozora.modules().byCategory(selected)) {
            if (query.isBlank() || m.name().toLowerCase().contains(query.toLowerCase())) out.add(m);
        }
        return out;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        int mx = PANEL_X + SIDEBAR_W + 8;
        int my = PANEL_Y + HEADER + 24;
        if (mouseX >= mx && mouseX <= PANEL_X + PANEL_W - 8) {
            int index = (int) ((mouseY - my) / 20);
            List<Module> modules = visibleModules();
            if (index >= 0 && index < modules.size()) {
                modules.get(index).toggle();
                return true;
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean shouldPause() {
        return false;
    }
}
