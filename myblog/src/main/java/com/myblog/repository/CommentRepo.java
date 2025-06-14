package com.myblog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.myblog.entity.Comment;
@Repository
public interface CommentRepo extends JpaRepository<Comment, Long> {
 List<Comment> getCommentByPostId(long postId);
}
