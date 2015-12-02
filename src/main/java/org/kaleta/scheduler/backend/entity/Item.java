package org.kaleta.scheduler.backend.entity;

import java.math.BigDecimal;

/**
 * Author: Stanislav Kaleta
 * Date: 20.7.2014
 */
public class Item {
    private Integer id;
    private String type;
    private String description;
    private Integer day;
    private Boolean income;
    private BigDecimal amount;

    public Item(){
        id = null;
        type = null;
        description = null;
        day = null;
        income = null;
        amount = null;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getIncome() {
        return income;
    }

    public void setIncome(Boolean income) {
        this.income = income;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Item item = (Item) o;

        if (amount != null ? !amount.equals(item.amount) : item.amount != null) return false;
        if (day != null ? !day.equals(item.day) : item.day != null) return false;
        if (description != null ? !description.equals(item.description) : item.description != null) return false;
        if (id != null ? !id.equals(item.id) : item.id != null) return false;
        if (income != null ? !income.equals(item.income) : item.income != null) return false;
        return !(type != null ? !type.equals(item.type) : item.type != null);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (day != null ? day.hashCode() : 0);
        result = 31 * result + (income != null ? income.hashCode() : 0);
        result = 31 * result + (amount != null ? amount.hashCode() : 0);
        return result;
    }
}
