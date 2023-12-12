package KNUChat.Record.domain.api;

import KNUChat.Record.auth.application.JwtProvider;
import KNUChat.Record.domain.application.RecordService;
import KNUChat.Record.domain.dto.request.RecordCreateRequest;
import KNUChat.Record.domain.dto.request.RecordSearchRequest;
import KNUChat.Record.domain.dto.request.RecordUpdateRequest;
import KNUChat.Record.domain.dto.response.RecordDetailResponse;
import KNUChat.Record.domain.dto.response.RecordBatchResponse;
import KNUChat.Record.domain.dto.response.RecordIdResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class RecordController {

    private final RecordService recordService;
    private final JwtProvider jwtProvider;

    @PostMapping("/record")
    public ResponseEntity<RecordIdResponse> createRecord(@RequestBody @Valid RecordCreateRequest request) {
        Long id = recordService.createRecord(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(new RecordIdResponse(id));
    }

    @GetMapping("/record/{id}")
    public ResponseEntity<RecordDetailResponse> getRecord(@PathVariable Long id) {
        RecordDetailResponse response = recordService.getRecordDetailById(id);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/record")
    public ResponseEntity<RecordBatchResponse> searchRecord(@RequestBody @Valid RecordSearchRequest requestBody, HttpServletRequest request) {
        boolean isMine;
        if (requestBody.getType().equals("user"))
            isMine = checkUser(request, requestBody.getSearchWord());
        else
            isMine = false;

        RecordBatchResponse response = recordService.getRecordBatch(
                requestBody.getSearchWord(),
                requestBody.getType(),
                requestBody.getPage(),
                isMine);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    private boolean checkUser(HttpServletRequest request, String searchWord) {
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        Long userId = jwtProvider.getUserIdFromToken(jwtProvider.parseAccessToken(authHeader));

        return Long.parseLong(searchWord) == userId;
    }

    @PatchMapping("/record")
    public ResponseEntity<RecordIdResponse> updateRecord(@RequestBody @Valid RecordUpdateRequest request) {
        recordService.updateRecord(request);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/record/{id}")
    public ResponseEntity<RecordIdResponse> deleteRecord(@PathVariable Long id) {
        recordService.deleteRecordById(id);

        return ResponseEntity.status(HttpStatus.OK).body(new RecordIdResponse(id));
    }
}
