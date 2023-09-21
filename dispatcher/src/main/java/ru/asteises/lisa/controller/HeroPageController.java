package ru.asteises.lisa.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.asteises.lisa.model.HeroPage;
import ru.asteises.lisa.service.HeroPageService;
import ru.asteises.lisa.util.Endpoints;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(Endpoints.API)
public class HeroPageController {

    private final HeroPageService heroPageService;

    @GetMapping(Endpoints.GET_HERO_PAGE_BY_URL_FIRST_CHAR)
    public ResponseEntity<List<HeroPage>> getHeroPagesByUrlFirstChar(@PathVariable String firstChar) {
        return new ResponseEntity<>(heroPageService.getHeroPageByUrlFirstChar(firstChar), HttpStatus.OK);
    }
}
