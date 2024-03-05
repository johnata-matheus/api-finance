package br.com.finance.exceptions.exceptionhandler;

import org.springframework.http.HttpStatus;

public record RestErrorMessage(HttpStatus status, String message) {
  
}
