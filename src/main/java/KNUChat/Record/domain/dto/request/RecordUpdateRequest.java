package KNUChat.Record.domain.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.List;

@Getter
public class RecordUpdateRequest {
    @NotNull(message = "Record ID는 필수 정보입니다.")
    private Long id;
    private String title;
    private String achievement;
    private String period;
    private String description;
    @NotNull(message = "공개 여부는 필수 정보입니다.")
    private boolean hiding;
    private List<String> urls;
    private List<String> hashtags;
}
