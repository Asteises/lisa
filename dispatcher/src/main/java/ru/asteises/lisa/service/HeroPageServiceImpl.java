package ru.asteises.lisa.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.asteises.lisa.model.HeroPage;
import ru.asteises.lisa.storage.HeroPageRepository;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class HeroPageServiceImpl implements HeroPageService {

    private final HeroPageRepository heroPageRepository;

    @Override
    public List<HeroPage> getHeroPageByUrlFirstChar(String firstChar) {
        List<HeroPage> heroPages = heroPageRepository.findAllByTitleStartingWithIgnoreCase(firstChar);
        log.info(String.valueOf(heroPages.size()));
        return heroPages;
    }
}
