package com.qkninja.network.thirdparty.waila;

import com.qkninja.network.block.BlockTransporter;
import mcp.mobius.waila.api.IWailaRegistrar;

/**
 * dilithium-transportation, class made on 2015-06-29
 *
 * @author Sam Beckmann
 */
public class Waila
{
    public static void onWailaCall(IWailaRegistrar registrar)
    {
        registrar.registerStackProvider(new WailaTransporterHandler(), BlockTransporter.class);
        registrar.registerBodyProvider(new WailaTransporterHandler(), BlockTransporter.class);
        registrar.registerNBTProvider(new WailaTransporterHandler(), BlockTransporter.class);
    }
}
