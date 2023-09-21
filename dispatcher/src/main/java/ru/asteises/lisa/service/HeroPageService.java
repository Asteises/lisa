package ru.asteises.lisa.service;

import ru.asteises.lisa.model.HeroPage;

import java.util.List;

public interface HeroPageService {

    List<HeroPage> getHeroPageByUrlFirstChar(String firstChar);
}
