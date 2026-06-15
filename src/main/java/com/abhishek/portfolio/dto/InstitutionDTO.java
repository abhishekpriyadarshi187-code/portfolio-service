package com.abhishek.portfolio.dto;

import com.abhishek.portfolio.enums.InstitutionType;
import lombok.Data;

@Data
public class InstitutionDTO {
    private String name;
    private InstitutionType type;
    private String location;
}