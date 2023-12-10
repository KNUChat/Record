package KNUChat.Record.auth.error;

public class JwtSecurityException extends RuntimeException {

    public JwtSecurityException() {
        super("서버 내부 오류. JWT를 처리할 수 없습니다.");
    }
}