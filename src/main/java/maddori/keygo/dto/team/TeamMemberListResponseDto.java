package maddori.keygo.dto.team;

import lombok.Builder;
import lombok.Data;
import maddori.keygo.dto.user.UserTeamResponseDto;

import java.util.List;

@Data
public class TeamMemberListResponseDto {

    List<UserTeamResponseDto> members;

    @Builder
    public TeamMemberListResponseDto(List<UserTeamResponseDto> userTeamResponseDtoList) {
        this.members = userTeamResponseDtoList;
    }
}
