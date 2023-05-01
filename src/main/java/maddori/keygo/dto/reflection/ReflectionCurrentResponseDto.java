package maddori.keygo.dto.reflection;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class ReflectionCurrentResponseDto {

    @JsonProperty("current_reflection_id")
    private Long id;

    @JsonProperty("reflection_name")
    private String reflectionName;

    @JsonProperty("reflection_date")
    private String reflectionDate;

    @JsonProperty("reflection_status")
    private String reflectionStatus;

    @JsonProperty("reflection_keywords")
    private List<String> reflectionKeywords;

    @Builder

    public ReflectionCurrentResponseDto(Long id, String reflectionName, String reflectionDate, String reflectionStatus, List<String> reflectionKeywords) {
        this.id = id;
        this.reflectionName = reflectionName;
        this.reflectionDate = reflectionDate;
        this.reflectionStatus = reflectionStatus;
        this.reflectionKeywords = reflectionKeywords;
    }
}
