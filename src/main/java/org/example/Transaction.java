package org.example;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Transaction {
    private Long id;
    private LocalDate date;
    private LocalDate valueDate;
    private String description;
    private String cheque;
    private BigDecimal deposit = BigDecimal.ZERO;
    private BigDecimal withdrawal = BigDecimal.ZERO;
    private BigDecimal balance = BigDecimal.ZERO;

    public Transaction() {}

    public Transaction(LocalDate date, LocalDate valueDate, String description, String cheque, BigDecimal deposit, BigDecimal withdrawal, BigDecimal balance) {
        this.date = date;
        this.valueDate = valueDate;
        this.description = description;
        this.cheque = cheque;
        this.deposit = deposit;
        this.withdrawal = withdrawal;
        this.balance = balance;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getWithdrawal() {
        return withdrawal;
    }

    public void setWithdrawal(BigDecimal withdrawal) {
        this.withdrawal = withdrawal;
    }

    public BigDecimal getDeposit() {
        return deposit;
    }

    public void setDeposit(BigDecimal deposit) {
        this.deposit = deposit;
    }

    public String getCheque() {
        return cheque;
    }

    public void setCheque(String cheque) {
        this.cheque = cheque;
    }

    public LocalDate getValueDate() {
        return valueDate;
    }

    public void setValueDate(LocalDate valueDate) {
        this.valueDate = valueDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", date=" + date +
                ", valueDate=" + valueDate +
                ", description='" + description + '\'' +
                ", cheque='" + cheque + '\'' +
                ", deposit=" + deposit +
                ", withdrawal=" + withdrawal +
                ", balance=" + balance +
                '}';
    }
}
