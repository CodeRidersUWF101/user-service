package com.coderiders.userservice.models.commonutils.models.googleBooks;


import com.coderiders.userservice.models.commonutils.models.UserLibraryWithBookDetails;
import com.coderiders.userservice.models.commonutils.models.UtilsUser;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class SaveBookRequest {
    private UtilsUser user;
    private UserLibraryWithBookDetails book;
}
