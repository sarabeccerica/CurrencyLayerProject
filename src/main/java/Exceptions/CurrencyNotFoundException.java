package Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *L'eccezione CurrencyNotFound viene lanciata quando la
 *moneta richiesta non è presente all'interno dei dati forniti
 *dall'API, l'eccezione è gestita dalla classe GlobalExceptionHandler
 */

@ResponseStatus(value=HttpStatus.BAD_REQUEST)
public class CurrencyNotFoundException extends Exception{
	private static final long serialVersionUID = 1L;
	
	public CurrencyNotFoundException(String message) {
		super(message);
	}
}