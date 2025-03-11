package com.striveonger.music.fairy.sources.api;

import com.striveonger.common.core.Jackson;

/**
 * @author Mr.Lee
 * @since 2024-10-13 16:46
 */
public abstract class Play {

    protected String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public abstract String getKey();

    @Override
    public int hashCode() {
        return getKey().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (obj instanceof Play play) {
            return getKey().equals(play.getKey());
        }
        return false;
    }

    @Override
    public String toString() {
        return Jackson.toJSONString(this);
    }
}
