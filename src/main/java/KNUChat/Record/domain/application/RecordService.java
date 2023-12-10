package KNUChat.Record.domain.application;

import KNUChat.Record.domain.dto.request.RecordUpdateRequest;
import KNUChat.Record.domain.dto.response.RecordDetailResponse;
import KNUChat.Record.domain.dto.request.RecordCreateRequest;
import KNUChat.Record.domain.dto.response.RecordBatchResponse;
import KNUChat.Record.domain.dto.response.RecordResponse;
import KNUChat.Record.domain.entity.Hashtag;
import KNUChat.Record.domain.entity.Record;
import KNUChat.Record.domain.entity.Url;
import KNUChat.Record.exception.BadSearchException;
import KNUChat.Record.exception.NotFoundException;
import KNUChat.Record.domain.repository.HashtagRepository;
import KNUChat.Record.domain.repository.RecordRepository;
import KNUChat.Record.domain.repository.UrlRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    public RecordBatchResponse getPaging(String searchWord, String type, int page) {
        Pageable pageable = PageRequest.of(page, 10);
        if (type.equals("user"))
            return RecordBatchResponse.of(getPagingByUserId(searchWord, pageable));
        if (type.equals("hashtag"))
            return RecordBatchResponse.of(getPagingByHashtag(searchWord, pageable));
        if (type.equals("keyword"))
            return RecordBatchResponse.of(getPagingByKeyword(searchWord, pageable));

        throw new BadSearchException(type);
    }

    public List<RecordResponse> getPagingByUserId(String userId, Pageable pageable) {
        Page<Record> recordPage = recordRepository.findByUserId(Long.parseLong(userId), pageable);

        List<RecordResponse> recordResponses = recordPage.stream()
                .map(record -> {
                    return RecordResponse.from(record, hashtagRepository.findAllByRecordId(record.getId()));
                })
                .toList();

        return recordResponses;
    }

    public List<RecordResponse> getPagingByHashtag(String tag, Pageable pageable) {
        Page<Hashtag> hashtagPage = hashtagRepository.findByTag(tag, pageable);

        List<RecordResponse> recordResponses = hashtagPage.stream()
                .map(hashtag -> {
                    Record record = hashtag.getRecord();
                    return RecordResponse.from(record, hashtagRepository.findAllByRecordId(record.getId()));
                })
                .toList();

        return recordResponses;
    }

    public List<RecordResponse> getPagingByKeyword(String keyword, Pageable pageable) {
        Page<Record> recordPage = recordRepository.findByTitleContaining(keyword, pageable);

        List<RecordResponse> recordResponses = recordPage.stream()
                .map(record -> {
                    return RecordResponse.from(record, hashtagRepository.findAllByRecordId(record.getId()));
                })
                .toList();

        return recordResponses;
    }

    public RecordDetailResponse getRecordDetailById(Long id) {
        Record record = recordRepository.findById(id).orElseThrow(() -> new NotFoundException("Record"));
        List<Url> urls = urlRepository.findAllByRecordId(record.getId());
        List<Hashtag> hashtags = hashtagRepository.findAllByRecordId(record.getId());

        return RecordDetailResponse.from(record, urls, hashtags);
    }

    @Transactional
    public void updateRecord(RecordUpdateRequest request) {
        Record record = recordRepository.findById(request.getId()).orElseThrow(() -> new NotFoundException("Record"));
        record.update(request);

        updateHashtags(request.getHashtags(), record);
        updateUrls(request.getUrls(), record);
    }

    private void updateHashtags(List<String> hashtagDtos, Record record) {
        hashtagRepository.deleteAllByRecordId(record.getId());
        if (hashtagDtos == null) return;
        buildAllHashtagFrom(hashtagDtos, record);
    }

    private void updateUrls(List<String> urlDtos, Record record) {
        urlRepository.deleteAllByRecordId(record.getId());
        if (urlDtos == null) return;
        buildAllUrlFrom(urlDtos, record);
    }

    @Transactional
    public void deleteRecordById(Long id) {
        urlRepository.deleteAllByRecordId(id);
        hashtagRepository.deleteAllByRecordId(id);
        recordRepository.deleteById(id);
    }
}
