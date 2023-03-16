package maddori.keygo.dto.user;

import lombok.Builder;
import lombok.Data;

@Data
public class UserTeamListResponseDto {
    private Long id;
    private String team_name;

    @Builder
    public UserTeamListResponseDto(Long id, String teamName) {
        this.id = id;
        this.team_name = teamName;
    }
}
