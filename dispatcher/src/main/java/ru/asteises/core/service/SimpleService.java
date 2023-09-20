package ru.asteises.core.service;

import org.springframework.stereotype.Service;
import ru.asteises.model.HeroDto;

@Service
public class SimpleService {

    public HeroDto getHero() {
        return HeroDto.builder()
                .name("Simple Hero")
                .build();
    }
}
