package maddori.keygo.entity;

import jakarta.persistence.Id;
import maddori.keygo.domain.entity.User;
import maddori.keygo.domain.entity.UserToken;
import maddori.keygo.repository.UserRepository;
import maddori.keygo.repository.UserTokenRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
public class UserTokenTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserTokenRepository userTokenRepository;


    @Test
    public void createUserTokenSuccess() throws Exception {
    //given
        User user1 =  User.builder()
                .id(1L)
                .username("user")
                .email("admin@admin.com")
                .sub("123412341234")
                .build();
        userRepository.save(user1);

        UserToken userToken1 = UserToken.builder()
                .id(1L)
                .user(user1)
                .refreshToken("refreshToken")
                .build();
        userTokenRepository.save(userToken1);
    //when
        UserToken userToken2 = userTokenRepository.findById(1L).get();

    //then
        assertThat(userToken1.getId()).isEqualTo(userToken2.getId());
        assertThat(userToken1.getRefreshToken()).isEqualTo(userToken2.getRefreshToken());
        assertThat(userToken1.getUser().getId()).isEqualTo(userToken2.getUser().getId());
    }

}
