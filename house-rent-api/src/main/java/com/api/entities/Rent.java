package com.api.entities;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@Accessors(chain = true)
public class Rent implements Serializable {
    private int id;
    private int rentId;
    private int ownerId;
    private int houseId;
    private String rentTime;
    private String tenancy;
    private BigDecimal rental;
    private BigDecimal deposit;
    private BigDecimal water;
    private BigDecimal power;
    private String urge;
    private String state;
    private String deletedFlag;
}
