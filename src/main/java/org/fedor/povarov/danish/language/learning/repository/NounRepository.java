package org.fedor.povarov.danish.language.learning.repository;

import org.fedor.povarov.danish.language.learning.entity.Noun;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface NounRepository extends JpaRepository<Noun, UUID> {
    List<Noun> findByEnglishContains(String english);
}
