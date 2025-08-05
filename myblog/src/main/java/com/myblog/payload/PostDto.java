package com.myblog.payload;

import java.util.Set;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PostDto {
	private long id;
	@NotEmpty(message="Can not leave empty")
	@Size(min=2,message="Post title should have atleast 2 characters")
	private String title;
	@NotEmpty(message="Can not leave empty")
	private String description;
	@NotEmpty(message="Can not leave empty")
	private String content;
	private Set<CommentDto> comments;
}
