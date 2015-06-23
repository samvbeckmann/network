package com.qkninja.network.utility;

import com.qkninja.network.tileentity.TileEntityTransporter;
import net.minecraft.client.Minecraft;

import java.util.ArrayList;
import java.util.List;

/**
 * dilithium-transportation, class made on 6/22/2015.
 *
 * @author Sam Beckmann
 */
public class TileEntityTrackerHelper
{
    private static Minecraft mc = Minecraft.getMinecraft();

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
