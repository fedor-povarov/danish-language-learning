package org.fedor.povarov.danish.language.learning.service.impl;

import lombok.RequiredArgsConstructor;
import org.fedor.povarov.danish.language.learning.entity.Verb;
import org.fedor.povarov.danish.language.learning.repository.VerbRepository;
import org.fedor.povarov.danish.language.learning.service.VerbService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class VerbServiceImpl implements VerbService {
    private final VerbRepository verbRepository;
    private final Random rand = new Random();

    @Override
    public Verb getVerbToLearn() {
        List<Verb> verbs = verbRepository.findAll();
        return verbs.get(rand.nextInt(verbs.size()));
    }

    @Override
    public void save(Verb verb) {
        verbRepository.save(verb);
    }

    @Override
    public void delete(Verb verb) {
        verbRepository.delete(verb);
    }

    @Override
    public List<Verb> findByEnglishContains(String englishText) {
        return verbRepository.findByEnglishContains(englishText);
    }
}
