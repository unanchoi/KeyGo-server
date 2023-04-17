package maddori.keygo.dto.team;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class TeamRequestDto {
    @JsonProperty("team_name")
    @Size(min = 1, max = 10)
    private String teamName;
}
