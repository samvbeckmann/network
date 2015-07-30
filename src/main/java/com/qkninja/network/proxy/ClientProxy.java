package com.qkninja.network.proxy;

import com.qkninja.network.client.renderer.LineRenderer;
import com.qkninja.network.client.renderer.TransporterItemRenderer;
import com.qkninja.network.client.renderer.TransporterRenderer;
import com.qkninja.network.client.renderer.UpgradeItemRenderer;
import com.qkninja.network.init.ModBlocks;
import com.qkninja.network.init.ModItems;
import com.qkninja.network.reference.Names;
import com.qkninja.network.tileentity.TileEntityTransporter;
import com.qkninja.network.utility.ResourceLocationHelper;
import cpw.mods.fml.client.registry.ClientRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;

/**
 * Contains methods for client-side proxy operations.
 *
 * @author QKninja
 */
@SuppressWarnings("unused")
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

        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ModBlocks.transporter), new TransporterItemRenderer());

        MinecraftForgeClient.registerItemRenderer(ModItems.redLED, new UpgradeItemRenderer(ResourceLocationHelper.getResourceLocation(Names.Models.RED_LED)));
        MinecraftForgeClient.registerItemRenderer(ModItems.blueLED, new UpgradeItemRenderer(ResourceLocationHelper.getResourceLocation(Names.Models.BLUE_LED)));
        MinecraftForgeClient.registerItemRenderer(ModItems.greenLED, new UpgradeItemRenderer(ResourceLocationHelper.getResourceLocation(Names.Models.GREEN_LED)));
    }
}
