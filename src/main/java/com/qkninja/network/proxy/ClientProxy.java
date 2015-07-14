package com.qkninja.network.proxy;

import com.qkninja.network.client.renderer.LineRenderer;
import com.qkninja.network.client.renderer.TransporterItemRenderer;
import com.qkninja.network.client.renderer.TransporterRenderer;
import com.qkninja.network.init.ModBlocks;
import com.qkninja.network.tileentity.TileEntityTransporter;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;

/**
 * Contains methods for client-side proxy operations.
 *
 * @author QKninja
 */
public class ClientProxy extends CommonProxy
{
    private TileEntitySpecialRenderer renderer = new TransporterRenderer();
    private TileEntity te = new TileEntityTransporter(); // TODO put in reasonable place

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

        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ModBlocks.transporter), new TransporterItemRenderer(renderer, te));
    }
}
