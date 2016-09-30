package org.kaleta.scheduler.backend.entity;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Stanislav Kaleta on 08.09.2015.
 */
public class UserType {
    private String name;
    private List<String> preparedDescriptions;
    private Color color;
    private Boolean sign;

    public UserType(){
        name = null;
        preparedDescriptions = new ArrayList<>();
        color = null;
        sign = null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getPreparedDescriptions() {
        return preparedDescriptions;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Boolean getSign() {
        return sign;
    }

    public void setSign(Boolean sign) {
        this.sign = sign;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserType type = (UserType) o;

        if (name != null ? !name.equals(type.name) : type.name != null) return false;
        if (preparedDescriptions != null ? !preparedDescriptions.equals(type.preparedDescriptions) : type.preparedDescriptions != null)
            return false;
        if (color != null ? !color.equals(type.color) : type.color != null) return false;
        if (sign != null ? !sign.equals(type.sign) : type.sign != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (preparedDescriptions != null ? preparedDescriptions.hashCode() : 0);
        result = 31 * result + (color != null ? color.hashCode() : 0);
        result = 31 * result + (sign != null ? sign.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return name;
    }
}
