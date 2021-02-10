package com.egovorushkin.logiweb.dto;

public class TruckStatsDto {

    private long total;
    private long available;
    private long busy;
    private  long faulty;

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

    public long getBusy() {
        return busy;
    }

    public void setBusy(long busy) {
        this.busy = busy;
    }

    public long getFaulty() {
        return faulty;
    }

    public void setFaulty(long faulty) {
        this.faulty = faulty;
    }
}
