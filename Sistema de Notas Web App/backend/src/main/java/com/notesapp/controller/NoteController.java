package com.notesapp.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.notesapp.dto.NoteDto;
import com.notesapp.mapper.NoteMapper;
import com.notesapp.model.Note;
import com.notesapp.service.NoteService;

@RestController
@RequestMapping("/notes")
@CrossOrigin(origins = "*")
public class NoteController {

    private final NoteService service;
    private final NoteMapper mapper;

    public NoteController(NoteService service, NoteMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping
    public List<NoteDto> getNotes(@RequestParam boolean archived,
                                  @RequestParam(required = false) String category,
                                  @RequestParam(required = false) LocalDate dueDateBefore) {
        return service.getNotes(archived, category, dueDateBefore)
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public NoteDto getNote(@PathVariable Long id) {
        return service.getNoteById(id)
                .map(mapper::toDto)
                .orElseThrow();
    }

    @PostMapping
    public NoteDto create(@RequestBody NoteDto dto) {
        Note note = mapper.toEntity(dto);
        Note saved = service.createNote(note);
        return mapper.toDto(saved);
    }

    @PutMapping("/{id}")
    public NoteDto update(@PathVariable Long id, @RequestBody NoteDto dto) {
        Note updated = service.updateNote(id, mapper.toEntity(dto));
        return mapper.toDto(updated);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.deleteNote(id);
    }

    @PatchMapping("/{id}/archive")
    public void toggleArchive(@PathVariable Long id) {
        service.toggleArchived(id);
    }

    @PatchMapping("/{id}/complete")
    public void toggleComplete(@PathVariable Long id) {
        service.toggleCompleted(id);
    }

    @PatchMapping("/{id}/priority")
    public void changePriority(@PathVariable Long id, @RequestParam String direction) {
        service.changePriority(id, direction);
    }
}