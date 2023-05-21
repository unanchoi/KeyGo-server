package maddori.keygo.dto.reflection;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ReflectionCurrentResponseDto {

    @JsonProperty("current_reflection_id")
    private Long id;

    @JsonProperty("reflection_name")
    private String reflectionName;

    @JsonProperty("reflection_date")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime reflectionDate;

    @JsonProperty("reflection_status")
    private String reflectionStatus;

    @JsonProperty("reflection_keywords")
    private List<String> reflectionKeywords;

    @Builder

    public ReflectionCurrentResponseDto(Long id, String reflectionName, LocalDateTime reflectionDate, String reflectionStatus, List<String> reflectionKeywords) {
        this.id = id;
        this.reflectionName = reflectionName;
        this.reflectionDate = reflectionDate;
        this.reflectionStatus = reflectionStatus;
        this.reflectionKeywords = reflectionKeywords;
    }
}
