package com.abhishek.portfolio.service.impl;

import com.abhishek.portfolio.dto.ProfileRequest;
import com.abhishek.portfolio.dto.ProfileResponse;
import com.abhishek.portfolio.mapper.ProfileMapper;
import com.abhishek.portfolio.model.Profile;
import com.abhishek.portfolio.repository.ProfileRepository;
import com.abhishek.portfolio.security.CustomPrincipal;
import com.abhishek.portfolio.service.ProfileService;
import com.abhishek.portfolio.storage.S3ProfileImageStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final ProfileRepository repository;
    private final ProfileMapper mapper;
    private final S3ProfileImageStorageService profileImageStorageService;

    @Override
    @Cacheable(value = "profiles", key = "#userId")
    public ProfileResponse getProfile(String userId) {

        Profile profile = repository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Profile not found"));

        return mapper.toResponse(profile);
    }

    @Override
    @CacheEvict(value = "profiles", key = "#principal.userId")
    public ProfileResponse createOrUpdateProfile(CustomPrincipal principal, ProfileRequest request) {

        Profile profile = repository.findByUserId(principal.getUserId())
                .map(existing -> mapper.updateEntity(existing, request, principal))
                .orElseGet(() -> mapper.toEntity(request, principal));

        Profile savedProfile = repository.save(profile);
        return mapper.toResponse(savedProfile);
    }

    @Override
    @CacheEvict(value = "profiles", key = "#principal.userId")
    public ProfileResponse updateProfileImage(CustomPrincipal principal, MultipartFile file) {

        Profile profile = repository.findByUserId(principal.getUserId())
                .orElseThrow(() -> new RuntimeException("Profile not found"));

        String oldImageKey = profile.getProfileImageKey();

        String newImageKey = profileImageStorageService.uploadProfileImage(
                principal.getUserId(),
                file
        );

        profile.setProfileImageKey(newImageKey);
        profile.setUpdatedAt(LocalDateTime.now());

        Profile savedProfile = repository.save(profile);

        profileImageStorageService.deleteProfileImage(oldImageKey);

        return mapper.toResponse(savedProfile);
    }


    @Override
    public String getProfileImageAsBase64(CustomPrincipal principal) {
        Profile profile = repository.findByUserId(principal.getUserId())
                .orElseThrow(() -> new RuntimeException("Profile not found"));

        String imageKey = profile.getProfileImageKey();

        if (imageKey == null || imageKey.isBlank()) {
            return null;
        }

        return profileImageStorageService.getImageAsBase64(imageKey);
    }
}