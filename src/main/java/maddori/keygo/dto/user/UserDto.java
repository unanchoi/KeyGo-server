package maddori.keygo.dto.user;

import lombok.Builder;
import lombok.Data;

@Data
public class UserDto {
    private Long id;
    private String nickname;

    @Builder
    public UserDto(Long id, String nickname) {
        this.id = id;
        this.nickname = nickname;
    }
}
