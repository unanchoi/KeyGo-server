package maddori.keygo.dto.feedback;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import maddori.keygo.domain.CssType;

@Data
@NoArgsConstructor
public class FeedbackUpdateRequestDto {

    private String type;

    @Size(min=1, max = 10, message = "keyword must be between 1 and 10 characters")
    @NotEmpty
    private String keyword;
    @Size(min = 1, max = 400, message = "content must be between 1 and 400 characters")
    @NotEmpty
    private String content;

    @Builder
    public FeedbackUpdateRequestDto(String type, String keyword, String content) {
        this.type = type;
        this.keyword = keyword;
        this.content = content;
    }
}
