package com.notesapp.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    private LocalDate dueDate;

    @ManyToOne
    private Category category;

    @Builder.Default
    private boolean archived = false;

    @Builder.Default
    private boolean completed = false;

    @Builder.Default
    private int priority = 0;
}