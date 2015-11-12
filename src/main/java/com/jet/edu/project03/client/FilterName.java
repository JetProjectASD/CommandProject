package com.jet.edu.project03.client;

import com.jet.edu.project03.exeptions.NameExeption;

/**
 * FilterName message from user
 */
public class FilterName implements Filter {

    private StringBuilder mes = new StringBuilder();
    ServerConnector connector;


    private final static String SEND = "/snd";
    private final static String HISTORY = "/hist";
    private final static String NAME = "/chid";
    private final static String ROOM = "/chroom";

    public FilterName (ServerConnector connector){
        this.connector = connector;
    }

    /**
     * FilterName messages
     *
     * @param message from console
     */
    @Override
    public String filter(String message, boolean connectFlag) throws NameExeption {
        String[] stringArray = message.split(" ");
        String pref = stringArray[0];
        for (int i = 1; i < stringArray.length; i++) {
            mes.append(message.split(" ")[i]);
        }


        switch (pref) {
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
            case ROOM : {
                return "ERR";
            }
            default: {
                throw new NameExeption("не корректная команда");

            }
        }



    }





}
