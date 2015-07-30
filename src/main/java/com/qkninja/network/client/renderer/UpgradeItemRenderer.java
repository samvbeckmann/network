package com.qkninja.network.client.renderer;

import com.qkninja.network.client.model.ModelUpgrade;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;

/**
 * dilithium-transportation, class made on 2015-07-24
 *
 * @author Sam Beckmann
 */
public class UpgradeItemRenderer implements IItemRenderer
{
    protected ModelUpgrade model;
    ResourceLocation texture;

    public UpgradeItemRenderer(ResourceLocation texture)
    {
        model = new ModelUpgrade();
        this.texture = texture;
    }


    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type)
    {
        return !type.equals(ItemRenderType.INVENTORY);
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper)
    {
        if (type.equals(ItemRenderType.ENTITY))
        {
            switch (helper)
            {
                case ENTITY_BOBBING:
                case ENTITY_ROTATION:
                    return true;
                default:
                    return false;
            }
        } else
            return false;
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack item, Object... data)
    {
        GL11.glPushMatrix();
        GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
        GL11.glTranslatef(-0.05F, 1.6F, -0.32F);
        GL11.glScalef(1.6F, 1.6F, 1.6F);

        switch (type)
        {
            case EQUIPPED:
                GL11.glTranslatef(-0.7F, -0.8F, 0.0F);
                GL11.glRotatef(-90.0F, 1.0F, 0.0F, 0.0F);
                GL11.glRotatef(90.0F, 0.0F, 0.0F, 1.0F);
                break;
            case EQUIPPED_FIRST_PERSON:
                GL11.glTranslatef(0.2F, -0.1F, -0.1F);
                break;
//            case INVENTORY:
//                GL11.glScalef(15.0F, 15.0F, 15.0F);
//                GL11.glTranslatef(0.34F, 0.0F, -0.32F);
        }

        GL11.glRotatef(180, 0.0F, 0.0F, 1.0F);
        Minecraft.getMinecraft().renderEngine.bindTexture(texture);
        model.render(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);

        GL11.glPopMatrix();
    }
}
