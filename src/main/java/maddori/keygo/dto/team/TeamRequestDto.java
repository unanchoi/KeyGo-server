package maddori.keygo.dto.team;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class TeamRequestDto {
    @JsonProperty("team_name")
    private String teamName;
}
