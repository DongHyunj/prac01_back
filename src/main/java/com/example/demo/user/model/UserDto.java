package com.example.demo.user.model;

import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

public class UserDto {

    @Getter
    public static class SignupReq {
        @Pattern(message = "이메일 형식이 아닙니다.", regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\\\.[a-zA-Z]{2,}$")
        private String email; // 이메일 형식으로만 저징되게 하고 싶음
        private String title;
        @Pattern(message = "한글과 공백만 입력 가능합니다.", regexp = "^[가-힣\\s]*$")
        private String name;
        @Pattern(message = "비밀번호는 숫자,영문,특수문자( !@#$%^&*() )를 조합해 8~20자로 생성해주세요", regexp = "^(?=.*[a-zA-Z])(?=.*[!@#$%^&*()])(?=.*[0-9]).{8,20}$")
        private String password;

        public User toEntity() {
            return User.builder()
                    .email(this.email)
                    .name(this.name)
                    .password(this.password)
                    .enable(false)
//                    .role("ROLE_USER") // User 엔티티에 도메인 무결성 제약조건으로 처리
                    .build();
        }
    }


    @Builder
    @Getter
    public static class SignupRes {
        private Long idx;
        private String email;
        private String name;

        public static SignupRes from(User entity) {
            return SignupRes.builder()
                    .idx(entity.getIdx())
                    .email(entity.getEmail())
                    .name(entity.getName())
                    .build();
        }
    }

    @Getter
    public static class LoginReq {
        private String email;
        private String password;
    }

    @Builder
    @Getter
    public static class LoginRes {
        private Long idx;
        private String email;
        private String name;

        public static LoginRes from(User entity) {
            return LoginRes.builder()
                    .idx(entity.getIdx())
                    .email(entity.getEmail())
                    .name(entity.getName())
                    .build();
        }
    }

    @Getter
    @Builder
    public static class OAuth {
        private String email;
        private String name;
        private String provider;
        private boolean enable;
        private String role;

        public static OAuth from(Map<String, Object> attributes, String provider) {
            String email = null;
            String name = null;

            switch (provider) {
                // 카카오 로그인
                case "kakao":
                    String providerId = ((Long) attributes.get("id")).toString();
                    email = providerId + "@kakao.social";
                    Map<String, Object> properties = (Map<String, Object>) attributes.get("properties");
                    name = (String) properties.get("nickname");
                    break;
                // 구글 로그인
                case "google":
                    email = (String) attributes.get("email");
                    name = (String) attributes.get("name");
                    break;
            }

            return OAuth.builder()
                    .email(email)
                    .name(name)
                    .provider(provider)
                    .enable(true)
                    .role("ROLE_USER")
                    .build();
        }

        public User toEntity() {
            return User.builder()
                    .email(this.email)
                    .name(this.name)
                    .password(this.provider)
                    .enable(this.enable)
                    .role(this.role)
                    .build();
        }
    }
}
