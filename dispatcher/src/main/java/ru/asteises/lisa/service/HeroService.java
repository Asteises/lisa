package ru.asteises.lisa.service;

import org.springframework.stereotype.Service;

import java.io.File;

@Service
public interface HeroService {

    File getHeroImage(String data);
}
