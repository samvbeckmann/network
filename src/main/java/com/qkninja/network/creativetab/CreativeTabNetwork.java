package com.qkninja.network.creativetab;

import com.qkninja.network.init.ModItems;
import com.qkninja.network.reference.Reference;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

/**
 * Defines the Creative tab for Network mod.
 *
 * @author QKninja
 */
public class CreativeTabNetwork
{
    public static final CreativeTabs NETWORK_TAB = new CreativeTabs(Reference.MOD_ID.toLowerCase())
    {
        @Override
        public Item getTabIconItem()
        {
            return ModItems.core;
        }
    };
}
