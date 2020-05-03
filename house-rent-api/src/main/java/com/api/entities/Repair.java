package com.api.entities;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
@Accessors(chain = true)
public class Repair implements Serializable {
    private int id;
    private int houseId;
    private int rentId;
    private int ownerId;
    private String state;
    private String time;
    private String goodsDesc;
    private String houseNumber;//未对应数据库字段
    private String deletedFlag;

}
