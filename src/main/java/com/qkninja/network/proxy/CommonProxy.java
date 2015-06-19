package com.qkninja.network.proxy;

/**
 * Contains proxy methods shared between server and client.
 *
 * @author QKninja
 */
public abstract class CommonProxy
{
    public abstract void preInit();

    public abstract void init();

    public abstract void postInit();
}
