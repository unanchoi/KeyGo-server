package maddori.keygo.dto.feedback;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import maddori.keygo.domain.entity.User;

@Data
@NoArgsConstructor
public class FeedbackCreateRequestDto {

    @NotEmpty(message = "type is required")
    private String type;

    @Size(min = 1, max = 10)
    @NotEmpty
    private String keyword;

    @Size(min = 1, max = 400)
    @NotEmpty
    private String content;
    @JsonProperty("to_id")
    private Long toId;

    @Builder
    public FeedbackCreateRequestDto(String type, String keyword, String content, Long toId) {
        this.type = type;
        this.keyword = keyword;
        this.content = content;
        this.toId = toId;
    }
}

