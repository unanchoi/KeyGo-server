package maddori.keygo.dto.reflection;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
public class ReflectionUpdateResponseDto {

    private Long id;
    @JsonProperty("reflection_name")
    private String reflectionName;
    @JsonProperty("date")
    private String reflectionDate;
    @JsonProperty("state")
    private String reflectionState;
    @JsonProperty("team_id")
    private Long teamId;

    @Builder

    public ReflectionUpdateResponseDto(Long id, String reflectionName, String reflectionDate, String reflectionState, Long teamId) {
        this.id = id;
        this.reflectionName = reflectionName;
        this.reflectionDate = reflectionDate;
        this.reflectionState = reflectionState;
        this.teamId = teamId;
    }
}
