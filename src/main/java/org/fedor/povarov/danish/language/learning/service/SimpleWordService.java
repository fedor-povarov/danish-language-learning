package org.fedor.povarov.danish.language.learning.service;

import org.fedor.povarov.danish.language.learning.entity.SimpleWord;

import java.util.List;

public interface SimpleWordService {
    SimpleWord getSimpleWordToLearn();

    void save(SimpleWord simpleWord);

    void delete(SimpleWord simpleWord);

    List<SimpleWord> findByEnglishContains(String value);
}
