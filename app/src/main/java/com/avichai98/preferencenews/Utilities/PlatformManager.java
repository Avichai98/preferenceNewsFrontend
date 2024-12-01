package com.avichai98.preferencenews.Utilities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PlatformManager {
    private ArrayList<String> platforms;
    private Map<String, Boolean> platformState;

    public PlatformManager() {
        platforms = new ArrayList<>();
        platformState = new HashMap<>();

        // Add all platforms to the list and initialize their state as false
        addAllPlatforms();
    }

    public ArrayList<String> getPlatforms() {
        return platforms;
    }

    public Map<String, Boolean> getPlatformState() {
        return platformState;
    }

    public void setPlatformState(String platform, boolean isSelected) {
        if (platformState.containsKey(platform)) {
            platformState.put(platform, isSelected);
        }
    }

    private void addAllPlatforms() {
        String[] predefinedPlatforms = {"Email", "Telegram"};

        for (String platform : predefinedPlatforms) {
            platforms.add(platform);
            platformState.put(platform, false); // Default state: not selected
        }
    }
}
