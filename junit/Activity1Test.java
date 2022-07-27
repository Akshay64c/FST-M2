package activities;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.ArrayList;

public class Activity1Test {

    static ArrayList<String> list;
    @BeforeEach
    void setup(){
        list = new ArrayList<String>();
        list.add("alpha");
        list.add("beta");
    }

    @Test
    public void insertTest(){
        Assertions.assertEquals(2, list.size(),"Wrong size");
        list.add("charlie");
        Assertions.assertEquals(3, list.size(),"Wrong size");
        Assertions.assertEquals("alpha", list.get(0), "Wrong element");
        Assertions.assertEquals("beta", list.get(1), "Wrong element");
        Assertions.assertEquals("charlie", list.get(2), "Wrong element");
    }

    @Test
    public void replaceTest(){
        list.set(1, "charlie");
        Assertions.assertEquals(2, list.size(), "Wrong size");
        Assertions.assertEquals("alpha", list.get(0), "Wrong element");
        Assertions.assertEquals("charlie", list.get(1), "Wrong element");
    }
}
