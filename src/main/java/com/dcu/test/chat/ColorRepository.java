package com.dcu.test.chat;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ColorRepository extends JpaRepository<Color, String> {
    Color findByUsername(String username);
}