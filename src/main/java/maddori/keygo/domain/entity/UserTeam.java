package maddori.keygo.domain.entity;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "userteam")
@NoArgsConstructor
public class UserTeam {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="user_id", referencedColumnName = "id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name="team_id", referencedColumnName = "id", nullable = false)
    private Team team;

    @Column(length = 6)
    private String nickname;

    @Column(length = 20)
    private String role;

    private String profileImagePath;

    private Byte admin;


}
