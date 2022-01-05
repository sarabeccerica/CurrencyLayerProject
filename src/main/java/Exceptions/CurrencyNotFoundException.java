package Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.BAD_REQUEST)
public class CurrencyNotFoundException extends Exception{
	private static final long serialVersionUID = 1L;
	
	public CurrencyNotFoundException(String message) {
		super(message);
	}
}