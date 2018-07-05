package com.example.shady.parkingo;

public class checkin_details {
    private String name;
    private String vehicle_no;
    private long mobile;
    private String slot;
    private int ticket;

    public void setTicket(int ticket) {
        this.ticket = ticket;
    }

    public void setMobile(long mobile) {
        this.mobile = mobile;
    }

    public void setSlot(String slot) {
        this.slot = slot;
    }

    public void setVehicle_no(String vehicle_no) {
        this.vehicle_no = vehicle_no;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public long getMobile() {
        return mobile;
    }

    public String getSlot() {
        return slot;
    }

    public String getVehicle_no() {
        return vehicle_no;
    }

    public int getTicket() {
        return ticket;
    }
}
