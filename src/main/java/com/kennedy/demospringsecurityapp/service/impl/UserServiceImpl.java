package com.kennedy.demospringsecurityapp.service.impl;

import com.kennedy.demospringsecurityapp.config.JwtService;
import com.kennedy.demospringsecurityapp.dto.*;
import com.kennedy.demospringsecurityapp.entity.UserEntity;
import com.kennedy.demospringsecurityapp.enums.Role;
import com.kennedy.demospringsecurityapp.repository.UserRepository;
import com.kennedy.demospringsecurityapp.service.EmailSenderService;
import com.kennedy.demospringsecurityapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
   //dependency to repository
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    //for login
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    //for email
    private final EmailSenderService emailSenderService;

    @Override
    public AuthResponseDto registerUser(RegistrationDto registrationDto) {
        UserEntity user = UserEntity.builder()
                .firstName(registrationDto.getFirstName())
                .lastName(registrationDto.getLastName())
                .email(registrationDto.getEmail())
                .password(passwordEncoder.encode(registrationDto.getPassword()))
                .role(Role.USER)
                .build();

        //send email alert
        EmailDetails emailDetails = EmailDetails.builder()
                .recipient(user.getEmail())
                .subject("Account Created")
                .messageBody("Congratulations, your Email has been created successfully")
                .build();

                emailSenderService.sendEmailAlert(emailDetails);

        userRepository.save(user);

        return AuthResponseDto.builder()
                .responseCode("001")
                .responseMessage("Account Created Succesfully")
                .registrationInfo(RegistrationInfo.builder()
                        .firstName(user.getFirstName())
                        .lastName(user.getLastName())
                        .email(user.getEmail())
                        .build())
                .build();

    }

    @Override
    public LoginResponse loginUser(LoginRequestDto loginRequestDto) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginRequestDto.getEmail(),
                loginRequestDto.getPassword()
        ));

        UserEntity user = userRepository.findByEmail(loginRequestDto.getEmail())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);

        return LoginResponse.builder()
                .responseCode("082")
                .responseMessage("Login Successful")
                .loginInfo(LoginInfo.builder()
                        .email(user.getEmail())
                                .token(jwtToken)
                                .build())
                        .build();
    }
}
