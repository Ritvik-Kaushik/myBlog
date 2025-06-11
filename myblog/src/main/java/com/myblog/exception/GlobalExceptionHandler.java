package com.myblog.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {
	private static final Logger logger=LoggerFactory.getLogger(GlobalExceptionHandler.class);
	@ExceptionHandler(Exception.class)
	public ResponseEntity globalExceptionHandler(Exception e, WebRequest request) {
		logger.error("Exception Occured: "+e.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad request");
	}

}
