package maddori.keygo.domain.dto.team;

import lombok.Builder;
import lombok.Data;

@Data
public class TeamResponseDto {

    private Long id;
    private String teamName;
    private String invitationCode;

    @Builder
    public TeamResponseDto(Long id, String teamName, String invitationCode) {
        this.id = id;
        this.teamName = teamName;
        this.invitationCode = invitationCode;
    }
}
