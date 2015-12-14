package com.qkninja.network.init;

import com.qkninja.network.reference.ConfigValues;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ShapedRecipes;
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
                GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.itemCore, 3),
                        " i ", "idi", " i",
                        'i', new ItemStack(Blocks.iron_bars),
                        'd', "gemDiamond"));
                GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.transporter),
                        "   ", " c ", "iri",
                        'c', new ItemStack(ModItems.itemCore),
                        'i', "ingotIron",
                        'r', "dustRedstone"));
                break;
            case 1:
                GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.itemCore),
                        " i ", "idi", " i ",
                        'i', new ItemStack(Blocks.iron_bars),
                        'd', "gemDiamond"));
                GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.transporter),
                        "   ", " c ", "iri",
                        'c', new ItemStack(ModItems.itemCore),
                        'i', "blockIron",
                        'r', "blockRedstone"));
                break;
            case 2:
                GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.itemCore),
                        " i ", "idi", " i",
                        'i', new ItemStack(Blocks.iron_bars),
                        'd', "blockDiamond"));
                GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.transporter),
                        "   ", " c ", "iri",
                        'c', new ItemStack(ModItems.itemCore),
                        'i', "blockIron",
                        'r', "blockRedstone"));
                break;
        }
        String glowstone = ConfigValues.difficulty == 2 ? "blockGlowstone" : "dustGlowstone";

        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.hyperspanner),
                "igi", "ili", "iri",
                'i', "ingotIron",
                'g', "dustGlowstone",
                'l', "paneGlass",
                'r', "dustRedstone"));

        GameRegistry.addShapedRecipe(new ItemStack(ModItems.fluidCore),
                " b ", "bcb", " b ",
                'b', new ItemStack(Items.lava_bucket),
                'c', new ItemStack(ModItems.itemCore));

        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.blueLED),
                " l ", " d ", "igi",
                'l', "paneGlass",
                'd', "gemDiamond",
                'i', "ingotIron",
                'g', glowstone));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.redLED),
                " l ", " d ", "igi",
                'l', "paneGlass",
                'd', "ingotGold",
                'i', "ingotIron",
                'g', glowstone));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.greenLED),
                " l ", " d ", "igi",
                'l', "paneGlass",
                'd', "slimeball",
                'i', "ingotIron",
                'g', glowstone));
    }
}
