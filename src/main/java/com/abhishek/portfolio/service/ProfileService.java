package com.abhishek.portfolio.service;

import com.abhishek.portfolio.dto.ProfileRequest;
import com.abhishek.portfolio.dto.ProfileResponse;
import com.abhishek.portfolio.security.CustomPrincipal;
import org.springframework.web.multipart.MultipartFile;

public interface ProfileService {

    ProfileResponse getProfile(String userId);

    ProfileResponse createOrUpdateProfile(CustomPrincipal principal, ProfileRequest request);

    ProfileResponse updateProfileImage(CustomPrincipal principal, MultipartFile file);

    String getProfileImageAsBase64(CustomPrincipal principal);
}
