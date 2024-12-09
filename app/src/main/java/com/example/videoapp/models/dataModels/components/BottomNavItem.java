package com.example.videoapp.models.dataModels.components;

import androidx.compose.ui.graphics.vector.ImageVector;

public class BottomNavItem {
    private final String name;
    private final String route;
    private final ImageVector icon;

    public BottomNavItem(String name, String route, ImageVector icon) {
        this.name = name;
        this.route = route;
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public String getRoute() {
        return route;
    }

    public ImageVector getIcon() {
        return icon;
    }
}

