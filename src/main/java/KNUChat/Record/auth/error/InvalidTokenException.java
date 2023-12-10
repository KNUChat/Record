package KNUChat.Record.auth.error;

import KNUChat.Record.exception.KnuchatException;
import org.springframework.http.HttpStatus;

public class InvalidTokenException extends KnuchatException {
    public InvalidTokenException(String message) {
        super(HttpStatus.UNAUTHORIZED, message);
    }
}