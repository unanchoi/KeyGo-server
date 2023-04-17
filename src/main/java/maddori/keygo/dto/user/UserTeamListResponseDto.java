package maddori.keygo.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
public class UserTeamListResponseDto {
    private Long id;

    @Size(min =1, max = 10)
    @JsonProperty("team_name")
    private String teamName;

    @Builder
    public UserTeamListResponseDto(Long id, String teamName) {
        this.id = id;
        this.teamName = teamName;
    }
}
