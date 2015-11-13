package com.jet.edu.project03.exeptions;

/**
 * name errors uncorrect comands
 */
public class NameExeption extends Exception {
    /**
     * Constructor for installing reason of exeption
     */
    public NameExeption(String s) {
        super(s);
    }
}
