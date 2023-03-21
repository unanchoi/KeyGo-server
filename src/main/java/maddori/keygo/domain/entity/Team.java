package maddori.keygo.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "current_reflection_id", referencedColumnName = "id")
    private Reflection currentReflection;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recent_reflection_id", referencedColumnName = "id")
    private Reflection recentReflection;

    @Column(length = 6, nullable = false)
    private String invitationCode;

    @Column(length = 10, nullable = false)
    private String teamName;
}
