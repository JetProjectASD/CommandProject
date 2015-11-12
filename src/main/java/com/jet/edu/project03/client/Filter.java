package com.jet.edu.project03.client;

import com.jet.edu.project03.exeptions.NameExeption;

/**
 * Fiter
 */
public interface Filter {
    public String filter(String messange, boolean connectFlag) throws NameExeption;

}
