package com.github.battle.market.event.update;

import lombok.NonNull;

public enum UpdateType {
    CREATED,
    REMOVED,
    BANNED,
    UNBANNED,
    UPDATED_LOCATION,
    REMOVED_LOCATION,
    UPDATED_DESCRIPTION,
    REMOVED_DESCRIPTION;

    public static UpdateType getUpdateTypeByName(@NonNull String name) {
        for (UpdateType type : values()) {
            if(type.name().equalsIgnoreCase(name)) {
                return type;
            }
        }
        return null;
    }
}
