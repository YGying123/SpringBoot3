package com.example.springboot03.model.domain.request;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 *
 */
@Data
public class UserRegisterRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = 6674641407727407624L;

    private String userAccount;
    private String userPassword;
    private String checkPassword;

    private String planetCode;


}
