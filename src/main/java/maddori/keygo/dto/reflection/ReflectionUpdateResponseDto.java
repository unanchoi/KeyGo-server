package maddori.keygo.dto.reflection;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReflectionUpdateResponseDto {

    private Long id;
    @JsonProperty("reflection_name")
    private String reflectionName;
    @JsonProperty("date")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime reflectionDate;
    @JsonProperty("state")
    private String reflectionState;
    @JsonProperty("team_id")
    private Long teamId;

    @Builder

    public ReflectionUpdateResponseDto(Long id, String reflectionName, LocalDateTime reflectionDate, String reflectionState, Long teamId) {
        this.id = id;
        this.reflectionName = reflectionName;
        this.reflectionDate = reflectionDate;
        this.reflectionState = reflectionState;
        this.teamId = teamId;
    }
}
