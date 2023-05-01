package maddori.keygo.dto.reflection;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ReflectionUpdateRequestDto {

    @Size(min = 1, max = 15)
    @JsonProperty("reflection_name")
    private String reflectionName;

    @JsonProperty("reflection_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime reflectionDate;

    @Builder
    public ReflectionUpdateRequestDto(String reflectionName, LocalDateTime reflectionDate) {
        this.reflectionName = reflectionName;
        this.reflectionDate = reflectionDate;
    }
}
