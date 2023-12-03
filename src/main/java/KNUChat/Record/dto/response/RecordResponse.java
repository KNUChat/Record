package KNUChat.Record.dto.response;

import KNUChat.Record.entity.Hashtag;
import KNUChat.Record.entity.Record;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RecordResponse {
    private Long recordId;
    private Long userId;
    private String title;
    private String period;
    private String Description;
    private List<String> hashtags;

    public static RecordResponse from(Record record, List<Hashtag> hashtags) {
        String description;
        if (record.getDescription().length() <= 100) description = record.getDescription();
        else description = record.getDescription().substring(0, 100);

        List<String> hashtagDtos;
        if (hashtags != null) hashtagDtos = hashtags.stream().map(Hashtag::getTag).toList();
        else hashtagDtos = null;

        return new RecordResponse(
                record.getId(),
                record.getUserId(),
                record.getTitle(),
                record.getPeriod(),
                description,
                hashtagDtos
        );
    }
}
