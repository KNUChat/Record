package KNUChat.Record.domain.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class RecordCreateRequest {
    @NotNull(message = "사용자 ID는 필수 정보입니다.")
    private Long userId;
    @NotNull(message = "제목은 필수 정보입니다.")
    private String title;
    private String achievement;
    private String period;
    private String description;
    @NotNull(message = "공개 여부는 필수 정보입니다.")
    private boolean hiding;
    private List<String> urls;
    private List<String> hashtags;
}
