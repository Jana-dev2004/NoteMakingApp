package com.example.NotemakingApp.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.NotemakingApp.Model.Note;
import com.example.NotemakingApp.Repository.NoteRepository;
@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/notes")
public class NoteController {

    @Autowired
    private NoteRepository noteRepository;

    @GetMapping
    public List<Note> getAllNotes() {
        return noteRepository.findAll();
    }
    

    @GetMapping("/category/{category}")
       public List<Note> getNotesByCategory(@PathVariable String category) {
           return noteRepository.findByCategoryIgnoreCase(category);
       }


    @PostMapping
    public Note createNote(@RequestBody Note note) {
        return noteRepository.save(note);
    }

    @PutMapping("/{id}")
    public Note updateNote(@PathVariable Long id, @RequestBody Note updatedNote) {
        return noteRepository.findById(id)
                .map(note -> {
                    note.setTitle(updatedNote.getTitle());
                    note.setContent(updatedNote.getContent());
                    return noteRepository.save(note);
                }).orElseThrow(() -> new RuntimeException("Note not found"));
    }

    @DeleteMapping("/{id}")
    public void deleteNote(@PathVariable Long id) {
        noteRepository.deleteById(id);
    }
}