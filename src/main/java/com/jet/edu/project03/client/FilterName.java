package com.jet.edu.project03.client;

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
        String preffiks = message.split(" ")[0];
        for (int i = 1; i < preffiks.length(); i++) {
            mes.append(message.split(" ")[i]).append(System.lineSeparator());
        }



        switch (preffiks) {
            case SEND: {
                if(connectFlag) {
                    if(mes.length() > 150) {
                       return "ERR";
                    }
                    return "ok " + mes;
                }

                break;
            }
            case HISTORY: {

                break;
            }
            case NAME: {

                break;
            }
            default: {
                throw new NameExeption("не корректная команда");

            }
        }

        return null;

    }


    private final static String SEND = "/snd";
    private final static String HISTORY = "/hist";
    private final static String NAME = "/chid";


}
