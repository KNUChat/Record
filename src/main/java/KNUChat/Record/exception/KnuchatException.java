package KNUChat.Record.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public class KnuchatException extends RuntimeException{

    private final HttpStatus httpStatus;

    @Setter
    private final String message;
}
