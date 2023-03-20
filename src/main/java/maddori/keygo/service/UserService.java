package maddori.keygo.service;

import lombok.RequiredArgsConstructor;
import maddori.keygo.domain.entity.UserTeam;
import maddori.keygo.dto.user.UserTeamListResponseDto;
import maddori.keygo.repository.UserTeamRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserTeamRepository userTeamRepository;

    @Transactional(readOnly = true)
    public List<UserTeamListResponseDto> getUserTeamList(Long userId) {
        List<UserTeam> userTeamList= userTeamRepository.findUserTeamsByUserId(userId);
        List<UserTeamListResponseDto> result = userTeamList.stream()
                        .map(r -> UserTeamListResponseDto.builder()
                                        .id(r.getTeam().getId())
                                        .teamName(r.getTeam().getTeamName())
                                        .build())
                        .collect(Collectors.toList());

        return result;
    }
}
