package org.example.blogplatform.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter@Setter
public class Post {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String tags;
    private String content;
    private String imageUrl;
    private String author;
    private boolean published;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
