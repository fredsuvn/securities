package com.tousie.securities.common.id;

import com.tousie.securities.mapper.IdMapper;

public class IdGenerator {

    private final IdMapper idMapper;
    private final IdRecord idRecord;
    private long now;

    public IdGenerator(IdMapper idMapper, IdRecord idRecord) {
        this.idMapper = idMapper;
        this.idRecord = idRecord;
        now = idRecord.getLast() + idRecord.getBuffer();
    }

    public synchronized long next() {
        long ret = now;
        if (now - idRecord.getLast() >= idRecord.getBuffer()) {
            idRecord.setLast(now);
            idMapper.updateByPrimaryKey(idRecord);
        }
        now++;
        return ret;
    }
}
