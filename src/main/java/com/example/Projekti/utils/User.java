package com.example.Projekti.utils;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
    public User(String name,
                String lastName,
                Gender gender,
                String email,
                String password,
                String company,
                String birthDay,
                String birthMonth,
                String birthYear,
                int birthDayInteger,
                int birthMonthInteger,
                int birthYearInteger) {
        this.name = name;
        this.lastName = lastName;
        this.gender = gender;
        this.email = email;
        this.password = password;
        this.company = company;
        this.birthDay = birthDay;
        this.birthMonth = birthMonth;
        this.birthYear = birthYear;
        this.birthDayInteger = birthDayInteger;
        this.birthMonthInteger = birthMonthInteger;
        this.birthYearInteger = birthYearInteger;
    }

    private String name;
    private String lastName;
    private Gender gender;
    private String email;
    private String password;
    private String company;
    private String birthDay;
    private String birthMonth;
    private String birthYear;
    private int birthDayInteger;
    private int birthMonthInteger;
    private int birthYearInteger;
}
