package com.myblog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.myblog.payload.CommentDto;
import com.myblog.payload.CommentResponse;
import com.myblog.payload.PostResponse;
import com.myblog.repository.CommentRepo;
import com.myblog.service.CommentService;
import com.myblog.util.AppConstants;

@RestController
@RequestMapping("/api/")
public class CommentController {
	@Autowired
	CommentService commentService;
	
	@PostMapping("/post/{postId}/comments")
	public ResponseEntity<CommentDto> createComment(@PathVariable(name="postId") long id,@RequestBody CommentDto dto){
		return ResponseEntity.status(HttpStatus.CREATED).body(commentService.createComment(id, dto));
	}
	@GetMapping("/post/comments")
	public ResponseEntity<CommentResponse> getAllComments(
			@RequestParam(value="pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false)int pageNo,
			@RequestParam(value="pageSize", defaultValue=AppConstants.DEFAULT_PAGE_SIZE, required=false)int pageSize,
			@RequestParam(value="sortBy", defaultValue="id", required=false)String sortBy,
			@RequestParam(value="sortDir", defaultValue="asc", required=false)String sortDir
			){
		return ResponseEntity.status(HttpStatus.OK).body(commentService.getAllComments(pageNo,pageSize,sortBy,sortDir));
	}	
	
	@GetMapping("/post/{postId}/comments")
	public ResponseEntity<List<CommentDto>> getCommentByID(@PathVariable long postId){
		return ResponseEntity.status(HttpStatus.OK).body(commentService.getCommentByPostId(postId));
	}
}
