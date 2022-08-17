package com.example.tanmeyah.product;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ProductType {

    CLASS_A(1000, 10000, 18, false),
    CLASS_B(10000, 50000, 24, false),
    CLASS_C(50000, 100000, 32, true);
    private final int min;
    private final int max;
    private final int duration;
    private final boolean isGranted;


}
