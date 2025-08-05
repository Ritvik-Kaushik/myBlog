package com.myblog.payload;



import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class CommentDto {
	private long id;
	@NotEmpty(message="Can not leave empty")
	private String name;
	@NotEmpty(message="Can not leave empty")
	private String body;
	@NotEmpty(message="Can not leave empty")
	@Email
	private String email;
}
