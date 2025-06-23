package com.example.dts.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "jwt")
@Data
public class JwtConfig {

    private String secret;
    private long expiration;
    private RefreshToken refreshToken = new RefreshToken();

    public static class RefreshToken {
        private long expiration;

        public long getExpiration() {
            return expiration;
        }

        public void setExpiration(long expiration) {
            this.expiration = expiration;
        }
    }
}
