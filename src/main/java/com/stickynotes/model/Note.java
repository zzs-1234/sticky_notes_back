package com.stickynotes.model;

import lombok.Data;
import javax.persistence.*;

@Data
@Entity
@Table(name = "notes")
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String content;
    private String color;
    private String createTime;
    private Boolean isDone;

    private Long categoryId;
} 