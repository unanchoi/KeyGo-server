package maddori.keygo.dto.feedback;

import lombok.Builder;
import lombok.Data;

@Data
public class FeedbackContentResponseDto {
    private Long id;
    private String keyword;
    private String content;

    @Builder
    FeedbackContentResponseDto(Long id, String keyword, String content) {
        this.id = id;
        this.keyword = keyword;
        this.content = content;
    }
}
