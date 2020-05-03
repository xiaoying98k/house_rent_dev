package com.api.entities;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@Accessors(chain = true)
public class Orders implements Serializable {
    private int id;
    private int rentId;
    private int ownerId;
    private String rentName;
    private String ownerName;
    private String ownerPhone;
    private String rentPhone;
    private int houseId;
    private String houseNumber;
    private BigDecimal housePrice;
    private BigDecimal deposit;
    private String houseRentType;
    private String houseTimeType;
    private String orderTime;
    private String rentTime;
    private String state;
    private  String deletedFlag;
}
