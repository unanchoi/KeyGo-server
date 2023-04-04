package maddori.keygo.dto.feedback;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import maddori.keygo.domain.entity.User;
import maddori.keygo.dto.user.UserDto;

@Data
public class FeedbackCreateResponseDto {

    private Long id;
    private String type;
    private String keyword;
    private String content;

    @JsonProperty("to_user")
    private UserDto toUser;

    @Builder
    public FeedbackCreateResponseDto(Long id, String type, String keyword, String content, UserDto toUser) {
        this.id = id;
        this.type = type;
        this.keyword = keyword;
        this.content = content;
        this.toUser = toUser;
    }
}
