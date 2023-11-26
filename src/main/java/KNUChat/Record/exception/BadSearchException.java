package KNUChat.Record.exception;

public class BadSearchException extends RuntimeException{
    public BadSearchException(String source) {
        super(source + "는 제공하지 않는 조건입니다.");
    }
}
