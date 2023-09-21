package ru.asteises.lisa.service;

import org.springframework.stereotype.Service;
import ru.asteises.lisa.model.HeroDto;

@Service
public class SimpleService {

    public HeroDto getHero() {
        return HeroDto.builder()
                .name("Simple Hero")
                .build();
    }
}
