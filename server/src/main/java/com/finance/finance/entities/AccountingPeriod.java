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

    @Column(name = "year")
    private int year;

    @Column(name = "notes", columnDefinition="TEXT")
    private String notes;

    public AccountingPeriod() {
    }

    public AccountingPeriod(long accountingPeriodId, Date startDate, Date endDate, int year, String notes) {
        this.accountingPeriodId = accountingPeriodId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.year = year;
        this.notes = notes;
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

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
