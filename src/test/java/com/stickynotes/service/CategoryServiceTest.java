package com.stickynotes.service;

import com.stickynotes.model.Category;
import com.stickynotes.repository.CategoryRepository;
import com.stickynotes.service.impl.CategoryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    private Category category;

    @BeforeEach
    void setUp() {
        category = new Category();
        category.setId(1L);
        category.setName("工作");
        category.setColor("#ff7eb9");
    }

    @Test
    void getAllCategories_ShouldReturnAllCategories() {
        List<Category> categories = Arrays.asList(category);
        when(categoryRepository.findAll()).thenReturn(categories);

        List<Category> result = categoryService.getAllCategories();

        assertEquals(1, result.size());
        assertEquals("工作", result.get(0).getName());
        verify(categoryRepository).findAll();
    }

    @Test
    void getCategoryById_ShouldReturnCategory() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));

        Category result = categoryService.getCategoryById(1L);

        assertEquals("工作", result.getName());
        verify(categoryRepository).findById(1L);
    }

    @Test
    void createCategory_ShouldReturnNewCategory() {
        when(categoryRepository.save(any(Category.class))).thenReturn(category);

        Category result = categoryService.createCategory(category);

        assertEquals("工作", result.getName());
        verify(categoryRepository).save(category);
    }

    @Test
    void updateCategory_ShouldReturnUpdatedCategory() {
        Category updatedCategory = new Category();
        updatedCategory.setName("学习");
        updatedCategory.setColor("#7afcff");

        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(categoryRepository.save(any(Category.class))).thenReturn(updatedCategory);

        Category result = categoryService.updateCategory(1L, updatedCategory);

        assertEquals("学习", result.getName());
        verify(categoryRepository).findById(1L);
        verify(categoryRepository).save(any(Category.class));
    }

    @Test
    void deleteCategory_ShouldDeleteCategory() {
        doNothing().when(categoryRepository).deleteById(1L);

        categoryService.deleteCategory(1L);

        verify(categoryRepository).deleteById(1L);
    }
} 