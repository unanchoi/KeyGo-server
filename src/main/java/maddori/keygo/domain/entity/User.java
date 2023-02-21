package maddori.keygo.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 6)
    private String username;

    @Column(nullable = false)
    private String email;

    private String sub;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="usertoken", referencedColumnName = "id", nullable = false)
    private UserToken userToken;

}
