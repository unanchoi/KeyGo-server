package maddori.keygo.entity;

import maddori.keygo.domain.entity.User;
import maddori.keygo.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
public class UserTest {

    @Autowired
    UserRepository userRepository;

    @Test
    public void userCreateSuccess() throws Exception {

        User user1 =  User.builder()
                .id(1L)
                .username("user")
                .email("admin@admin.com")
                .sub("123412341234")
                .build();

        userRepository.save(user1);
    //when
        User user2 = userRepository.findById(1L).get();
    //then
        assertThat(user1.getId()).isEqualTo(user2.getId());
        assertThat(user1.getEmail()).isEqualTo(user2.getEmail());
        assertThat(user1.getSub()).isEqualTo(user2.getSub());
        assertThat(user1.getUsername()).isEqualTo(user2.getUsername());
    }
}