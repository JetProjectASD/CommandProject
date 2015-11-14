package com.jet.edu.project03.clients.write;

import com.jet.edu.project03.clients.write.exceptions.SomeException;

/**
 * enum with some commands for good understanding with server
 */
public enum IDsFiltering {
    SEND("/snd"),
    HISTORY("/hist"),
    NAME("/chid"),
    ROOM("/chroom");

    private String prefix;

    IDsFiltering(String prefix) {
        this.prefix = prefix;
    }

    /**
     * separate prefix from message
     * @param message which user write to console
     */
    public static String getPrefix(String message) throws SomeException {
        for (IDsFiltering iDsFiltering : IDsFiltering.values()) {
            if (message.startsWith(iDsFiltering.prefix))
                return iDsFiltering.toString();
        }
        throw new SomeException("User, you're stupid!!!111");
    }
}
