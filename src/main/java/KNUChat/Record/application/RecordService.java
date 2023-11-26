package KNUChat.Record.application;

import KNUChat.Record.dto.request.RecordCreateRequest;
import KNUChat.Record.dto.response.RecordDetailResponse;
import KNUChat.Record.entity.Hashtag;
import KNUChat.Record.entity.Record;
import KNUChat.Record.entity.Url;
import KNUChat.Record.exception.NotFoundException;
import KNUChat.Record.repository.HashtagRepository;
import KNUChat.Record.repository.RecordRepository;
import KNUChat.Record.repository.UrlRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecordService {

    @Autowired
    private final RecordRepository recordRepository;
    @Autowired
    private final UrlRepository urlRepository;
    @Autowired
    private final HashtagRepository hashtagRepository;

    public Long createRecord(RecordCreateRequest request) {
        Record record = buildRecordFrom(request);
        if (request.getUrls() != null) buildAllUrlFrom(request.getUrls(), record);
        if (request.getHashtags() != null) buildAllHashtagFrom(request.getHashtags(), record);

        return record.getId();
    }

    public Record buildRecordFrom(RecordCreateRequest request) {
        Record record = Record.builder()
                .userId(request.getUserId())
                .title(request.getTitle())
                .achievement(request.getAchievement())
                .period(request.getPeriod())
                .description(request.getDescription())
                .build();

        recordRepository.save(record);

        return record;
    }

    public void buildAllUrlFrom(List<String> urlDtos, Record record) {
        List<Url> urls = urlDtos.stream()
                .map(urlDto -> Url.builder()
                        .link(urlDto)
                        .record(record)
                        .build())
                .toList();

        urlRepository.saveAll(urls);
    }

    public void buildAllHashtagFrom(List<String> hashtagDtos, Record record) {
        List<Hashtag> hashtags = hashtagDtos.stream()
                .map(hashtagDto -> Hashtag.builder()
                        .tag(hashtagDto)
                        .record(record)
                        .build())
                .toList();

        hashtagRepository.saveAll(hashtags);
    }

    public RecordDetailResponse getRecordDetailById(Long id) {
        Record record = recordRepository.findById(id).orElseThrow(() -> new NotFoundException("Record"));
        List<Url> urls = urlRepository.findAllByRecordId(record.getId());
        List<Hashtag> hashtags = hashtagRepository.findAllByRecordId(record.getId());

        return RecordDetailResponse.from(record, urls, hashtags);
    }

    @Transactional
    public void deleteRecordById(Long id) {
        urlRepository.deleteAllByRecordId(id);
        hashtagRepository.deleteAllByRecordId(id);
        recordRepository.deleteById(id);
    }
}
