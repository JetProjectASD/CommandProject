package com.jet.edu.project03.client;

import com.jet.edu.project03.exeptions.NameExeption;

/**
 * Filter preffiks messange from ..
 */
public class FilterName implements Filter {

    private StringBuilder mes = new StringBuilder();
    ServerConnector connector;

    public FilterName (ServerConnector connector){
        this.connector = connector;
    }


    /**
     * Filter messanges
     *
     * @param message from consol
     */
    @Override
    public String filter(String message, boolean connectFlag) throws NameExeption {
        String[] stringArray = message.split(" ");
        String preffiks = stringArray[0];
        for (int i = 1; i < stringArray.length; i++) {
            mes.append(message.split(" ")[i]);
        }



        switch (preffiks) {
            case SEND: {
                if(connectFlag) {
                    if(mes.length() > 150) {
                       return "ERR";
                    }
                    return mes.toString();
                } else {
                    return "ERR";
                }

            }
            case HISTORY: {
                return  "ERR";
            }
            case NAME: {
                if(mes.length() > 50){
                    return "ERR";
                }
                return mes.toString();
            }
            default: {
                throw new NameExeption("не корректная команда");

            }
        }



    }


    private final static String SEND = "/snd";
    private final static String HISTORY = "/hist";
    private final static String NAME = "/chid";


}
