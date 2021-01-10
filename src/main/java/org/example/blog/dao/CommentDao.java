package org.example.blog.dao;

import org.example.blog.pojo.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CommentDao extends JpaRepository<Comment, String>, JpaSpecificationExecutor<Comment> {
}
