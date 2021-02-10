package com.egovorushkin.logiweb.dto;

public class DriverStatsDto {

    private long total;
    private long available;
    private long notAvailable;

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public long getAvailable() {
        return available;
    }

    public void setAvailable(long available) {
        this.available = available;
    }

    public long getNotAvailable() {
        return this.total - this.available;
    }

    public void setNotAvailable(long notAvailable) {
        this.notAvailable = notAvailable;
    }
}
