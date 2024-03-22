package org.fedor.povarov.danish.language.learning.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Data
@NoArgsConstructor
@Entity
public class NounSuggestion {
    @Id
    @Column(name = "noun_suggestion_id")
    @GeneratedValue
    private UUID id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "noun_id")
    private Noun noun;
    private Boolean isSuccess;
    private Date suggestionDate;
}
