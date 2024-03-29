package maddori.keygo.service;

import com.google.gson.*;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import maddori.keygo.common.exception.CustomException;
import maddori.keygo.security.JwtHandler;
import maddori.keygo.domain.entity.User;
import maddori.keygo.dto.auth.LoginRequestDto;
import maddori.keygo.dto.auth.LoginResponseDto;
import maddori.keygo.repository.FeedbackRepository;
import maddori.keygo.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;
import java.util.Base64;
import java.util.Objects;

import static maddori.keygo.common.response.ResponseCode.*;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final JwtHandler jwtHandler;
    private final UserRepository userRepository;
    private final FeedbackRepository feedbackRepository;

    @Transactional
    public void deleteUser(Long userId) {
        feedbackRepository.fromUserSetNull(userId);
        userRepository.delete(userRepository.findById(userId).orElseThrow(() ->
                                                    new CustomException(USER_NOT_EXIST)));
    }

    @Transactional
    public LoginResponseDto appleLogin(LoginRequestDto loginRequestDto) {
        // 공개키로 identity token 검증 (공개키 가져오기 -> 검증)
        JsonArray publicKeyList = getApplePublicKeyList();
        PublicKey publicKey = makePublicKey(loginRequestDto.getToken(), publicKeyList);
        Long userId = signInProcess(publicKey, loginRequestDto.getToken());

        // token 발급
        String accessToken = jwtHandler.createAccessToken(userId);
        String refreshToken = jwtHandler.createRefreshToken();

        return LoginResponseDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }


    // 새로운 회원 생성
    private User createUser(String sub, String email) {
        User user = userRepository.save(User.builder()
                        .email(email)
                        .sub(sub)
                        .build());
        return user;
    }

    // 이미 존재하는 회원 로그인 - 이메일 변경시 이메일 정보 업데이트
    private User loginUser(User user, String email) {
        if (user.getEmail() != email) { // 이메일 변경시 업데이트
            user.updateEmail(email);
            userRepository.save(user);
        }
        return user;
    }

    private Long signInProcess(PublicKey publicKey, String token) {
        Claims userInfo = Jwts.parser().setSigningKey(publicKey).parseClaimsJws(token).getBody();
        JsonObject userInfoObject;
        JsonParser parser = new JsonParser();
        userInfoObject = (JsonObject) parser.parse(new Gson().toJson(userInfo));
        // 이메일, sub 정보 가져오기
        JsonElement appleSub = userInfoObject.get("sub");
        String sub = appleSub.getAsString();

        JsonElement appleEmail = userInfoObject.get("email");
        String email = appleEmail.getAsString();

        // 이미 존재하는 회원 - 이메일 업데이트, 존재하지 않는 회원 - 새로 생성
        final User[] user = new User[1];
        userRepository.findBySub(sub).ifPresentOrElse(
            (result) -> {
                user[0] = loginUser(result, email);
            }, () -> {
                user[0] = createUser(sub, email);
            }
        );

        return user[0].getId();
    }

    // identity token의 kid, alg 값과 일치하는 키 정보 선택, 공개키 생성하기
    private PublicKey makePublicKey(String token, JsonArray publicKeyList) {
        JsonObject selectedObject = null;
        // token의 header에서 kid, alg 정보 찾기
        String[] decodeArray = token.split("\\.");
        String header = new String(Base64.getDecoder().decode(decodeArray[0]));
        JsonParser parser = new JsonParser();
        JsonElement kid = ((JsonObject) parser.parse(header)).get("kid");
        JsonElement alg = ((JsonObject) parser.parse(header)).get("alg");

        // 공개키 리스트에서 kid, alg 일치하는 공개키 정보 고르기
        for (JsonElement publicKey : publicKeyList) {
            JsonObject publicKeyObject = publicKey.getAsJsonObject();
            JsonElement publicKid = publicKeyObject.get("kid");
            JsonElement publicAlg = publicKeyObject.get("alg");

            if (Objects.equals(kid, publicKid) && Objects.equals(alg, publicAlg)) {
                selectedObject = publicKeyObject;
                break;
            }
        }

        if (selectedObject == null) throw new CustomException(IDENTITY_TOKEN_INVALID);

        // 선택된 공개키 데이터로 공개키 생성
        PublicKey selectedPublicKey = getPublicKey(selectedObject);

        return selectedPublicKey;
    }

    public PublicKey getPublicKey(JsonObject object) {
        String nStr = object.get("n").toString();
        String eStr = object.get("e").toString();

        byte[] nBytes = Base64.getUrlDecoder().decode(nStr.substring(1, nStr.length() - 1));
        byte[] eBytes = Base64.getUrlDecoder().decode(eStr.substring(1, eStr.length() - 1));

        BigInteger nValue = new BigInteger(1, nBytes);
        BigInteger eValue = new BigInteger(1, eBytes);

        try {
            RSAPublicKeySpec publicKeySpec = new RSAPublicKeySpec(nValue, eValue);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);
            return publicKey;
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
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
            JsonObject keys = (JsonObject) JsonParser.parseString(result.toString());
            JsonArray keyList = (JsonArray) keys.get("keys");

            return keyList;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
