package com.you.system.domain.bo;

import lombok.Data;

import java.util.List;

@Data
public class AuthUserBo {

    private Long roleId;

    private List<Long> userIds;
}
