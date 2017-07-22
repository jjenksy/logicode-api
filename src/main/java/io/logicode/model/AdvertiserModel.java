package io.logicode.model;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by jenksy on 7/7/17.
 * name
 * address
 * credit limit
 */
@Data
public class AdvertiserModel implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private String name;
    private String contactName;
    private Integer creditLimit = null;
    private String errorMessage = "OK";//default to ok


    public AdvertiserModel() {
    }

    public AdvertiserModel(String message) {
        this.errorMessage = message;
    }

    public AdvertiserModel(String name, String contactName, int creditLimit) {
        this.name = name;
        this.contactName = contactName;
        this.creditLimit = creditLimit;
    }
}
