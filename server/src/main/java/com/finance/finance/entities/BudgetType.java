package com.finance.finance.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "budget_types")
public class BudgetType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long budgetTypeId;

    @Column(name = "type")
    private String type;

    public BudgetType() {
    }

    public BudgetType(String type) {
        this.type = type;
    }

    public long getBudgetTypeId() {
        return budgetTypeId;
    }

    public void setBudgetTypeId(long budgetTypeId) {
        this.budgetTypeId = budgetTypeId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
