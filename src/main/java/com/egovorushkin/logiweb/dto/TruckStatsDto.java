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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TruckStatsDto that = (TruckStatsDto) o;

        if (total != that.total) return false;
        if (available != that.available) return false;
        if (busy != that.busy) return false;
        return faulty == that.faulty;
    }

    @Override
    public int hashCode() {
        int result = (int) (total ^ (total >>> 32));
        result = 31 * result + (int) (available ^ (available >>> 32));
        result = 31 * result + (int) (busy ^ (busy >>> 32));
        result = 31 * result + (int) (faulty ^ (faulty >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "TruckStatsDto{" +
                "total=" + total +
                ", available=" + available +
                ", busy=" + busy +
                ", faulty=" + faulty +
                '}';
    }
}
