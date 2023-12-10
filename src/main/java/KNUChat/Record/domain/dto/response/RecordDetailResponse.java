package KNUChat.Record.domain.dto.response;

import KNUChat.Record.domain.entity.Hashtag;
import KNUChat.Record.domain.entity.Record;
import KNUChat.Record.domain.entity.Url;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class RecordDetailResponse {
    private final Long userId;
    private final String title;
    private final String achievement;
    private final String period;
    private final String description;
    private final List<String> urls;
    private final List<String> hashtags;

    public static RecordDetailResponse from(Record record, List<Url> urls, List<Hashtag> hashtags) {
        List<String> urlDtos;
        List<String> hashtagDtos;

        if (urls != null) urlDtos = urls.stream().map(Url::getLink).toList();
        else urlDtos = null;

        if (hashtags != null) hashtagDtos = hashtags.stream().map(Hashtag::getTag).toList();
        else hashtagDtos = null;

        return new RecordDetailResponse(
                record.getUserId(),
                record.getTitle(),
                record.getAchievement(),
                record.getPeriod(),
                record.getDescription(),
                urlDtos,
                hashtagDtos
        );
    }
}
