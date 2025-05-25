package com.myblog.service.impl;

import org.springframework.stereotype.Service;

import com.myblog.entity.Post;
import com.myblog.payload.PostDto;
import com.myblog.repository.PostRepository;
import com.myblog.service.PostService;



@Service
public class PostServiceImpl implements PostService {
	private PostRepository postRepository;
	
	public PostServiceImpl(PostRepository postRepository) {
		this.postRepository=postRepository;
	}
	@Override
	public PostDto createPost(PostDto postDto) {
	
		Post post =new Post();
		post.setTitle(postDto.getTitle());
		post.setContent(postDto.getContent());
		post.setDescription(postDto.getDescription());
		
		Post newPost  = postRepository.save(post);
		 PostDto postResponse=new PostDto();
		 postResponse.setId(newPost.getId());
		 postResponse.setTitle(newPost.getTitle());
		 postResponse.setContent(newPost.getContent());
		 postResponse.setDescription(newPost.getDescription());
		 
		return postResponse;
	}
	@SuppressWarnings("deprecation")
	@Override
	public boolean isPostPresent(long id) {
		return (postRepository.existsById(id))?true:false;
	}


}
