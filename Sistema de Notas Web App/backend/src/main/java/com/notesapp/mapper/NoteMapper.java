package com.notesapp.mapper;

import com.notesapp.dto.NoteDto;
import com.notesapp.model.Category;
import com.notesapp.model.Note;
import org.springframework.stereotype.Component;
import java.time.LocalDate;

@Component
public class NoteMapper {

    public NoteDto toDto(Note note) {
        return NoteDto.builder()
                .id(note.getId())
                .title(note.getTitle())
                .description(note.getDescription())
                .dueDate(note.getDueDate() != null ? note.getDueDate().toString() : null)
                .category(note.getCategory() != null ? note.getCategory().getName() : null)
                .categoryColor(note.getCategory() != null ? note.getCategory().getColorHex() : null)
                .archived(note.isArchived())
                .completed(note.isCompleted())
                .priority(note.getPriority())
                .build();
    }

    public Note toEntity(NoteDto dto) {
        Note note = Note.builder()
                .id(dto.getId())
                .title(dto.getTitle())
                .description(dto.getDescription())
                .dueDate(dto.getDueDate() != null ? LocalDate.parse(dto.getDueDate()) : null)
                .archived(dto.isArchived())
                .completed(dto.isCompleted())
                .priority(dto.getPriority())
                .build();

        if (dto.getCategory() != null) {
            Category category = new Category();
            category.setName(dto.getCategory());
            category.setColorHex(dto.getCategoryColor());
            note.setCategory(category);
        }

        return note;
    }
}