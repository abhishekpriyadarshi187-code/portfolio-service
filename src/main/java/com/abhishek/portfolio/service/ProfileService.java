package com.abhishek.portfolio.service;

import com.abhishek.portfolio.dto.ProfileRequest;
import com.abhishek.portfolio.dto.ProfileResponse;
import com.abhishek.portfolio.security.CustomPrincipal;

public interface ProfileService {

    ProfileResponse getProfile(String userId);

    ProfileResponse createOrUpdateProfile(CustomPrincipal principal, ProfileRequest request);
}
