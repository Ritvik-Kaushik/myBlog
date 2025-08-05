package com.myblog.service;

import com.myblog.payload.LoginDto;
import com.myblog.payload.RegisterDto;

public interface AuthService {
    String login(LoginDto loginDto);

    String register(RegisterDto registerDto);
}
