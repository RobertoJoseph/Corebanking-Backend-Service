package com.example.tanmeyah.customer.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AddFacilityAndGrantorDTO {
    private String customerNationalId;
    private String facilityName;
    private String nationalId;
}
