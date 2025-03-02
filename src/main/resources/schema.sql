-- 创建数据库
CREATE DATABASE IF NOT EXISTS sticky_notes DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE sticky_notes;

-- 创建分类表
CREATE TABLE IF NOT EXISTS categories (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL COMMENT '分类名称',
    color VARCHAR(50) NOT NULL COMMENT '分类颜色',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='便签分类表';

-- 创建便签表
CREATE TABLE IF NOT EXISTS notes (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    content TEXT NOT NULL COMMENT '便签内容',
    color VARCHAR(50) NOT NULL COMMENT '便签颜色',
    create_time VARCHAR(20) NOT NULL COMMENT '创建日期',
    is_done BOOLEAN DEFAULT FALSE COMMENT '是否完成',
    category_id BIGINT NOT NULL COMMENT '分类ID',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    FOREIGN KEY (category_id) REFERENCES categories(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='便签表';

-- 插入默认分类数据
INSERT INTO categories (name, color) VALUES
    ('工作', '#ff7eb9'),
    ('学习', '#7afcff'),
    ('生活', '#feff9c'),
    ('娱乐', '#fff740'),
    ('健康', '#98ff98'),
    ('其他', '#dcdcdc'); 