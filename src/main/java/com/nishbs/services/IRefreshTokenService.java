package com.nishbs.services;

import com.nishbs.jwt.AuthResponse;
import com.nishbs.jwt.RefreshTokenRequest;

public interface IRefreshTokenService {

    public AuthResponse refreshToken(RefreshTokenRequest request);
}