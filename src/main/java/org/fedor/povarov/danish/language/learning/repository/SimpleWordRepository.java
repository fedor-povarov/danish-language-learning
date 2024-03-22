package org.fedor.povarov.danish.language.learning.repository;

import org.fedor.povarov.danish.language.learning.entity.SimpleWord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SimpleWordRepository extends JpaRepository<SimpleWord, UUID> {
    List<SimpleWord> findByEnglishContains(String english);
}
