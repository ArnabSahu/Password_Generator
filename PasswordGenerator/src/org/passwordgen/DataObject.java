package org.passwordgen;

import java.io.Serializable;

public class DataObject implements Serializable {
    public String label, pass;

    public DataObject(String label, String pass) {
        this.label = label;
        this.pass = pass;
    }
}
