
package com.example.NotemakingApp;

import com.example.NotemakingApp.Controller.NoteController;
import com.example.NotemakingApp.Model.Note;
import com.example.NotemakingApp.Repository.NoteRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(NoteController.class)
public class NotemakingAppApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NoteRepository noteRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testGetAllNotes() throws Exception {
        Note note1 = new Note();
        note1.setId(1L);
        note1.setTitle("Title1");
        note1.setContent("Content1");
        note1.setCategory("Work");

        Note note2 = new Note();
        note2.setId(2L);
        note2.setTitle("Title2");
        note2.setContent("Content2");
        note2.setCategory("Personal");

        Mockito.when(noteRepository.findAll()).thenReturn(Arrays.asList(note1, note2));

        mockMvc.perform(get("/api/notes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    public void testGetNotesByCategory() throws Exception {
        Note note = new Note();
        note.setId(1L);
        note.setTitle("Title");
        note.setContent("Content");
        note.setCategory("Work");

        Mockito.when(noteRepository.findByCategoryIgnoreCase("Work")).thenReturn(Arrays.asList(note));

        mockMvc.perform(get("/api/notes/category/Work"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].category").value("Work"));
    }

    @Test
    public void testCreateNote() throws Exception {
        Note note = new Note();
        note.setTitle("New Title");
        note.setContent("New Content");
        note.setCategory("Ideas");

        Note savedNote = new Note();
        savedNote.setId(1L);
        savedNote.setTitle("New Title");
        savedNote.setContent("New Content");
        savedNote.setCategory("Ideas");

        Mockito.when(noteRepository.save(Mockito.any(Note.class))).thenReturn(savedNote);

        mockMvc.perform(post("/api/notes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(note)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("New Title"));
    }

    @Test
    public void testUpdateNote() throws Exception {
        Note existingNote = new Note();
        existingNote.setId(1L);
        existingNote.setTitle("Old Title");
        existingNote.setContent("Old Content");
        existingNote.setCategory("Work");

        Note updatedNote = new Note();
        updatedNote.setId(1L);
        updatedNote.setTitle("Updated Title");
        updatedNote.setContent("Updated Content");
        updatedNote.setCategory("Work");

        Mockito.when(noteRepository.findById(1L)).thenReturn(Optional.of(existingNote));
        Mockito.when(noteRepository.save(Mockito.any(Note.class))).thenReturn(updatedNote);

        mockMvc.perform(put("/api/notes/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedNote)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Updated Title"));
    }

    @Test
    public void testDeleteNote() throws Exception {
        Mockito.doNothing().when(noteRepository).deleteById(1L);

        mockMvc.perform(delete("/api/notes/1"))
                .andExpect(status().isOk());

        Mockito.verify(noteRepository).deleteById(1L);
    }
}