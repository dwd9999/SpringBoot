package com.ssafy.user.dto;

import lombok.Data;

import java.util.Date;

@Data
public class UserInfoDto {
    private String name;
    private String email;
    private boolean isAdmin;
    private boolean flag;
    private Date joinDate;
}
