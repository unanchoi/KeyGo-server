package maddori.keygo.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 6)
    private String username;

    @Column(nullable = false)
    private String email;

    private String sub;

    @OneToOne(mappedBy = "user")
    private UserToken userToken;

}
