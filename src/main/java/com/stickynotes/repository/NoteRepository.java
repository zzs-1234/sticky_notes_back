package com.stickynotes.repository;

import com.stickynotes.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface NoteRepository extends JpaRepository<Note, Long> {
    List<Note> findByUserIdAndIsDone(Long userId, Boolean isDone);
    List<Note> findByUserIdAndCreateTime(Long userId, String createTime);
    List<Note> findByUserIdAndCategoryIdAndIsDone(Long userId, Long categoryId, Boolean isDone);
    List<Note> findByUserId(Long userId);
} 