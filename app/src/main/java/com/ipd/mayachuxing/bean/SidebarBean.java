package com.ipd.mayachuxing.bean;

public class SidebarBean {
    private String name;
    private int iconSelect;
    private int iconUnselect;
    private double money;
    private boolean isShow;

    public int getIconSelect() {
        return iconSelect;
    }

    public void setIconSelect(int iconSelect) {
        this.iconSelect = iconSelect;
    }

    public int getIconUnselect() {
        return iconUnselect;
    }

    public void setIconUnselect(int iconUnselect) {
        this.iconUnselect = iconUnselect;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public boolean isShow() {
        return isShow;
    }

    public void setShow(boolean show) {
        isShow = show;
    }
}
