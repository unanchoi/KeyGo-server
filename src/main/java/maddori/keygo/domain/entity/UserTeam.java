package maddori.keygo.domain.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

@Entity
@Table(name = "userteam")
@NoArgsConstructor
@Getter
public class UserTeam {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="user_id", referencedColumnName = "id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name="team_id", referencedColumnName = "id", nullable = false)
    private Team team;

    @Column(length = 6, nullable = false)
    private String nickname;

    @Column(length = 20)
    private String role;

    private String profileImagePath;

    private Boolean admin;

    @Builder
    public UserTeam(Long id, User user, Team team, String nickname, String role, String profileImagePath, Boolean admin) {
        this.id = id;
        this.user = user;
        this.team = team;
        this.nickname = nickname;
        this.role = role;
        this.profileImagePath = profileImagePath;
        this.admin = admin;
    }
}
