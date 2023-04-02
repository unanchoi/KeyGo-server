package maddori.keygo.dto.user;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserTeamRequestDto {
    private String nickname;
    private String role;
}
