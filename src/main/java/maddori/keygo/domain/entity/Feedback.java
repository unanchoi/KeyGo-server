package maddori.keygo.domain.entity;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import maddori.keygo.domain.CssType;

@Entity
@NoArgsConstructor
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="team_id", referencedColumnName = "id", nullable = false)
    private Team team;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="reflection_id", referencedColumnName = "id", nullable = false)
    private Reflection reflection;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="from_id", referencedColumnName = "id")
    private User fromUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="to_id", referencedColumnName = "id", nullable = false)
    private User toId;

    @Enumerated(EnumType.STRING)
    private CssType type;

    @Column(length = 10, nullable = false)
    private String keyword;

    @Column(length = 400, nullable = false)
    private String content;

    @Column(length = 200)
    private String startContent;

}
