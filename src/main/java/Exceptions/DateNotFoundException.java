package Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *L'eccezione DateNotFound viene lanciata quando la
 *data richiesta non e' presente all'interno dei dati forniti
 *dall'API, l'eccezione e' gestita dalla classe GlobalExceptionHandler
 * @author Beccerica Sara
 */

@ResponseStatus(value=HttpStatus.BAD_REQUEST)
public class DateNotFoundException extends Exception{
	private static final long serialVersionUID = 1L;
	
	public DateNotFoundException(String message) {
		super(message);
	}
}
