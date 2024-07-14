package br.com.finance.infra.exceptionhandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.finance.domain.account.exceptions.AccountNotfoundException;
import br.com.finance.domain.expense.exceptions.ExpenseNotFoundException;
import br.com.finance.domain.revenue.exceptions.RevenueNotFoundException;
import br.com.finance.domain.user.exceptions.EmailExistsException;
import br.com.finance.domain.user.exceptions.EmailNotFoundException;
import br.com.finance.domain.user.exceptions.TokenNotFountException;
import br.com.finance.domain.user.exceptions.UserNotExistsException;
import br.com.finance.domain.user.exceptions.UserNotFoundException;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(UserNotFoundException.class)
  private ResponseEntity<RestErrorMessage> userNotFoundHandler(UserNotFoundException exception) {
    RestErrorMessage responseException = new RestErrorMessage(HttpStatus.NOT_FOUND, "Email ou senha incorretos");
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseException);
  }

  @ExceptionHandler(EmailExistsException.class)
  private ResponseEntity<RestErrorMessage> emailExistsHandler(EmailExistsException exception) {
    RestErrorMessage responseException = new RestErrorMessage(HttpStatus.CONFLICT, "Email já cadastrado");
    return ResponseEntity.status(HttpStatus.CONFLICT).body(responseException);
  }

  @ExceptionHandler(EmailNotFoundException.class)
  private ResponseEntity<RestErrorMessage> userEmailNotFoundHandler(EmailNotFoundException exception) {
    RestErrorMessage responseException = new RestErrorMessage(HttpStatus.NOT_FOUND, "Email não encontrado");
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseException);
  }

  @ExceptionHandler(UserNotExistsException.class)
  private ResponseEntity<RestErrorMessage> userNotExistsHandler(UserNotExistsException exception) {
    RestErrorMessage responseException = new RestErrorMessage(HttpStatus.NOT_FOUND, "Usuário não encontrado");
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseException);
  }


  @ExceptionHandler(TokenNotFountException.class)
  private ResponseEntity<RestErrorMessage> userEmailNotFoundHandler(TokenNotFountException exception) {
    RestErrorMessage responseException = new RestErrorMessage(HttpStatus.NOT_FOUND, "Token não encontrado ou inválido");
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseException);
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

  @ExceptionHandler(AccountNotfoundException.class)
  private ResponseEntity<RestErrorMessage> AccountNotFoundHandler(AccountNotfoundException exception){
    RestErrorMessage responseException = new RestErrorMessage(HttpStatus.NOT_FOUND, "Conta não encontrada");

    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseException);
  }

}
