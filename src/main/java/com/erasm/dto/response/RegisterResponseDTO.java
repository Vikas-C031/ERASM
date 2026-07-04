package com.erasm.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterResponseDTO {

    private Long id;

    private String username;

    private String email;

    private String employeeCode;

    private String employeeName;

    private String role;

    private Boolean enabled;

}