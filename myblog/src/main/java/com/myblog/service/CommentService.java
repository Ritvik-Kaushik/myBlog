package com.myblog.service;

import java.util.List;

import com.myblog.payload.CommentDto;
import com.myblog.payload.CommentResponse;

public interface CommentService {
	public CommentDto createComment(long id,CommentDto dto);
	public CommentResponse getAllComments(int pageNo,int pageSize,String sortBy, String sortDir);
	public List<CommentDto> getCommentByPostId(long postId);
	public CommentDto getCommentById(long postId, long commentId);
	public CommentDto updateComment(long postId, long commentId,CommentDto dto);
	public CommentDto deleteComment(long postId, long commentId);
}
