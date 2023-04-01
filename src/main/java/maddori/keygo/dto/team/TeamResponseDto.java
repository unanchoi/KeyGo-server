package maddori.keygo.dto.team;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
public class TeamResponseDto {

    private Long id;

    @JsonProperty("team_name")
    private String teamName;

    @JsonProperty("invitation_code")
    private String invitationCode;

    @Builder
    public TeamResponseDto(Long id, String teamName, String invitationCode) {
        this.id = id;
        this.teamName = teamName;
        this.invitationCode = invitationCode;
    }
}
