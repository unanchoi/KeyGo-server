package maddori.keygo.dto.feedback;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import maddori.keygo.dto.reflection.ReflectionResponseDto;
import maddori.keygo.dto.user.UserDto;

import java.util.ArrayList;
import java.util.List;

@Data
public class FeedbackFromMeToMemberResponseDto {
    @JsonProperty("to_user")
    private UserDto toUser;

    private ReflectionResponseDto reflection;

    @JsonProperty("Continue")
    private List<FeedbackContentResponseDto> continueType = new ArrayList<>();

    @JsonProperty("Stop")
    private List<FeedbackContentResponseDto> stopType = new ArrayList<>();

    @Builder
    public FeedbackFromMeToMemberResponseDto(UserDto toUser, ReflectionResponseDto reflection, List<FeedbackContentResponseDto> continueType, List<FeedbackContentResponseDto> stopType) {
        this.toUser = toUser;
        this.reflection = reflection;
        this.continueType = continueType;
        this.stopType = stopType;
    }
}
