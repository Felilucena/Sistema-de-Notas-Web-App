package com.notesapp.repository;

import com.notesapp.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {
    List<Note> findByArchived(boolean archived);
    List<Note> findByArchivedAndCategory_NameContainingIgnoreCaseAndDueDateLessThanEqual(boolean archived, String category, LocalDate dueDate);
    List<Note> findByArchivedAndCategory_NameContainingIgnoreCase(boolean archived, String category);
    List<Note> findByArchivedAndDueDateLessThanEqual(boolean archived, LocalDate dueDate);
}