package com.api.entities;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Accessors(chain = true)
public class User implements Serializable {
    private int id;
    private String name;
    private String nickname;
    private String password;
    private Integer type;
    private BigDecimal money;
    private String payPwd;
    private String phone;
    private String deletedFlag;
}
