package com.qkninja.network.init;

import com.qkninja.network.item.ItemRedLED;
import com.qkninja.network.item.ItemItemCore;
import com.qkninja.network.item.ItemNetwork;
import com.qkninja.network.item.ItemHyperspanner;
import com.qkninja.network.reference.Names;
import com.qkninja.network.reference.Reference;
import cpw.mods.fml.common.registry.GameRegistry;

/**
 * Initializes all the items in the Network mod.
 *
 * @author QKninja
 */
@GameRegistry.ObjectHolder(Reference.MOD_ID)
public class ModItems
{
    public static final ItemNetwork core = new ItemItemCore();
    public static final ItemNetwork hyperspanner = new ItemHyperspanner();
    public static final ItemNetwork redLED = new ItemRedLED();


    public static void init()
    {
        GameRegistry.registerItem(core, Names.Items.ITEM_CORE);
        GameRegistry.registerItem(hyperspanner, Names.Items.HYPERSPANNER);
        GameRegistry.registerItem(redLED, Names.Items.RED_LED);
    }
}
