package com.nguyenkhang.mobile_store.configuration;

import com.nguyenkhang.mobile_store.dto.request.IntrospectRequest;
import com.nguyenkhang.mobile_store.service.AuthenticationService;
import com.nimbusds.jose.JOSEException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;

import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.text.ParseException;
import java.util.Objects;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class CustomJwtDecoder implements JwtDecoder {
    @NonFinal
    @Value("${jwt.signerKey}")
    protected String signerKey;
    AuthenticationService authenticationService;

    @NonFinal
    NimbusJwtDecoder nimbusJwtDecoder = null;



    @Override
    public Jwt decode(String token) throws JwtException  {
        try {
            var response = authenticationService.introspect(IntrospectRequest.builder().token(token).build());
            if (!response.isValid()){
                throw new JwtException("Token invalid!");
            }// check token con hieu luc khong
        } catch (ParseException| JOSEException e) {
            throw new JwtException(e.getMessage());
        }

        if (Objects.isNull(nimbusJwtDecoder)){
            SecretKeySpec secretKeySpec =new SecretKeySpec(signerKey.getBytes(),"HS512");

            nimbusJwtDecoder = NimbusJwtDecoder
                    .withSecretKey(secretKeySpec)
                    .macAlgorithm(MacAlgorithm.HS512)
                    .build();
        }

        return nimbusJwtDecoder.decode(token);
    }
}
