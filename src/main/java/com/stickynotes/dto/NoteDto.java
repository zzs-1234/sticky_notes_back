package com.stickynotes.dto;

import lombok.Data;

@Data
public class NoteDto {
    private String content; // 便签内容
    private String color;   // 便签颜色
    private String createTime; // 创建日期
    private Long categoryId; // 分类ID
    private Boolean isDone; // 是否完成
} 