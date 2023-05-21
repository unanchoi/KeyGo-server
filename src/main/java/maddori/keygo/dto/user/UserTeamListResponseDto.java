package maddori.keygo.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
public class UserTeamListResponseDto {
    private Long id;

    @JsonProperty("team_name")
    private String teamName;

    private String nickname;

    @Builder
    public UserTeamListResponseDto(Long id, String teamName, String nickname) {
        this.id = id;
        this.teamName = teamName;
        this.nickname = nickname;
    }
}
