package com.edu.system.domain.vo;

import lombok.Data;

@Data
public class AuthScopesVo {

    private String[] authedUser;

    private Long[] authedDept;
}
