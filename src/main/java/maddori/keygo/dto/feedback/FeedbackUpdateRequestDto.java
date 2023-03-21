package maddori.keygo.dto.feedback;

import lombok.Builder;
import lombok.Data;
import maddori.keygo.domain.CssType;

@Data
public class FeedbackUpdateRequestDto {

    private String type;
    private String keyword;
    private String content;

    @Builder
    public FeedbackUpdateRequestDto(String type, String keyword, String content) {
        this.type = type;
        this.keyword = keyword;
        this.content = content;
    }
}
