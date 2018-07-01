package com.example.shady.parkingo;

public class checkin_details {
    private String name;
    private String vehicle_no;
    private int mobile;
    private String slot;
    public void checkin_details(String name, String vehicle_no,int mobile,String slot){
        this.name=name;
        this.vehicle_no=vehicle_no;
        this.mobile=mobile;
        this.slot=slot;
    }

    public String getName() {
        return name;
    }

    public int getMobile() {
        return mobile;
    }

    public String getSlot() {
        return slot;
    }

    public String getVehicle_no() {
        return vehicle_no;
    }
}
