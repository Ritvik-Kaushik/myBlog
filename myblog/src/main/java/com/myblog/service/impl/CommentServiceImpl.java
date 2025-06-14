package com.myblog.service.impl;

import java.util.List;import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.myblog.entity.Comment;
import com.myblog.entity.Post;
import com.myblog.exception.RescourceNotFound;
import com.myblog.payload.CommentDto;
import com.myblog.payload.CommentResponse;
import com.myblog.payload.PostDto;
import com.myblog.payload.PostResponse;
import com.myblog.repository.CommentRepo;
import com.myblog.repository.PostRepository;
import com.myblog.service.CommentService;

@Service
public class CommentServiceImpl implements CommentService {
	@Autowired
	private CommentRepo commentRepo;
	@Autowired
	private PostRepository postRepository;

	@Override
	public CommentDto createComment(long id, CommentDto dto) {
		Comment comment=mapToEntity(dto);
		
			Post post=postRepository.findById(id).orElseThrow(()->new RescourceNotFound("Post", "id", id));
			
		comment.setPost(post);
		
		return mapToDto(commentRepo.save(comment));
	}
	
	
	
	public CommentResponse getAllComments(int pageNo,int pageSize,String sortBy, String sortDir) {

		Sort sort=sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())? Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
		
		Pageable pageable=PageRequest.of(pageNo, pageSize,sort);
		Page<Comment> pages=commentRepo.findAll(pageable);
		List<Comment> postList=pages.getContent();
		
		List<CommentDto> content= postList.stream().map(post -> mapToDto(post)).collect(Collectors.toList());
		CommentResponse commentResponse=new CommentResponse();
		commentResponse.setContent(content);
		commentResponse.setLast(pages.isLast());
		commentResponse.setPageNo(pages.getNumber());
		commentResponse.setPageSize(pages.getSize());
		commentResponse.setTotalElements(pages.getTotalElements());
		commentResponse.setTotalPages(pages.getTotalPages());
		
		return commentResponse;
	}
	
	
	@Override
	public List<CommentDto> getCommentByPostId(long postId) {
		List<Comment> comments =commentRepo.getCommentByPostId(postId);
		return comments.stream().map(comment ->mapToDto(comment)).collect(Collectors.toList());
	}
	
	private Comment mapToEntity(CommentDto dto) {
		Comment comment=new Comment();
		comment.setBody(dto.getBody());
		comment.setEmail(dto.getEmail());
		comment.setId(dto.getId());
		comment.setName(dto.getName());
		
		return comment;
	}
	private CommentDto mapToDto(Comment comment) {
		CommentDto dto=new CommentDto();
		dto.setBody(comment.getBody());
		dto.setEmail(comment.getEmail());
		dto.setId(comment.getId());
		dto.setName(comment.getName());
		
		return dto;
	}



	
	
	
}
