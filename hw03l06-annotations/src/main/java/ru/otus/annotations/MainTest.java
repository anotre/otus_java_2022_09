package ru.otus.annotations;

import ru.otus.annotations.Main;

import java.util.Arrays;
import java.util.List;

public class MainTest {
    private Main sut;
    @Before
    public void before() {
        this.sut = new Main();
    }

    @After
    public void after() {
        this.sut = null;
    }

    @Test
    public void getSumListTest() throws Exception {
        List<Integer> integers = Arrays.asList(100, 200, 300);
        int expectedResult = 600;
        if (this.sut.getSumList(integers) != expectedResult) {
            throw new Exception("An error when calculating the sum of list items in the getSumList method");
        }
    }
}
