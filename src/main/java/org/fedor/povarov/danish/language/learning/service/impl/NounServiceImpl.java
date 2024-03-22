package org.fedor.povarov.danish.language.learning.service.impl;

import lombok.RequiredArgsConstructor;
import org.fedor.povarov.danish.language.learning.entity.Noun;
import org.fedor.povarov.danish.language.learning.entity.NounSuggestion;
import org.fedor.povarov.danish.language.learning.repository.NounRepository;
import org.fedor.povarov.danish.language.learning.repository.NounSuggestionRepository;
import org.fedor.povarov.danish.language.learning.service.NounService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NounServiceImpl implements NounService {
    private final NounRepository nounRepository;
    private final NounSuggestionRepository nounSuggestionRepository;

    @Override
    public NounSuggestion saveNounSuggestion(Noun noun, Boolean isSuccess) {
        NounSuggestion nounSuggestion = new NounSuggestion();
        nounSuggestion.setIsSuccess(isSuccess);
        nounSuggestion.setNoun(noun);
        nounSuggestion.setSuggestionDate(new Date());
        nounSuggestionRepository.save(nounSuggestion);
        return nounSuggestion;
    }

    @Override
    public List<Noun> findByEnglishContains(String englishText) {
        return nounRepository.findByEnglishContains(englishText);
    }

    @Override
    public void delete(Noun noun) {
        nounRepository.delete(noun);
    }

    @Override
    public void save(Noun noun) {
        nounRepository.save(noun);
    }

    @Override
    public Noun getNounToLearn() {
        return nounRepository.findAll().stream()
                .filter(NounServiceImpl::filterOutVeryRecentlySuggestedNoun)
                .sorted(NounServiceImpl::compareBySuccessfulSuggestions)
                .collect(Collectors.toList())
                .stream().findFirst()
                .orElseThrow();
    }

    private static int compareBySuccessfulSuggestions(Noun noun1, Noun noun2) {
        List<NounSuggestion> nounSuggestions1 = noun1.getNounSuggestions();
        List<NounSuggestion> nounSuggestions2 = noun2.getNounSuggestions();

        long successfulNounSuggestions1Amount = nounSuggestions1.stream()
                .filter(NounSuggestion::getIsSuccess)
                .count();
        long successfulNounSuggestions2Amount = nounSuggestions2.stream()
                .filter(NounSuggestion::getIsSuccess)
                .count();
        return Long.compare(successfulNounSuggestions1Amount, successfulNounSuggestions2Amount);
    }

    private static boolean filterOutVeryRecentlySuggestedNoun(Noun noun) {
        List<NounSuggestion> nounSuggestionsOrderedByDate = noun.getNounSuggestions().stream()
                .sorted(Comparator.comparing(NounSuggestion::getSuggestionDate))
                .collect(Collectors.toList());
        Optional<NounSuggestion> optLatestNounSuggestion = nounSuggestionsOrderedByDate.stream()
                .findFirst();
        if (optLatestNounSuggestion.isEmpty()) {
            //if there is no last suggestion date, then the word can be suggested
            return true;
        }
        NounSuggestion latestNounSuggestion = optLatestNounSuggestion.orElseThrow();

        LocalDateTime latestNounSuggestionAsLocalDate = latestNounSuggestion.getSuggestionDate()
                .toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
        LocalDateTime oneMinuteAgo = LocalDateTime.now().minusMinutes(1);

        return latestNounSuggestionAsLocalDate.isBefore(oneMinuteAgo);
    }
}
