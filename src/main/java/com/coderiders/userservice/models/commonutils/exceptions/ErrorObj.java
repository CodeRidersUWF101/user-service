package com.coderiders.userservice.models.commonutils.exceptions;

import lombok.Builder;

@Builder
public class ErrorObj {
  private int errorCode;
  private String errorId;
  private String errorMessage;
}