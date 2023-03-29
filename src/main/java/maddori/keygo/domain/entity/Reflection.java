package maddori.keygo.domain.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import maddori.keygo.domain.ReflectionState;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
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

    @Builder
    public Reflection(Long id, Team team, String reflectionName, LocalDateTime date, ReflectionState state) {
        this.id = id;
        this.team = team;
        this.reflectionName = reflectionName;
        this.date = date;
        this.state = state;
    }

}
