package com.myblog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.myblog.entity.Post;
import com.myblog.payload.PostDto;
@Repository
public interface PostRepository extends JpaRepository<Post, Long>{
	
}
