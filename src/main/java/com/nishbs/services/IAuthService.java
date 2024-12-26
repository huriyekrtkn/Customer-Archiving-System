package com.nishbs.services;

import com.nishbs.dto.DtoUser;
import com.nishbs.jwt.AuthRequest;
import com.nishbs.jwt.AuthResponse;

public interface IAuthService {

    public DtoUser register(AuthRequest request);

    public AuthResponse authenticate(AuthRequest request);
}