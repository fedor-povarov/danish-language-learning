package org.fedor.povarov.danish.language.learning.repository;

import org.fedor.povarov.danish.language.learning.entity.NounSuggestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface NounSuggestionRepository extends JpaRepository<NounSuggestion, UUID> {
}
