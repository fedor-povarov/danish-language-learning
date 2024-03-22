package org.fedor.povarov.danish.language.learning.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.UUID;

@Data
@NoArgsConstructor
@Entity
public class SimpleWord {
    @Id
    @Column(name = "simple_word_id")
    @GeneratedValue
    private UUID id;
    private String english;
    private String danish;
}
