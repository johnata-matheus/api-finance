package br.com.finance.infra.exceptionhandler;

import org.springframework.http.HttpStatus;

public record RestErrorMessage(HttpStatus status, String message) {
  
}
