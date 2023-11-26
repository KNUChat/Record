package KNUChat.Record.dto.request;

import lombok.Getter;

@Getter
public class RecordSearchRequest {
    private int page;
    private String keyword;
    private String type;
}
