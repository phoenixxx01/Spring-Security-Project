package com.kennedy.demospringsecurityapp.service;

import com.kennedy.demospringsecurityapp.dto.AuthResponseDto;
import com.kennedy.demospringsecurityapp.dto.LoginRequestDto;
import com.kennedy.demospringsecurityapp.dto.LoginResponse;
import com.kennedy.demospringsecurityapp.dto.RegistrationDto;

public interface UserService {
    AuthResponseDto registerUser(RegistrationDto registrationDto);
    LoginResponse loginUser(LoginRequestDto loginRequestDto);
}
