package com.finance.finance.entities;

import javax.persistence.*;

@Entity
@Table(name = "period_budgets")
public class PeriodBudget {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long periodBudgetId;

    @Column(name = "amount")
    private double amount;

    @ManyToOne
    @JoinColumn(name="accounting_period_id")
    private AccountingPeriod accountingPeriod;

    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    private User user;

}
