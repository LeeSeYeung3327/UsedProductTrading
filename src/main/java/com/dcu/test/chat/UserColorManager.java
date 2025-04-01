package com.dcu.test.chat;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Component
public class UserColorManager {

    private final Map<String, String> userColors = new HashMap<>();
    private final String[] colors = {"#FF5733", "#33FF57", "#3357FF", "#FF33A1", "#A133FF", "#FFD733"};

    public String getUserColor(String username) {
        return userColors.computeIfAbsent(username, k -> colors[new Random().nextInt(colors.length)]);
    }
}
