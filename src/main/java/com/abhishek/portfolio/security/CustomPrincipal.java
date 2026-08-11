package com.abhishek.portfolio.security;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CustomPrincipal {

    private final String userId;
    private final String email;
    private final String mobileNumber;
}
