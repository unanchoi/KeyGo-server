package maddori.keygo.dto.user;

import lombok.Data;
import maddori.keygo.domain.entity.Team;
import maddori.keygo.domain.entity.User;
import maddori.keygo.domain.entity.UserTeam;

@Data
public class UserTeamRequestDto {
    private String nickname;
    private String role;

    public UserTeam toEntity(User user, Team team) {
        return UserTeam.builder()
                .user(user)
                .team(team)
                .nickname(nickname)
                .role(role)
                .build();
    }
}
