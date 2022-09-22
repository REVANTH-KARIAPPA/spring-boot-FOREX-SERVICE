package com.forexservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Data
public class Account {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer accountId;

    @Column(unique = true)
    private String accountNumber;
    private String bankName;
    private String ifscCode;
    private Double balance;

}
