package KNUChat.Record.api;

import KNUChat.Record.application.RecordService;
import KNUChat.Record.dto.request.RecordCreateRequest;
import KNUChat.Record.dto.response.RecordIdResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RecordController {

    private final RecordService recordService;

    @PostMapping("/record")
    public ResponseEntity<RecordIdResponse> createRecord(@RequestBody @Valid RecordCreateRequest request) {
        Long id = recordService.createRecord(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(new RecordIdResponse(id));
    }
}
