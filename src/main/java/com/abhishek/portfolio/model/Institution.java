package com.abhishek.portfolio.model;

import com.abhishek.portfolio.enums.InstitutionType;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Institution {

    private String name;
    private InstitutionType type;     // UNIVERSITY / COLLEGE / SCHOOL
    private String location;
    private String code;     // optional
}