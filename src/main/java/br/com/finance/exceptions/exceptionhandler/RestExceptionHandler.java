package br.com.finance.exceptions.exceptionhandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.finance.exceptions.ExpenseNotFoundException;
import br.com.finance.exceptions.RevenueNotFoundException;
import br.com.finance.exceptions.UserExistsException;
import br.com.finance.exceptions.UserNotFoundException;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(UserNotFoundException.class)
  private ResponseEntity<RestErrorMessage> userNotFoundHandler(UserNotFoundException exception) {
    RestErrorMessage responseException = new RestErrorMessage(HttpStatus.NOT_FOUND, "Usuário não encontrado");
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseException);
  }

  @ExceptionHandler(UserExistsException.class)
  private ResponseEntity<RestErrorMessage> userExistsHandler(UserExistsException exception) {
    RestErrorMessage responseException = new RestErrorMessage(HttpStatus.CONFLICT, "Usuário já cadastrado");
    return ResponseEntity.status(HttpStatus.CONFLICT).body(responseException);
  }

  @ExceptionHandler(ExpenseNotFoundException.class)
  private ResponseEntity<RestErrorMessage> expenseNotFoundHandler(ExpenseNotFoundException exception){
    RestErrorMessage responseException = new RestErrorMessage(HttpStatus.NOT_FOUND, "Despesa não encontrada");

    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseException);
  }

  @ExceptionHandler(RevenueNotFoundException.class)
  private ResponseEntity<RestErrorMessage> revenueNotFoundHandler(RevenueNotFoundException exception){
    RestErrorMessage responseException = new RestErrorMessage(HttpStatus.NOT_FOUND, "Receita não encontrada");

    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseException);
  }

}
