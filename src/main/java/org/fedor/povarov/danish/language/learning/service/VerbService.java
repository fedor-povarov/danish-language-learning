package org.fedor.povarov.danish.language.learning.service;

import org.fedor.povarov.danish.language.learning.entity.Verb;

import java.util.List;

public interface VerbService {
    Verb getVerbToLearn();

    void save(Verb verb);

    void delete(Verb verb);

    List<Verb> findByEnglishContains(String text);
}
