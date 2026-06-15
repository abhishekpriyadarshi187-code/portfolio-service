package com.abhishek.portfolio.controller;

import com.abhishek.portfolio.dto.ResumeResponse;
import com.abhishek.portfolio.dto.ResumeTemplateRequest;
import com.abhishek.portfolio.service.ResumeService;
import com.abhishek.portfolio.security.CustomPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/resume")
@RequiredArgsConstructor
public class ResumeController {

    private final ResumeService resumeService;

    @GetMapping
    public ResumeResponse getResume() {
        return resumeService.getResume(getPrincipal().getUserId());
    }

    @PostMapping("/template")
    public ResumeResponse saveTemplate(@RequestBody ResumeTemplateRequest request) {
        return resumeService.saveTemplate(getPrincipal().getUserId(), request.getTemplateId());
    }

    @PostMapping("/upload")
    public ResumeResponse uploadResume(
            @RequestParam("file") MultipartFile file,
            @RequestParam("templateId") String templateId) {
        return resumeService.uploadResumePdf(getPrincipal().getUserId(), templateId, file);
    }

    @GetMapping("/download-url")
    public ResumeResponse getDownloadUrl() {
        return resumeService.getDownloadUrl(getPrincipal().getUserId());
    }

    private CustomPrincipal getPrincipal() {
        return (CustomPrincipal) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
    }
}
