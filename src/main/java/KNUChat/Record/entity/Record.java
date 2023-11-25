package KNUChat.Record.entity;

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

    @Builder
    public Record(Long userId, String title, String achievement, String period, String description) {
        this.userId = userId;
        this.title = title;
        this.achievement = achievement;
        this.period = period;
        this.description = description;
    }
}
