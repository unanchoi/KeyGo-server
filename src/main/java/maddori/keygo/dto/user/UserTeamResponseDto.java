package maddori.keygo.dto.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import maddori.keygo.dto.team.TeamResponseDto;

@Data
public class UserTeamResponseDto {
    private Long id;
    private String nickname;
    private String role;

    @JsonProperty("profile_image_path")
    private String profileImagePath;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("user_id")
    private Long userId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private TeamResponseDto team;

    @Builder
    public UserTeamResponseDto(Long id, String nickname, String role, String profileImagePath, Long userId, TeamResponseDto team) {
        this.id = id;
        this.nickname = nickname;
        this.role = role;
        this.profileImagePath = profileImagePath;
        this.userId = userId;
        this.team = team;
    }

    @Builder
    public UserTeamResponseDto(Long id, String nickname, String role, String profileImagePath) {
        this.id = id;
        this.nickname = nickname;
        this.role = role;
        this.profileImagePath = profileImagePath;
    }
}
