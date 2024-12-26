package com.nishbs.services.impl;

import com.nishbs.entities.RefreshToken;
import com.nishbs.entities.User;
import com.nishbs.exception.RefreshTokenException;
import com.nishbs.jwt.AuthResponse;
import com.nishbs.jwt.JwtService;
import com.nishbs.jwt.RefreshTokenRequest;
import com.nishbs.repository.RefreshTokenRepository;
import com.nishbs.services.IRefreshTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenServiceImpl implements IRefreshTokenService {

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private JwtService jwtService;

    public boolean isRefreshTokenExpired(Date expiredDate) {
        return new Date().before(expiredDate);
    }

    private RefreshToken createRefreshToken(User user) {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setRefreshToken(UUID.randomUUID().toString());
        refreshToken.setExpireDate(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 4));
        refreshToken.setUser(user);

        return refreshToken;
    }

    @Override
    public AuthResponse refreshToken(RefreshTokenRequest request) {

        Optional<RefreshToken> optional = refreshTokenRepository.findByRefreshToken(request.getRefreshToken());
        if (optional.isEmpty()) {
            throw new RefreshTokenException("Refresh Token Geçersizdir ! Girilen Refresh Token : " + request.getRefreshToken());
        }

        RefreshToken refreshToken = optional.get();

        if (!isRefreshTokenExpired(refreshToken.getExpireDate())) {
            throw new RefreshTokenException("Refresh Token Süresi Dolmuştur ! Girilen Refresh Token : " + request.getRefreshToken());
        }

        String accessToken = jwtService.generateToken(refreshToken.getUser());
        RefreshToken savedRefreshToken = refreshTokenRepository.save(createRefreshToken(refreshToken.getUser()));

        return new AuthResponse(accessToken, savedRefreshToken.getRefreshToken());
    }
}