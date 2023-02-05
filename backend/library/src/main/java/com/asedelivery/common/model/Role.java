package com.asedelivery.common.model;

public enum Role {
    DISPATCHER,
    DELIVERER,
    CUSTOMER,
    BOX;

    public static final String DISPATCHER_STR = "DISPATCHER";
    public static final String DELIVERER_STR = "DELIVERER";
    public static final String CUSTOMER_STR = "CUSTOMER";
    public static final String BOX_STR = "BOX";

    public String toString(){
        switch(this){
            case DISPATCHER : return DISPATCHER_STR;
            case DELIVERER  : return DELIVERER_STR;
            case CUSTOMER   : return CUSTOMER_STR;
            case BOX        : return BOX_STR;
        }
        return null;
    }
}
