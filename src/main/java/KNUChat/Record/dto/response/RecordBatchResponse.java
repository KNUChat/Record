package KNUChat.Record.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class RecordBatchResponse {
    private List<RecordResponse> recordResponses;

    public static RecordBatchResponse of(List<RecordResponse> recordResponses) {
        return new RecordBatchResponse(recordResponses);
    }
}
