package maddori.keygo.service;

import maddori.keygo.domain.entity.Reflection;
import maddori.keygo.repository.ReflectionRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
public class ReflectionServiceTest {

    @Autowired
    ReflectionService reflectionService;

    @Autowired
    ReflectionRepository reflectionRepository;
    
    @Test
    public void deleteReflectionDetailSuccess() throws Exception {
    //given
        reflectionService.deleteReflectionDetail(1L, 1L);
    //when
        Reflection reflection =  reflectionRepository.findById(1L).get();
    
    //then
        assertThat(reflection.getId()).isEqualTo(1L);
        assertThat(reflection.getTeam().getId()).isEqualTo(1L);
        assertThat(reflection.getReflectionName()).isNull();
        assertThat(reflection.getDate()).isNull();
    }
}
