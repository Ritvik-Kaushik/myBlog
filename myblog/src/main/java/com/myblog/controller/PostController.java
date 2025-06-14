package com.myblog.controller;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.myblog.payload.PostDto;
import com.myblog.payload.PostResponse;
import com.myblog.service.PostService;
import com.myblog.util.AppConstants;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/posts")
public class PostController {
	@Autowired
	private PostService postService;
	
	@PostMapping()
	public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto){
		
		
		
		return ResponseEntity.status(HttpStatus.CREATED).body(postService.createPost(postDto));
	}
	
	@GetMapping
	public ResponseEntity<PostResponse> getAllPosts(
			@RequestParam(value="pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false)int pageNo,
			@RequestParam(value="pageSize", defaultValue=AppConstants.DEFAULT_PAGE_SIZE, required=false)int pageSize,
			@RequestParam(value="sortBy", defaultValue="id", required=false)String sortBy,
			@RequestParam(value="sortDir", defaultValue="asc", required=false)String sortDir
			){
		return ResponseEntity.status(HttpStatus.OK).body(postService.getAllPost(pageNo,pageSize,sortBy,sortDir));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<PostDto> getPostById(@PathVariable(name="id")long id) {
		return ResponseEntity.status(HttpStatus.OK).body(postService.getPostById(id));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<PostDto>updatePost(@Valid @RequestBody PostDto postDto, @PathVariable(name="id") long id){
		return ResponseEntity.status(HttpStatus.OK).body(postService.updatePost(postDto,id));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<PostDto> deletePost(@PathVariable(name="id")long id){
		return ResponseEntity.status(HttpStatus.OK).body(postService.deletePost(id));
	}
}
