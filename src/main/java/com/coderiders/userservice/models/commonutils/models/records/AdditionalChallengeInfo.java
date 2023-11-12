package com.coderiders.userservice.models.commonutils.models.records;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class AdditionalChallengeInfo {
    private Double done;
    private Double toGo;
    private Double percentComplete;
}
