package com.example.tanmeyah.repaymentSchedule.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.util.Pair;

import javax.swing.*;
import java.time.LocalDate;
import java.util.ArrayList;

@Getter
@Setter
public class RepaymentScheduleDTO {
    private LocalDate startDate;
    private LocalDate endDate;
    private int numberOfInstallments;
    private double totalAmount;
    private double monthlyAmount;
    private int currentInstallment;
    private ArrayList<Pair<LocalDate,Boolean>> installment;
}
