package com.example.NotemakingApp.Repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.NotemakingApp.Model.Note;


public interface NoteRepository extends JpaRepository<Note, Long> {
	   List<Note> findByCategoryIgnoreCase(String category);
}

