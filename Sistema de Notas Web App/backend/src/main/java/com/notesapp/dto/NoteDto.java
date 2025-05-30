package com.notesapp.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NoteDto {

    private Long id;
    private String title;
    private String description;
    private String dueDate;
    private String category;
    private String categoryColor;
    private boolean archived;
    private boolean completed;
    private int priority;
}