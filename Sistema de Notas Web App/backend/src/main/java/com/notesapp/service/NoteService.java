package com.notesapp.service;

import com.notesapp.model.Category;
import com.notesapp.model.Note;
import com.notesapp.repository.CategoryRepository;
import com.notesapp.repository.NoteRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class NoteService {

    private final NoteRepository noteRepository;
    private final CategoryRepository categoryRepository;

    public NoteService(NoteRepository noteRepository, CategoryRepository categoryRepository) {
        this.noteRepository = noteRepository;
        this.categoryRepository = categoryRepository;
    }

    public List<Note> getNotes(boolean archived, String category, LocalDate dueDateBefore) {
        if (category != null && !category.isEmpty() && dueDateBefore != null)
            return noteRepository.findByArchivedAndCategory_NameContainingIgnoreCaseAndDueDateLessThanEqual(archived, category, dueDateBefore);
        if (category != null && !category.isEmpty())
            return noteRepository.findByArchivedAndCategory_NameContainingIgnoreCase(archived, category);
        if (dueDateBefore != null)
            return noteRepository.findByArchivedAndDueDateLessThanEqual(archived, dueDateBefore);
        return noteRepository.findByArchived(archived);
    }

    public Optional<Note> getNoteById(Long id) {
        return noteRepository.findById(id);
    }

    public Note createNote(Note note) {
        if (note.getCategory() != null) {
            Category category = categoryRepository.findByNameIgnoreCase(note.getCategory().getName())
                    .orElseGet(() -> categoryRepository.save(note.getCategory()));
            note.setCategory(category);
        }
        return noteRepository.save(note);
    }

    public Note updateNote(Long id, Note updated) {
        Note note = noteRepository.findById(id).orElseThrow();
        note.setTitle(updated.getTitle());
        note.setDescription(updated.getDescription());
        note.setDueDate(updated.getDueDate());

        if (updated.getCategory() != null) {
            Category category = categoryRepository.findByNameIgnoreCase(updated.getCategory().getName())
                    .orElseGet(() -> categoryRepository.save(updated.getCategory()));
            note.setCategory(category);
        }

        return noteRepository.save(note);
    }

    public void deleteNote(Long id) {
        noteRepository.deleteById(id);
    }

    public void toggleArchived(Long id) {
        Note note = noteRepository.findById(id).orElseThrow();
        note.setArchived(!note.isArchived());
        noteRepository.save(note);
    }

    public void toggleCompleted(Long id) {
        Note note = noteRepository.findById(id).orElseThrow();
        note.setCompleted(!note.isCompleted());
        noteRepository.save(note);
    }

    public void changePriority(Long id, String direction) {
        Note note = noteRepository.findById(id).orElseThrow();
        int change = direction.equalsIgnoreCase("up") ? -1 : 1;
        note.setPriority(note.getPriority() + change);
        noteRepository.save(note);
    }
}