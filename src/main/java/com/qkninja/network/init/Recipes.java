package com.qkninja.network.init;

import com.qkninja.network.reference.ConfigValues;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.ShapedOreRecipe;

/**
 * Initializes standard crafting recipes.
 *
 * @author QKninja
 */
public class Recipes
{
    public static void init()
    {
        switch (ConfigValues.difficulty)
        {
            case 0:
                GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.core, 3),
                        " i ", "idi", " i",
                        'i', new ItemStack(Blocks.iron_bars),
                        'd', "gemDiamond"));
                GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.transporter),
                        "   ", " c ", "iri",
                        'c', new ItemStack(ModItems.core),
                        'i', "ingotIron",
                        'r', "dustRedstone"));
                break;
            case 1:
                GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.core),
                        " i ", "idi", " i",
                        'i', new ItemStack(Blocks.iron_bars),
                        'd', "gemDiamond"));
                GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.transporter),
                        "   ", " c ", "iri",
                        'c', new ItemStack(ModItems.core),
                        'i', "blockIron",
                        'r', "blockRedstone"));
                break;
            case 2:
                GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.core),
                        " i ", "idi", " i",
                        'i', new ItemStack(Blocks.iron_bars),
                        'd', "blockDiamond"));
                GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.transporter),
                        "   ", " c ", "iri",
                        'c', new ItemStack(ModItems.core),
                        'i', "blockIron",
                        'r', "blockRedstone"));
                break;
        }

        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.hyperspanner),
                "igi", "ili", "iri",
                'i', "ingotIron",
                'g', "dustGlowstone",
                'l', "paneGlass",
                'r', "dustRedstone"));
    }
}
