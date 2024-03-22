package org.fedor.povarov.danish.language.learning.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@NoArgsConstructor
public class NounSuggestionDto {
    private UUID id;
    private UUID nounId;
    private Boolean isSuccess;
    private Date suggestionDate;
}
