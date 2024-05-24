package com.nobblecrafts.challenge.devsecopssr.security.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

@Data
@ConfigurationProperties(prefix = "spring.security.oauth2")
public class OAuth2ConfigProperties {

    private RSA rsa;
    public record RSA(RSAPublicKey publicKey, RSAPrivateKey privateKey) {}
}
