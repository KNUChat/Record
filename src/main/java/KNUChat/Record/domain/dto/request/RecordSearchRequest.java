package KNUChat.Record.domain.dto.request;

import lombok.Getter;

@Getter
public class RecordSearchRequest {
    private int page;
    private String searchWord;
    private String type;
}
