package com.abhishek.portfolio.controller;

import com.abhishek.portfolio.dto.ProfileRequest;
import com.abhishek.portfolio.dto.ProfileResponse;
import com.abhishek.portfolio.security.CustomPrincipal;
import com.abhishek.portfolio.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @PutMapping(value = "/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ProfileResponse> updateProfileImage(
            @AuthenticationPrincipal CustomPrincipal principal,
            @RequestParam("file") MultipartFile file
    ) {

        System.out.println("Image upload controller reached");
        System.out.println("File name: " + file.getOriginalFilename());
        System.out.println("File size: " + file.getSize());
        System.out.println("Content type: " + file.getContentType());
        return ResponseEntity.ok(service.updateProfileImage(principal, file));
    }

    @GetMapping(value = "/image/base64", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> getProfileImageAsBase64(
            @AuthenticationPrincipal CustomPrincipal principal
    ) {
        String base64Image = service.getProfileImageAsBase64(principal);

        if (base64Image == null || base64Image.isBlank()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(base64Image);
    }
}