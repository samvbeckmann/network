package com.qkninja.network.utility;

import com.qkninja.network.reference.Messages;
import com.qkninja.network.tileentity.TileEntityTransporter;

/**
 * Helper methods for using transporter modes.
 *
 * @author Sam Beckmann
 */
public class ModeHelper
{
    /**
     * Gets the localized name from a {@link TileEntityTransporter.TransporterMode}
     *
     * @param mode TransporterMode to be converted to a String
     * @return localized name of mode
     */
    public static String getStringFromMode(TileEntityTransporter.TransporterMode mode)
    {
        String s = "";
        switch (mode)
        {
            case NEUTRAL:
                s =  Messages.Strings.MODE_NEUTRAL;
                break;
            case IMPORT:
                s =  Messages.Strings.MODE_IMPORT;
                break;
            case EXPORT:
                s =  Messages.Strings.MODE_EXPORT;
                break;
        }
        return s;
    }
}
