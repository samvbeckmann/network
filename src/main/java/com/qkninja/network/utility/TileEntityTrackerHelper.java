package com.qkninja.network.utility;

import com.qkninja.network.tileentity.TileEntityTransporter;
import net.minecraft.client.Minecraft;

import java.util.ArrayList;
import java.util.List;

/**
 * Helper methods for tracking all the tile entities in the game
 *
 * @author Sam Beckmann
 */
public class TileEntityTrackerHelper
{
    private static Minecraft mc = Minecraft.getMinecraft();

    /**
     * Gets all the Transporters currently loaded in the game.
     *
     * @return a list of all the transporters currently loaded in the world
     */
    public static List<TileEntityTransporter> findTransporters()
    {
        List<TileEntityTransporter> transporters = new ArrayList<TileEntityTransporter>();

        for (Object object : mc.theWorld.loadedTileEntityList)
        {
            if (object != null && object instanceof TileEntityTransporter)
            {
                transporters.add((TileEntityTransporter) object);
            }
        }
        return  transporters;
    }
}
