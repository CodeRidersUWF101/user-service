package com.coderiders.userservice.exceptions;

import lombok.Data;

import java.util.List;

@Data
public class UserErrorResponse {
    private int errorCode;
    private String errorId;
    private String errorMessage;
    private List<ErrorObj> additionalErrors;
}
