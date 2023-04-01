package maddori.keygo.dto.feedback;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import maddori.keygo.domain.entity.Feedback;

import java.util.ArrayList;
import java.util.List;

@Data
public class FeedbackUserAndTeamResponseDto {

    private String category;

    @JsonProperty("user_feedback")
    List<FeedbackResponseDto> userFeedbackList = new ArrayList<>();

    @JsonProperty("team_feedback")
    List<FeedbackResponseDto> teamFeedbackList = new ArrayList<>();

    @Builder
    public FeedbackUserAndTeamResponseDto(String category, List<FeedbackResponseDto> userFeedbackList, List<FeedbackResponseDto> teamFeedbackList) {
        this.category = category;
        this.userFeedbackList = userFeedbackList;
        this.teamFeedbackList = teamFeedbackList;
    }
}
