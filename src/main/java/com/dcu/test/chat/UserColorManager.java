package com.dcu.test.chat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class UserColorManager {

    private final ColorRepository colorRepository;
    private final String[] colors = {"#FF5733", "#33FF57", "#3357FF", "#FF33A1", "#A133FF", "#FFD733"};

    @Autowired
    public UserColorManager(ColorRepository colorRepository) {
        this.colorRepository = colorRepository;
    }

    public String getUserColor(String username) {
        // 사용자 이름 변경 시에도 색상을 유지
        Color color = colorRepository.findByUsername(username);

        if (color != null) {
            return color.getColor();  // 이미 저장된 색상 반환
        } else {
            // 새로운 색상 생성 후 저장
            String newColor = colors[new Random().nextInt(colors.length)];
            colorRepository.save(new Color(username, newColor));  // 색상 저장
            return newColor;
        }
    }
}
