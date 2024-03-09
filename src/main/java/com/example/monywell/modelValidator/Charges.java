package com.example.monywell.modelValidator;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class Charges {

    private static final Double SAME_BANK_CHARGES = 20.05;

    private static final Double INTER_BANK_CHARGES = 30.65;

    public static Double getSAME_BANK_CHARGES() {
        return SAME_BANK_CHARGES;
    }
    public static Double getINTER_BANK_CHARGES() {
        return INTER_BANK_CHARGES;
    }

}
