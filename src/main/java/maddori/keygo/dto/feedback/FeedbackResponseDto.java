package maddori.keygo.dto.feedback;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import maddori.keygo.dto.user.UserDto;

@Data
public class FeedbackResponseDto {

    private Long id;
    private String type;
    private String keyword;
    private String content;
    @JsonProperty("from_user")
    private UserDto fromUser;

    @Builder
    public FeedbackResponseDto(Long id, String type, String keyword, String content, UserDto fromUser) {
        this.id = id;
        this.type = type;
        this.keyword = keyword;
        this.content = content;
        this.fromUser = fromUser;
    }
}
