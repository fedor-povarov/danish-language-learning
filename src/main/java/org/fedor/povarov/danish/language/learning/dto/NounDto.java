package org.fedor.povarov.danish.language.learning.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
public class NounDto {
    private UUID id;
    private String english;
    private String gender;
    private String singularIndefinite;
    private String singularDefinite;
    private String pluralIndefinite;
    private String pluralDefinite;
    private Boolean isIrregular;
    private List<NounSuggestionDto> nounSuggestionDtos;
}
