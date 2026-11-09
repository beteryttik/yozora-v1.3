package ru.yozora.module;

public class Setting {
    private final String name;
    private double value;
    private final double min, max;

    public Setting(String name, double value, double min, double max) {
        this.name = name;
        this.value = value;
        this.min = min;
        this.max = max;
    }

    public String name() { return name; }
    public double value() { return value; }
    public void set(double v) { value = Math.max(min, Math.min(max, v)); }
}
