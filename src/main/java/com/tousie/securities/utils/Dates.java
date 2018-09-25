package com.tousie.securities.utils;

import java.util.Date;

/**
 * @author sunqian
 */
public class Dates {

    public static long differenceInMillis(Date from, Date to) {
        return to.getTime() - from.getTime();
    }

    public static long differenceInMillis(Date from) {
        return differenceInMillis(from, new Date());
    }
}
