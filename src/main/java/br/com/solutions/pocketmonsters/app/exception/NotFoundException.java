package br.com.solutions.pocketmonsters.app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {
    public NotFoundException(Long id) {
        super(String.format("Registro com o id %d não encontrado.", id));
    }
}
