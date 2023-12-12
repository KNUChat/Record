package KNUChat.Record.domain.application;

import KNUChat.Record.domain.dto.request.RecordCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class RecordInitializer implements ApplicationRunner {

    @Autowired
    RecordService recordService;

    @Override
    public void run(ApplicationArguments args) {

        int id = 0;

        for (int i = 0; i < 10; i++) {
            RecordCreateRequest request = new RecordCreateRequest();

            for (int j = 0; j < 5; j++) {
                request.setUserId((long) i);
                request.setTitle("TEST RECORD " + Integer.toString(id));
                request.setAchievement("test achieve" + Integer.toString(id));
                request.setPeriod("2020.01.01-2020.01.01");
                request.setDescription("test description for pagination");
                request.setHiding(j == 3);

                List<String> hashtags = new ArrayList<>();
                List<String> urls = new ArrayList<>();
                if (j % 3 == 0) {
                    hashtags = null;
                    urls = null;
                }
                else if (j % 3 == 1) {
                    urls = null;
                    for (int k = 0; k < 3; k++)
                        hashtags.add("test" + Integer.toString(id));
                }
                else {
                    hashtags = null;
                    for (int k = 0; k < 3; k++)
                        urls.add("test" + Integer.toString(id));
                }

                request.setHashtags(hashtags);
                request.setUrls(urls);

                recordService.createRecord(request);

                id += 1;
            }
        }

        for (int i = 0; i < 10; i++) {
            RecordCreateRequest request = new RecordCreateRequest();

            for (int j = 0; j < 5; j++) {
                String n = Integer.toString(id);
                request.setUserId((long) i);
                request.setTitle("TEST RECORD" + n);
                request.setAchievement("test achieve" + n);
                request.setPeriod("2020.01.01-2020.01.01");
                request.setDescription("01234567891123456789212345678931234567894123456789512345678961234567897123456789812345678991234567890123456789");
                request.setHiding(j == 3);

                List<String> hashtags = new ArrayList<>();
                List<String> urls = new ArrayList<>();
                if (j % 3 == 0) {
                    hashtags = null;
                    urls = null;
                }
                else if (j % 3 == 1) {
                    urls = null;
                    for (int k = 0; k < 3; k++)
                        hashtags.add("test" + Integer.toString(k) + n);
                }
                else {
                    hashtags = null;
                    for (int k = 0; k < 3; k++)
                        urls.add("test" + Integer.toString(k) + n);
                }

                request.setHashtags(hashtags);
                request.setUrls(urls);

                recordService.createRecord(request);

                id += 1;
            }
        }
    }
}
