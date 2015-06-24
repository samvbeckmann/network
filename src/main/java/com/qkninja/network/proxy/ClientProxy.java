package com.qkninja.network.proxy;

import com.qkninja.network.client.renderer.LineRenderer;
import com.qkninja.network.client.renderer.TransporterRenderer;
import com.qkninja.network.tileentity.TileEntityTransporter;
import cpw.mods.fml.client.registry.ClientRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;

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
        registerRenderers();
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

    private void registerRenderers()
    {
        MinecraftForge.EVENT_BUS.register(new LineRenderer(Minecraft.getMinecraft()));

        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityTransporter.class, new TransporterRenderer());
    }
}
