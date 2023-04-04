package maddori.keygo.service;

import com.nimbusds.jose.shaded.gson.JsonArray;
import com.nimbusds.jose.shaded.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import maddori.keygo.common.exception.CustomException;
import maddori.keygo.dto.auth.LoginRequestDto;
import maddori.keygo.dto.auth.LoginResponseDto;
import maddori.keygo.repository.FeedbackRepository;
import maddori.keygo.repository.UserRepository;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.security.PublicKey;

import static maddori.keygo.common.response.ResponseCode.*;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final FeedbackRepository feedbackRepository;

    @Transactional
    public void deleteUser(Long userId) {
        userRepository.delete(userRepository.findById(userId).orElseThrow(() ->
                                                    new CustomException(USER_NOT_EXIST)));
        feedbackRepository.fromUserSetNull(userId);
    }

    @Transactional
    public LoginResponseDto appleLogin(LoginRequestDto loginRequestDto) {
        // 공개키로 identity token 검증 (공개키 가져오기 -> 검증)
        JsonArray publicKeyList = getApplePublicKeyList();
        PublicKey publicKey = validateIdentityToken(loginRequestDto.getToken());

        return null;
    }

    private PublicKey validateIdentityToken(String token) {

        return null;
    }

    // apple social login public key 정보 가져오기
    public JsonArray getApplePublicKeyList() {
        try {
            // apple의 공개키 리스트 string으로 가져오기
            URL url = new URL("https://appleid.apple.com/auth/keys");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            StringBuffer result = new StringBuffer();
            String line = "";
            while ((line = br.readLine()) != null) {
                result.append(line);
            }

            // 가져온 공개키 리스트를 json objects의 array 형식으로 변환
            JSONParser parser = new JSONParser();
            JsonObject keys = (JsonObject) parser.parse(result.toString());
            JsonArray keyList = (JsonArray) keys.get("keys");

            return keyList;

        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
    }


}
