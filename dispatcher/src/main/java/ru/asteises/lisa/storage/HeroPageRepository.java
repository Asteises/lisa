package ru.asteises.lisa.storage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.asteises.lisa.model.HeroPage;

import java.util.List;
import java.util.UUID;

@Repository
public interface HeroPageRepository extends JpaRepository<HeroPage, UUID> {

    List<HeroPage> findAllByTitleStartingWithIgnoreCase(String firstChar);
}
