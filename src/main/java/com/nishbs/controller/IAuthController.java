package com.nishbs.controller;

import com.nishbs.dto.DtoUser;
import com.nishbs.jwt.AuthRequest;
import com.nishbs.jwt.AuthResponse;
import com.nishbs.jwt.RefreshTokenRequest;

public interface IAuthController {

    public DtoUser register(AuthRequest request);

    public AuthResponse authenticate(AuthRequest request);

    public AuthResponse refreshToken(RefreshTokenRequest request);
}