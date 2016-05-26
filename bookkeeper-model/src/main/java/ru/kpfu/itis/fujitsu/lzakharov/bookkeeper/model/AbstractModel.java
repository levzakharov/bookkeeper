package ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.model;

import java.io.Serializable;

public abstract class AbstractModel implements Serializable {
    protected Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public abstract int hashCode();

    @Override
    public abstract boolean equals(Object obj);

    @Override
    public abstract String toString();
}
