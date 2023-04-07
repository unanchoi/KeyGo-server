package maddori.keygo.service;

import maddori.keygo.domain.entity.Reflection;
import maddori.keygo.dto.reflection.ReflectionUpdateRequestDto;
import maddori.keygo.dto.reflection.ReflectionUpdateResponseDto;
import maddori.keygo.repository.ReflectionRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
public class ReflectionServiceTest {

    @Autowired
    ReflectionRepository reflectionRepository;

    @Autowired
    ReflectionService reflectionService;

    @Test
    public void updateReflectionDetailSuccess() throws Exception {
    //given
        Reflection reflection = reflectionRepository.findById(1L).get();
        ReflectionUpdateRequestDto dto = ReflectionUpdateRequestDto.builder()
                        .reflectionDate(LocalDateTime.now())
                        .reflectionName("맛쟁이 사과처럼 회고")
                        .build();

    //when
        ReflectionUpdateResponseDto responseDto = reflectionService.updateReflectionDetail(
                1L, reflection.getId(), dto);

    //then
        assertThat(responseDto.getReflectionName()).isEqualTo(dto.getReflectionName());
        assertThat(responseDto.getReflectionDate()).isEqualTo(dto.getReflectionDate().toString());
        assertThat(responseDto.getId()).isEqualTo(reflection.getId());
        assertThat(responseDto.getTeamId()).isEqualTo(reflection.getTeam().getId());
        assertThat(responseDto.getReflectionState()).isEqualTo(reflection.getState().toString());
 }
 
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
