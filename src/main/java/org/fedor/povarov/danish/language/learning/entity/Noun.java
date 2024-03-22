package org.fedor.povarov.danish.language.learning.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@Entity
public class Noun {
    @Id
    @Column(name = "noun_id")
    @GeneratedValue
    private UUID id;
    private String english;
    private String gender;
    private String singularIndefinite;
    private String singularDefinite;
    private String pluralIndefinite;
    private String pluralDefinite;
    private Boolean isIrregular;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "noun")
    @Cascade(CascadeType.DELETE)
    private List<NounSuggestion> nounSuggestions;
}
