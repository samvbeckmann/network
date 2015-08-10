package com.qkninja.network.init;

import com.qkninja.network.item.*;
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
    public static final ItemNetwork itemCore = new ItemItemCore();
    public static final ItemNetwork hyperspanner = new ItemHyperspanner();
    public static final ItemNetwork redLED = new ItemRedLED();
    public static final ItemNetwork blueLED = new ItemBlueLED();
    public static final ItemNetwork greenLED = new ItemGreenLED();
    public static final ItemNetwork fluidCore = new ItemFluidCore();


    public static void init()
    {
        GameRegistry.registerItem(itemCore, Names.Items.ITEM_CORE);
        GameRegistry.registerItem(hyperspanner, Names.Items.HYPERSPANNER);
        GameRegistry.registerItem(redLED, Names.Items.RED_LED);
        GameRegistry.registerItem(blueLED, Names.Items.BLUE_LED);
        GameRegistry.registerItem(greenLED, Names.Items.GREEN_LED);
        GameRegistry.registerItem(fluidCore, Names.Items.FLUID_CORE);
    }
}
