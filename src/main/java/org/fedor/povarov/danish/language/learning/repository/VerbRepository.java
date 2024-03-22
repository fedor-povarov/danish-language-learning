package org.fedor.povarov.danish.language.learning.repository;

import org.fedor.povarov.danish.language.learning.entity.Verb;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface VerbRepository extends JpaRepository<Verb, UUID> {
    List<Verb> findByEnglishContains(String english);
}
