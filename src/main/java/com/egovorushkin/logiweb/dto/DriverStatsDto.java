package com.egovorushkin.logiweb.dto;

import java.io.Serializable;

/**
 * Represent a driver statistic
 * extends {@link AbstractDto}
 * implements {@link Serializable}
 */
public class DriverStatsDto implements Serializable{

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DriverStatsDto that = (DriverStatsDto) o;

        if (total != that.total) return false;
        if (available != that.available) return false;
        return notAvailable == that.notAvailable;
    }

    @Override
    public int hashCode() {
        int result = (int) (total ^ (total >>> 32));
        result = 31 * result + (int) (available ^ (available >>> 32));
        result = 31 * result + (int) (notAvailable ^ (notAvailable >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "DriverStatsDto{" +
                "total=" + total +
                ", available=" + available +
                ", notAvailable=" + notAvailable +
                '}';
    }
}
