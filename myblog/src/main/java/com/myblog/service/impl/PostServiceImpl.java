package com.myblog.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.myblog.entity.Post;
import com.myblog.exception.RescourceNotFound;
import com.myblog.payload.PostDto;
import com.myblog.payload.PostResponse;
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
	
		Post post =mapToEntity(postDto);

		Post newPost  = postRepository.save(post);
		 PostDto postResponse=mapToDto(newPost);
		
		 
		return postResponse;
	}
	@Override
	public PostResponse getAllPost(int pageNo, int pageSize,String sortBy,String sortDir){
		
		Sort sort=sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())? Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
		
		Pageable pageable=PageRequest.of(pageNo, pageSize,sort);
		Page<Post> pages=postRepository.findAll(pageable);
		List<Post> postList=pages.getContent();
		
		List<PostDto> content= postList.stream().map(post -> mapToDto(post)).collect(Collectors.toList());
		PostResponse postResponse=new PostResponse();
		postResponse.setContent(content);
		postResponse.setLast(pages.isLast());
		postResponse.setPageNo(pages.getNumber());
		postResponse.setPageSize(pages.getSize());
		postResponse.setTotalElements(pages.getTotalElements());
		postResponse.setTotalPages(pages.getTotalPages());
		
		return postResponse;
		
	}
	
	@Override
	public PostDto getPostById(long id) {
		Post post=postRepository.findById(id).orElseThrow(()-> new RescourceNotFound("Post", "Id", id)); 
		
		return mapToDto(post);
	}
	
	@Override
	public PostDto updatePost(PostDto dto, long id) {
	    Post currentPost = postRepository.findById(id)
	        .orElseThrow(() -> new RescourceNotFound("Post", "Id", id));

	    currentPost.setTitle(dto.getTitle());
	    currentPost.setContent(dto.getContent());
	    currentPost.setDescription(dto.getDescription());

	    return mapToDto(postRepository.save(currentPost));
	}
	
	@Override
	public PostDto deletePost(long id) {
		try{
			Post post= postRepository.findById(id).orElseThrow(()-> new RescourceNotFound("Post", "Id: ", id));
			postRepository.delete(post);
			return mapToDto(post); 
		}catch(RescourceNotFound e) {
			throw new RescourceNotFound("Post", "id", id);
		}
		
	}

	//map dto to Post
	private Post mapToEntity(PostDto postDto) {
		Post post =new Post();
		post.setTitle(postDto.getTitle());
		post.setContent(postDto.getContent());
		post.setDescription(postDto.getDescription());
		return post;
	}
	
	//map postDto to post
	private PostDto mapToDto(Post newPost) {
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
