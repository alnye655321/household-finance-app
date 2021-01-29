package com.finance.finance.entities;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "accounting_periods")
public class AccountingPeriod {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long accountingPeriodId;

    @JsonFormat(pattern="yyyy-MM-dd")
    @Column(name = "start_date")
    private java.sql.Date startDate;

    @JsonFormat(pattern="yyyy-MM-dd")
    @Column(name = "end_date")
    private java.sql.Date endDate;


    public AccountingPeriod() {
    }

    public AccountingPeriod(Date startDate, Date endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public long getAccountingPeriodId() {
        return accountingPeriodId;
    }

    public void setAccountingPeriodId(long accountingPeriodId) {
        this.accountingPeriodId = accountingPeriodId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }


}
