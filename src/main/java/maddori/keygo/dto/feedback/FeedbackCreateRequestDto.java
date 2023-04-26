package maddori.keygo.dto.feedback;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import maddori.keygo.domain.entity.User;

@Data
@NoArgsConstructor
public class FeedbackCreateRequestDto {

    private String type;
    private String keyword;
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

