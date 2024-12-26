package com.nishbs.services.impl;

import com.nishbs.dto.DtoUser;
import com.nishbs.entities.RefreshToken;
import com.nishbs.entities.User;
import com.nishbs.exception.AuthenticationException;
import com.nishbs.jwt.AuthRequest;
import com.nishbs.jwt.AuthResponse;
import com.nishbs.jwt.JwtService;
import com.nishbs.repository.RefreshTokenRepository;
import com.nishbs.repository.UserRepository;
import com.nishbs.services.IAuthService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthServiceImpl implements IAuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationProvider authenticationProvider;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Override
    public DtoUser register(AuthRequest request) {

        DtoUser dtoUser = new DtoUser();
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        User savedUser = userRepository.save(user);
        BeanUtils.copyProperties(savedUser, dtoUser);

        return dtoUser;
    }

    private RefreshToken createRefreshToken(User user) {

        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setRefreshToken(UUID.randomUUID().toString());
        refreshToken.setExpireDate(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 4));
        refreshToken.setUser(user);

        return refreshToken;
    }

    @Override
    public AuthResponse authenticate(AuthRequest request) {

        try {
            UsernamePasswordAuthenticationToken auth =
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword());
            authenticationProvider.authenticate(auth);

            Optional<User> optionalUser = userRepository.findByUsername(request.getUsername());
            if (optionalUser.isEmpty()) {
                throw new AuthenticationException("Kullanıcı Bulunamadı!");
            }

            String accessToken = jwtService.generateToken(optionalUser.get());
            RefreshToken refreshToken = createRefreshToken(optionalUser.get());

            refreshTokenRepository.save(refreshToken);
            return new AuthResponse(accessToken, refreshToken.getRefreshToken());
        }
        catch (Exception e) {
            throw new AuthenticationException("Kullanıcı Adı Veya Şifre Hatalı !");
        }
    }
}