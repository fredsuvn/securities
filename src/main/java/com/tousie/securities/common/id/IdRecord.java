package com.tousie.securities.common.id;

import javax.persistence.Id;
import javax.persistence.Table;

@Table
public class IdRecord {

    @Id
    private String name;
    private Long last;
    private Long buffer;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getLast() {
        return last;
    }

    public void setLast(Long last) {
        this.last = last;
    }

    public Long getBuffer() {
        return buffer;
    }

    public void setBuffer(Long buffer) {
        this.buffer = buffer;
    }
}
