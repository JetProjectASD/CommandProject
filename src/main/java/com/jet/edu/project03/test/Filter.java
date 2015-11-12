package com.jet.edu.project03.test;

public interface Filter {
    String SEND = "/snd";
    String HISTORY = "/hist";
    String NAME = "/chid";
    String ROOM = "/chroom";

    boolean filter(String message);
}
