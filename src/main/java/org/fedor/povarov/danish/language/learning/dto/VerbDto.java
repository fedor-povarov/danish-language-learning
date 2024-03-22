package org.fedor.povarov.danish.language.learning.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class VerbDto {
    private UUID id;
    private String english;
    private String danish;
}
