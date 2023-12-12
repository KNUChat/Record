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
                .hiding(request.isHiding())
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

    public RecordBatchResponse getRecordBatch(String searchWord, String type, int page, boolean isMine) {
        Pageable pageable = PageRequest.of(page, 10);
        Page<Record> resultPage = switch (type) {
            case "user" -> getPagingByUserId(searchWord, pageable, isMine);
            case "hashtag" -> getPagingByHashtag(searchWord, pageable);
            case "keyword" -> getPagingByKeyword(searchWord, pageable);
            default -> throw new BadSearchException(type);
        };

        int totalPages = resultPage.getTotalPages();
        List<RecordResponse> recordResponses = resultPage.stream()
                .map(record -> RecordResponse.from(record, hashtagRepository.findAllByRecordId(record.getId())))
                .toList();

        return RecordBatchResponse.of(recordResponses, totalPages);
    }

    public Page<Record> getPagingByUserId(String userId, Pageable pageable, boolean isMine) {
        Page<Record> resultPage;
        if (isMine)
            resultPage = recordRepository.findByUserId(Long.parseLong(userId), pageable);
        else
            resultPage = recordRepository.findByHidingIsFalseAndUserId(Long.parseLong(userId), pageable);

        return resultPage;
    }

    public Page<Record> getPagingByHashtag(String tag, Pageable pageable) {
        Page<Record> resultPage;
        resultPage = hashtagRepository.findRecordByHidingIsFalseAndTag(tag, pageable);

        return resultPage;
    }

    public Page<Record> getPagingByKeyword(String keyword, Pageable pageable) {
        Page<Record> resultPage;
        resultPage = recordRepository.findByHidingIsFalseAndTitleContaining(keyword, pageable);

        return resultPage;
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
