package KNUChat.Record.domain.entity;

import KNUChat.Record.domain.dto.request.RecordUpdateRequest;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "record")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Record {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "title")
    private String title;

    @Column(name = "achievement")
    private String achievement;

    @Column(name = "period")
    private String period;

    @Column(name = "description")
    private String description;

    @Column(name = "hiding")
    private boolean hiding;

    @Builder
    public Record(Long userId, String title, String achievement, String period, String description, boolean hiding) {
        this.userId = userId;
        this.title = title;
        this.achievement = achievement;
        this.period = period;
        this.description = description;
        this.hiding = hiding;
    }

    public Record update(RecordUpdateRequest request) {
        if (request.getTitle() != null) this.title = request.getTitle();
        if (request.getAchievement() != null) this.achievement = request.getAchievement();
        if (request.getPeriod() != null) this.period = request.getPeriod();
        if (request.getDescription() != null) this.description = request.getDescription();
        this.hiding = request.isHiding();

        return this;
    }
}
