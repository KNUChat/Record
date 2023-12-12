package KNUChat.Record.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class RecordBatchResponse {
    private List<RecordResponse> recordResponses;
    private int totalPages;

    public static RecordBatchResponse of(List<RecordResponse> recordResponses, int totalPages) {
        return new RecordBatchResponse(recordResponses, totalPages);
    }
}
