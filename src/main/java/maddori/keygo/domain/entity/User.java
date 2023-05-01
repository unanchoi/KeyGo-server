package maddori.keygo.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 6)
    private String username;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String sub;

    @Builder
    public User(Long id, String username, String email, String sub) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.sub = sub;
    }

    public void updateEmail(String email) {
        this.email = email;
    }
}
