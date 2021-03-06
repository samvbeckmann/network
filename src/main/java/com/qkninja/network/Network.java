package com.qkninja.network;

import com.qkninja.network.handler.ConfigurationHandler;
import com.qkninja.network.init.ModBlocks;
import com.qkninja.network.init.ModItems;
import com.qkninja.network.init.Recipes;
import com.qkninja.network.proxy.CommonProxy;
import com.qkninja.network.reference.Reference;
import com.qkninja.network.utility.LogHelper;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

/**
 * Main class for the Network mod
 *
 * @author QKninja
 * @version 1.7.10-0.2.1
 */
@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION, guiFactory = Reference.GUI_FACTORY_CLASS)
public class Network
{
    @Mod.Instance(Reference.MOD_ID)
    public static Network instance;

    @SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        ConfigurationHandler.init(event.getSuggestedConfigurationFile());
        FMLCommonHandler.instance().bus().register(new ConfigurationHandler());

        ModItems.init();

        ModBlocks.init();

        proxy.preInit();

        FMLInterModComms.sendMessage("Waila", "register", "com.qkninja.network.thirdparty.waila.Waila.onWailaCall");

        LogHelper.info("Pre Initialization Complete!");

    }

    @Mod.EventHandler
    public void Init(FMLInitializationEvent event)
    {
        proxy.init();

        Recipes.init();

        LogHelper.info("Initialization Complete!");
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        proxy.postInit();

        LogHelper.info("Post Initialization Complete!");

    }

//    @Mod.EventHandler
//    public void onIMCMessages(FMLInterModComms.IMCEvent event)
//    {
//
//    }
}
