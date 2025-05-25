package com.myblog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myblog.payload.PostDto;
import com.myblog.service.PostService;

@RestController
@RequestMapping("/api/posts")
public class PostController {
	@Autowired
	private PostService postService;
	
	@PostMapping()
	public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto){
		
		
		
		return ResponseEntity.status(HttpStatus.CREATED).body(postService.createPost(postDto));
	}
}
