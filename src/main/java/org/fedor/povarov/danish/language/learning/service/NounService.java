package org.fedor.povarov.danish.language.learning.service;

import org.fedor.povarov.danish.language.learning.entity.Noun;
import org.fedor.povarov.danish.language.learning.entity.NounSuggestion;

import java.util.List;

public interface NounService {
    Noun getNounToLearn();

    NounSuggestion saveNounSuggestion(Noun noun, Boolean isSuccess);

    List<Noun> findByEnglishContains(String text);

    void delete(Noun noun);

    void save(Noun noun);
}
