package com.api.entities;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Accessors(chain = true)
public class House implements Serializable {
    private int id;
    private int ownerId;
    private String number;
    private String houseType;
    private String image;
    private String location;
    private int renting;
    private BigDecimal price;
    private BigDecimal deposit;
    private int count;
    private String rentType;
    private String timeType;
    private int area;
    private String label;
    private String houseDesc;
    private String deletedFlag;
}
