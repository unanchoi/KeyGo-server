package maddori.keygo.dto.user;

import lombok.Builder;
import lombok.Data;

@Data
public class UserDto {
    private Long id;
    private String nickName;

    @Builder
    public UserDto(Long id, String nickName) {
        this.id = id;
        this.nickName = nickName;
    }
}
