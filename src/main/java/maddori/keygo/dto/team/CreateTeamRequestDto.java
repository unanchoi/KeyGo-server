package maddori.keygo.dto.team;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import maddori.keygo.dto.user.UserTeamRequestDto;

@Data
@NoArgsConstructor
public class CreateTeamRequestDto extends UserTeamRequestDto{
    @JsonProperty("team_name")
    @Size(min = 1, max = 10)
    private String teamName;

    public CreateTeamRequestDto(String teamName, String nickname, String role) {
        super(nickname, role);
        this.teamName = teamName;
    }
}
