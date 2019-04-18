package ru.tokens.site.entities;

import java.io.Serializable;
import java.time.LocalDate;

/**
 *
 * @author solon4ak
 */
public class BirthCertificate implements Serializable {
    
    private String series;
    private String number;
    private String issueDepartment;
    private LocalDate issueDate;

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getIssueDepartment() {
        return issueDepartment;
    }

    public void setIssueDepartment(String issueDepartment) {
        this.issueDepartment = issueDepartment;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
    }
    
    
}
