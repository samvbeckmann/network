package com.qkninja.network.thirdparty.waila;

import com.qkninja.network.block.BlockTransporter;
import mcp.mobius.waila.api.IWailaRegistrar;

/**
 * Waila handler, to register custom tooltips.
 *
 * @author QKninja
 */
public class Waila
{
    @SuppressWarnings("unused")
    public static void onWailaCall(IWailaRegistrar registrar)
    {
        registrar.registerStackProvider(new WailaTransporterHandler(), BlockTransporter.class);
        registrar.registerBodyProvider(new WailaTransporterHandler(), BlockTransporter.class);
        registrar.registerNBTProvider(new WailaTransporterHandler(), BlockTransporter.class);
    }
}
