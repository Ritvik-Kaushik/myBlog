package com.myblog.exception;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.myblog.payload.ErrorDetails;

@ControllerAdvice
public class GlobalExceptionHandler {
	private static final Logger logger=LoggerFactory.getLogger(GlobalExceptionHandler.class);
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorDetails> globalExceptionHandler(Exception ex, WebRequest wr) {
		logger.error("Exception Occured: "+ex.getMessage());
		ErrorDetails errorDetails=new ErrorDetails(new Date(), ex.getMessage(), wr.getDescription(false));
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDetails);
	}
	@ExceptionHandler(RescourceNotFound.class)
	public ResponseEntity<ErrorDetails> handleResourceNotFound(RescourceNotFound ex, WebRequest wr){
		logger.error("Exception Occured: "+ex.getMessage());
		ErrorDetails errorDetails=new ErrorDetails(new Date(), ex.getMessage(), wr.getDescription(false));
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDetails);
		
		
	}

}
