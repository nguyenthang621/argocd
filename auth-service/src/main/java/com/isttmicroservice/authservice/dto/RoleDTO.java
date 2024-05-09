package com.isttmicroservice.authservice.dto;

import lombok.Data;

@Data
public class RoleDTO {
    private Integer id;

    private String role;
    
    private UserDTO user;
    
    
}
