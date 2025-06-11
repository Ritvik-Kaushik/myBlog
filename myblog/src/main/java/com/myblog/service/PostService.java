package com.myblog.service;

import java.util.List;

import com.myblog.payload.PostDto;
import com.myblog.payload.PostResponse;

public interface PostService{
	PostDto createPost(PostDto postDto);
	boolean isPostPresent(long id);
	public PostResponse getAllPost(int pageNo, int pageSize,String sortBy,String sortDir);
	public PostDto getPostById(long id);
	public PostDto updatePost(PostDto postDto,long id);
	public PostDto deletePost(long id);
}
