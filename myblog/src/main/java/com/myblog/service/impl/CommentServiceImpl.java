package com.myblog.service.impl;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.myblog.entity.Comment;
import com.myblog.entity.Post;
import com.myblog.exception.BlogAPIException;
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
	@Autowired
	private ModelMapper mapper;

	@Override
	public CommentDto createComment(long id, CommentDto dto) {
		Comment comment = mapToEntity(dto);

		Post post = postRepository.findById(id).orElseThrow(() -> new RescourceNotFound("Post", "id", id));

		comment.setPost(post);

		return mapToDto(commentRepo.save(comment));
	}

	public CommentResponse getAllComments(int pageNo, int pageSize, String sortBy, String sortDir) {

		Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
				: Sort.by(sortBy).descending();

		Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
		Page<Comment> pages = commentRepo.findAll(pageable);
		List<Comment> postList = pages.getContent();

		List<CommentDto> content = postList.stream().map(post -> mapToDto(post)).collect(Collectors.toList());
		CommentResponse commentResponse = new CommentResponse();
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
		List<Comment> comments = commentRepo.getCommentByPostId(postId);
		return comments.stream().map(comment -> mapToDto(comment)).collect(Collectors.toList());
	}

	@Override
	public CommentDto getCommentById(long postId, long commentId) {

		// retrieving post
		Post post = postRepository.findById(postId).orElseThrow(() -> new RescourceNotFound("Post", "Id", postId));

		// retrieving Comment
		Comment comment = commentRepo.findById(commentId)
				.orElseThrow(() -> new RescourceNotFound("Comment", "Id", commentId));

		// check if comment belongs to same post or not

		if (!comment.getPost().getId().equals(post.getId())) {
			throw new BlogAPIException("This comment doesn't belongs to this Post.", HttpStatus.NOT_FOUND);
		}

		return mapToDto(comment);
	}

	@Override
	public CommentDto updateComment(long postId, long commentId, CommentDto dto) {
		// retrieving post
		Post post = postRepository.findById(postId).orElseThrow(() -> new RescourceNotFound("Post", "Id", postId));

		// retrieving Comment
		Comment comment = commentRepo.findById(commentId)
				.orElseThrow(() -> new RescourceNotFound("Comment", "Id", commentId));

		// check if comment belongs to same post or not
		if (!comment.getPost().getId().equals(post.getId())) {
			throw new BlogAPIException("This comment doesn't belongs to this Post.", HttpStatus.NOT_FOUND);
		}

		comment.setBody(dto.getBody());
		comment.setEmail(dto.getEmail());
		comment.setName(dto.getName());
		comment.setPost(post);

		return mapToDto(commentRepo.save(comment));
	}

	@Override
	public CommentDto deleteComment(long postId, long commentId) {

		// retrieving post
		Post post = postRepository.findById(postId).orElseThrow(() -> new RescourceNotFound("Post", "Id", postId));

		// retrieving Comment
		Comment comment = commentRepo.findById(commentId)
				.orElseThrow(() -> new RescourceNotFound("Comment", "Id", commentId));

		// check if comment belongs to same post or not
		if (!comment.getPost().getId().equals(post.getId())) {
			throw new BlogAPIException("This comment doesn't belongs to this Post.", HttpStatus.NOT_FOUND);
		}

		commentRepo.delete(comment);
		return mapToDto(comment);
	}

	private Comment mapToEntity(CommentDto dto) {
		Comment comment = mapper.map(dto, Comment.class);
//		comment.setBody(dto.getBody());
//		comment.setEmail(dto.getEmail());
//		comment.setId(dto.getId());
//		comment.setName(dto.getName());

		return comment;
	}

	private CommentDto mapToDto(Comment comment) {
		CommentDto dto = mapper.map(comment, CommentDto.class);
//		dto.setBody(comment.getBody());
//		dto.setEmail(comment.getEmail());
//		dto.setId(comment.getId());
//		dto.setName(comment.getName());
//		
		return dto;
	}

}
