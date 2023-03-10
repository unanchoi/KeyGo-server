package maddori.keygo.dto.team;

import lombok.Builder;
import lombok.Data;

@Data
public class TeamNameResponseDto {

    private Long id;
    private String team_name;

    @Builder
    public TeamNameResponseDto(Long id, String teamName) {
        this.id = id;
        this.team_name = teamName;
    }
}
