package org.fedor.povarov.danish.language.learning.service.impl;

import lombok.RequiredArgsConstructor;
import org.fedor.povarov.danish.language.learning.entity.SimpleWord;
import org.fedor.povarov.danish.language.learning.repository.SimpleWordRepository;
import org.fedor.povarov.danish.language.learning.service.SimpleWordService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class SimpleWordServiceImpl implements SimpleWordService {
    private final SimpleWordRepository simpleWordRepository;
    private final Random rand = new Random();

    @Override
    public SimpleWord getSimpleWordToLearn() {
        List<SimpleWord> simpleWords = simpleWordRepository.findAll();
        return simpleWords.get(rand.nextInt(simpleWords.size()));
    }

    @Override
    public void save(SimpleWord simpleWord) {
        simpleWordRepository.save(simpleWord);
    }

    @Override
    public void delete(SimpleWord simpleWord) {
        simpleWordRepository.delete(simpleWord);
    }

    @Override
    public List<SimpleWord> findByEnglishContains(String englishText) {
        return simpleWordRepository.findByEnglishContains(englishText);
    }
}
