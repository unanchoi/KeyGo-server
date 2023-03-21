package maddori.keygo.dto.reflection;

import lombok.Builder;
import lombok.Data;
import maddori.keygo.domain.ReflectionState;

import java.time.LocalDateTime;

@Data
public class ReflectionResponseDto {

    private Long id;
    private String reflectionName;
    private LocalDateTime date;
    private ReflectionState state;
    private Long teamId;

    @Builder
    public ReflectionResponseDto(Long id, String reflectionName, LocalDateTime date, ReflectionState state, Long teamId) {
        this.id = id;
        this.reflectionName = reflectionName;
        this.date = date;
        this.state = state;
        this.teamId = teamId;
    }
}
