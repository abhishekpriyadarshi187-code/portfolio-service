package com.abhishek.portfolio.controller;

import com.abhishek.portfolio.dto.ProfileRequest;
import com.abhishek.portfolio.dto.ProfileResponse;
import com.abhishek.portfolio.security.CustomPrincipal;
import com.abhishek.portfolio.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService service;

    @GetMapping
    public ProfileResponse getProfile() {

        CustomPrincipal principal = (CustomPrincipal) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        return service.getProfile(principal.getUserId());
    }

    @PostMapping
    public ProfileResponse createOrUpdate(@RequestBody ProfileRequest request) {

        CustomPrincipal principal = (CustomPrincipal) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();

        return service.createOrUpdateProfile(principal, request);
    }
}