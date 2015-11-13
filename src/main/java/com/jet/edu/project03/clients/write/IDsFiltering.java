package com.jet.edu.project03.clients.write;

import com.jet.edu.project03.clients.write.exceptions.SomeException;

public enum IDsFiltering {
    SEND("/snd"),
    HISTORY("/hist"),
    NAME("/chid"),
    ROOM("/chroom");

    private String prefix;

    IDsFiltering(String prefix) {
        this.prefix = prefix;
    }

    public static String getPrefix(String message) throws SomeException {
        for (IDsFiltering iDsFiltering : IDsFiltering.values()) {
            if (message.startsWith(iDsFiltering.prefix))
                return iDsFiltering.toString();
        }
        throw new SomeException("User, you're stupid!!!111");
    }
}
