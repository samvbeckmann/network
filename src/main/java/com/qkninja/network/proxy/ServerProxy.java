package com.qkninja.network.proxy;

import net.minecraft.entity.player.EntityPlayer;

/**
 * Contains methods for server-side proxy operations.
 *
 * @author QKninja
 */
@SuppressWarnings("unused")
public class ServerProxy extends CommonProxy
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
        return null;
    }
}
