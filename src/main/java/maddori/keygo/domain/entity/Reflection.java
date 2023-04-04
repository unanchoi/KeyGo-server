package maddori.keygo.domain.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import maddori.keygo.domain.ReflectionState;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
@DynamicInsert
@DynamicUpdate
public class Reflection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="team_id", referencedColumnName = "id", nullable = false)
    private Team team;

    @Column(name = "reflection_name", length = 15)
    private String reflectionName;

    @Column
    private LocalDateTime date;

    @Column(nullable = false, columnDefinition = "VARCHAR(255) default 'SettingRequired'")
    @Enumerated(EnumType.STRING)
    private ReflectionState state;

    public void endReflection() {
        this.state = ReflectionState.Done;
    }


    public void updateReflectionName(String reflectionName) {
        this.reflectionName = reflectionName;
    }

    public void updateReflectionDate(LocalDateTime date) {
        this.date = date;
        }
        
    public void deleteInfo() {
        this.reflectionName = null;
        this.date = null;
        this.state = ReflectionState.SettingRequired;
    }

    @Builder
    public Reflection(Long id, Team team, String reflectionName, LocalDateTime date, ReflectionState state) {
        this.id = id;
        this.team = team;
        this.reflectionName = reflectionName;
        this.date = date;
        this.state = state;
    }
}
