package ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.model;

/**
 * Represents the Coordinate.
 */
public class Coordinate {
    private String x;
    private Long y;

    public Coordinate(String x, Long y) {
        this.x = x;
        this.y = y;
    }

    public String getX() {
        return x;
    }

    public void setX(String x) {
        this.x = x;
    }

    public Long getY() {
        return y;
    }

    public void setY(Long y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "Coordinate{" +
                "x='" + x + '\'' +
                ", y=" + y +
                '}';
    }
}
