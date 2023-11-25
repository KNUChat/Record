package KNUChat.Record.exception;

public class NotFoundException extends RuntimeException {

    public NotFoundException(String source) {
        super("존재하지 않는 " + source + "입니다.");
    }
}
