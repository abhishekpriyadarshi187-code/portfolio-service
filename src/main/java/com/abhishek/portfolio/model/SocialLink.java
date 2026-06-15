package com.abhishek.portfolio.model;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SocialLink {

    private String platform; // LinkedIn, GitHub
    private String url;
}