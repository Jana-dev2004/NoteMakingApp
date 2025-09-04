package com.example.NotemakingApp.Model;
import jakarta.persistence.*;
import java.time.LocalDateTime;
@Entity
public class Note {
	   @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    private String title;
	    @Column(columnDefinition = "TEXT")
	    private String content;
	    private LocalDateTime createdAt;
	    
	    private String category;



		@PrePersist
	    protected void onCreate() {
	        createdAt = LocalDateTime.now();
	    }

   public Note() {
        
    }


	    public String getCategory() {
			return category;
		}

		public void setCategory(String category) {
			this.category = category;
		}

		// Getters
	    public Long getId() {
	        return id;
	    }

	    public String getTitle() {
	        return title;
	    }

	    public String getContent() {
	        return content;
	    }

	    public LocalDateTime getCreatedAt() {
	        return createdAt;
	    }

	    // Setters
	    public void setId(Long id) {
	        this.id = id;
	    }

	    public void setTitle(String title) {
	        this.title = title;
	    }

	    public void setContent(String content) {
	        this.content = content;
	    }

	    public void setCreatedAt(LocalDateTime createdAt) {
	        this.createdAt = createdAt;
	    }
}
