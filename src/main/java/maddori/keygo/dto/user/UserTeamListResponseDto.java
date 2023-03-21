package maddori.keygo.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
public class UserTeamListResponseDto {
    private Long id;

    @JsonProperty("team_name")
    private String teamName;

    @Builder
    public UserTeamListResponseDto(Long id, String teamName) {
        this.id = id;
        this.teamName = teamName;
    }
}
