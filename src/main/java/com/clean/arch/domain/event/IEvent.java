package com.clean.arch.domain.event;

import com.clean.arch.domain.common.Entity;

public interface IEvent {
    default public String getName() {
        return getClass().getName();
    }

    Entity<?> getEntity();

    boolean isGlobal();
}
