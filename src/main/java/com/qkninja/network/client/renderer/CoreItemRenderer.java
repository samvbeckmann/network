package com.qkninja.network.client.renderer;

import com.qkninja.network.client.model.ModelCrystal;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;

/**
 * ItemRenderer for various cores
 *
 * @author QK ninja
 */
public class CoreItemRenderer implements IItemRenderer
{
    protected ModelCrystal model;
    protected ResourceLocation texture;

    public CoreItemRenderer(ResourceLocation texture)
    {
        model = new ModelCrystal();
        this.texture = texture;
    }

    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type)
    {
        return true;
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper)
    {
        return true;
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack item, Object... data)
    {
        GL11.glPushMatrix();
        GL11.glTranslatef(0.0F, -1.55F, 0.0F);
        GL11.glScalef(1.6F, 1.6F, 1.6F);

        switch (type)
        {
            case INVENTORY:
                GL11.glScalef(2.5F, 2.5F, 2.5F);
                GL11.glTranslatef(0.0F, -0.6F, 0.0F);
                GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
                break;
            case EQUIPPED:
                GL11.glScalef(2.0F, 2.0F, 2.0F);
                GL11.glTranslatef(0.2F, -0.3F, 0.2F);
                break;
            case EQUIPPED_FIRST_PERSON:
                GL11.glTranslatef(-0.3F, 0.7F, 0.4F);
        }

        Minecraft.getMinecraft().renderEngine.bindTexture(texture);
        model.render(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);

        GL11.glPopMatrix();
    }
}
