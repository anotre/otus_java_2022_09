package ru.otus.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ObjectForMessage implements Copyable<ObjectForMessage> {
    private List<String> data;

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = new ArrayList<>(data);
    }

    public ObjectForMessage copy() {
        List<String> buffer = new ArrayList<>(data);
        ObjectForMessage copy = new ObjectForMessage();
        copy.setData(buffer);

        return copy;
    }
}
