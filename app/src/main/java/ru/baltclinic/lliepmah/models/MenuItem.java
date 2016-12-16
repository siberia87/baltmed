package ru.baltclinic.lliepmah.models;

import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.StringRes;


public class MenuItem {

    private int id;
    private int titleRes;
    private int drawableRes;
    private boolean selected = false;

    private int badgeValue = 0;

    public MenuItem(@IdRes int id, @StringRes int titleRes, @DrawableRes int drawableRes) {
        this.id = id;
        this.titleRes = titleRes;
        this.drawableRes = drawableRes;
    }

    public void setBadgeValue(int badgeValue) {
        this.badgeValue = badgeValue;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public int getId() {
        return id;
    }

    public int getTitleRes() {
        return titleRes;
    }

    public int getDrawableRes() {
        return drawableRes;
    }

    public boolean isSelected() {
        return selected;
    }

    public int getBadgeValue() {
        return badgeValue;
    }
}
