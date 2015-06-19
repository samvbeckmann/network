package com.qkninja.network.init;

import com.qkninja.network.item.itemCore;
import com.qkninja.network.item.ItemNetwork;
import com.qkninja.network.item.ItemHyperspanner;
import com.qkninja.network.reference.Names;
import com.qkninja.network.reference.Reference;
import cpw.mods.fml.common.registry.GameRegistry;

/**
 * Initializes all the items in the mod.
 *
 * @author Sam Beckmann
 */
@GameRegistry.ObjectHolder(Reference.MOD_ID)
public class ModItems
{
    public static final ItemNetwork core = new itemCore();
    public static final ItemNetwork hyperspanner = new ItemHyperspanner();


    public static void init()
    {
        GameRegistry.registerItem(core, Names.Items.CORE);
        GameRegistry.registerItem(hyperspanner, Names.Items.HYPERSPANNER);
    }
}
