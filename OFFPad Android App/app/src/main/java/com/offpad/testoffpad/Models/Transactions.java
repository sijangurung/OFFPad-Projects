package com.offpad.testoffpad.Models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by djlophu on 14/09/15.
 */
@Table(name = "Transactions")
public class Transactions extends Model {
    @Column(name= "date")
    public String Date;

    @Column(name= "destination_number")
    public String Destination_Number;

    @Column(name= "amount")
    public String amount;

    @Column(name = "status")
    public String status;

    public Transactions() {super(); }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getDestination_Number() {
        return Destination_Number;
    }

    public void setDestination_Number(String destination_Number) {
        Destination_Number = destination_Number;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
