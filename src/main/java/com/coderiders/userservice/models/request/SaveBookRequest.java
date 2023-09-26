package com.coderiders.userservice.models.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class SaveBookRequest {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String clerkId;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String isbn_10;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String isbn_13;
}
