package ee.lars.springmvc.spittr;

import java.time.LocalDate;

public class Spittle {

    private final Long id;
    private final String message;
    private final LocalDate time;
    private Double latitude;
    private Double longitude;

    public Spittle(String message, LocalDate time) {
        this(null, message, time, null, null);
    }

    public Spittle(Long id, String message, LocalDate time, Double longitude, Double latitude) {
        this.id = id;
        this.message = message;
        this.time = time;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public long getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public LocalDate getTime() {
        return time;
    }

    public Double getLongitude() {
        return longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }

        Spittle spittle = (Spittle) o;

        if (this.id != null ? !this.id.equals(spittle.id) : spittle.id != null) {
            return false;
        }
        return this.time != null ? this.time.equals(spittle.time) : spittle.time == null;
    }

    @Override
    public int hashCode() {
        int result = this.id != null ? this.id.hashCode() : 0;
        result = 31 * result + (this.time != null ? this.time.hashCode() : 0);
        return result;
    }
}
