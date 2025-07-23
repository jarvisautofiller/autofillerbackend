package com.autofiller.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.auto.value.AutoValue.Builder;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private int age;
    private String address;
    private String email;
    private String profession;
    private String accountNumber;
    private String ifscCode;
    private String income;

    @JsonProperty("dob")
    private String dob;



    public void setDob(String age) {
        this.age = calculateAge(age);
    }


    public int calculateAge(String dobString) {
        try {
            java.time.LocalDate dob = java.time.LocalDate.parse(dobString);
            return java.time.Period.between(dob, java.time.LocalDate.now()).getYears();
        } catch (Exception e) {
            return 0;
        }
    }

}
