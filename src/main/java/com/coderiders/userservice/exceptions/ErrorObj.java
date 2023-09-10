package com.coderiders.userservice.exceptions;

import lombok.Builder;

@Builder
public class ErrorObj {
    private int errorCode;
    private String errorId;
    private String errorMessage;
}
