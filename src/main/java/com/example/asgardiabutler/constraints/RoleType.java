package com.example.asgardiabutler.constraints;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum RoleType {

    HR("hiring manager"),
    PM("project manager"),
    EM("employee"),
    ADMIN("admin");

    public String name;


}
