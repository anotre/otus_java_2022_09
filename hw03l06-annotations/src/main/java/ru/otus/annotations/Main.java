package ru.otus.annotations;

import java.util.List;

public class Main {
    public Integer getSumList(List<Integer> list) {
        Integer result = 0;

        for (Integer element : list) {
            result += element;
        }
        return result;
    }
}
