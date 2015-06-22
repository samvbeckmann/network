package com.qkninja.network.proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

/**
 * Contains methods for client-side proxy operations.
 *
 * @author QKninja
 */
public class ClientProxy extends CommonProxy
{
    @Override
    public void preInit()
    {
        // NOOP
    }

    @Override
    public void init()
    {
        // NOOP
    }

    @Override
    public void postInit()
    {
        // NOOP
    }

    @Override
    public EntityPlayer getClientPlayer()
    {
        return Minecraft.getMinecraft().thePlayer;
    }
}
