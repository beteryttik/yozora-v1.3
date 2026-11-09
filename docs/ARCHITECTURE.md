# Архитектура Yozora

## Слои
- core — модульная система, конфиг, события
- modules — 4 категории: OPTIMIZATION, VISUAL, HUD, MOVEMENT
- mixin — перехват рендера/игрового цикла
- gui — ClickGUI на Screen

## Рендер HUD
Единая точка: HudManager, зарегистрирован на HudRenderCallback.
Все HUD-модули рисуются через него — один стек, минимальный overhead.

## Оптимизации
Все cull-модули работают на раннем отсечении (head-cancel в mixin),
чтобы дорогой код не выполнялся вовсе.
