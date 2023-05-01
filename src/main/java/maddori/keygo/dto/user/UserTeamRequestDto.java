package maddori.keygo.dto.user;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserTeamRequestDto {

    @Pattern(regexp ="/[\\{\\}\\[\\]\\/?.,;:|\\)*~`!^\\-_+<>@\\#$%&\\\\\\=\\(\\'\\\"]/")
    @Pattern(regexp = "^[^\\uD800-\\uDFFF]+$")
    @Size(min = 1, max = 6)
    private String nickname;

    private String role;
}
