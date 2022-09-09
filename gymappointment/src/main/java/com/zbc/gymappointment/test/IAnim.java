package com.zbc.gymappointment.test;

public interface IAnim {
    default boolean isAttachToWindow() {
        return false;
    }
}
