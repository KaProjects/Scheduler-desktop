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
}
