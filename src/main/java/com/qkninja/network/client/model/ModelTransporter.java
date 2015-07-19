package com.qkninja.network.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

/**
 * transporter - QKninja
 * Created using Tabula 4.1.1
 */
public class ModelTransporter extends ModelBase
{
    public ModelRenderer shape6;
    public ModelRenderer shape7;
    public ModelRenderer shape8;
    public ModelRenderer shape8_1;
    public ModelRenderer shape8_2;
    public ModelRenderer shape8_3;

    public ModelTransporter()
    {
        this.textureWidth = 46;
        this.textureHeight = 11;
        this.shape8_1 = new ModelRenderer(this, 12, 0);
        this.shape8_1.setRotationPoint(0.0F, 20.7F, 0.0F);
        this.shape8_1.addBox(-0.5F, 0.0F, -0.5F, 1, 5, 1, 0.0F);
        this.setRotateAngle(shape8_1, -0.9712757287348444F, 0.0F, 0.0F);
        this.shape8_2 = new ModelRenderer(this, 12, 0);
        this.shape8_2.setRotationPoint(0.0F, 20.7F, 0.0F);
        this.shape8_2.addBox(-0.5F, 0.0F, -0.5F, 1, 5, 1, 0.0F);
        this.setRotateAngle(shape8_2, 0.0F, 0.0F, -0.9712757287348444F);
        this.shape7 = new ModelRenderer(this, 0, 4);
        this.shape7.setRotationPoint(0.0F, 20.0F, 0.0F);
        this.shape7.addBox(-1.5F, 0.0F, -1.5F, 3, 3, 3, 0.0F);
        this.shape6 = new ModelRenderer(this, 6, 0);
        this.shape6.setRotationPoint(0.0F, 23.0F, 0.0F);
        this.shape6.addBox(-5.0F, 0.0F, -5.0F, 10, 1, 10, 0.0F);
        this.shape8 = new ModelRenderer(this, 12, 0);
        this.shape8.setRotationPoint(0.0F, 20.7F, 0.0F);
        this.shape8.addBox(-0.5F, 0.0F, -0.5F, 1, 5, 1, 0.0F);
        this.setRotateAngle(shape8, 0.0F, 0.0F, 0.9712757287348444F);
        this.shape8_3 = new ModelRenderer(this, 12, 0);
        this.shape8_3.setRotationPoint(0.0F, 20.7F, 0.0F);
        this.shape8_3.addBox(-0.5F, 0.0F, -0.5F, 1, 5, 1, 0.0F);
        this.setRotateAngle(shape8_3, 0.9712757287348444F, 0.0F, 0.0F);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
        this.shape8_1.render(f5);
        this.shape8_2.render(f5);
        this.shape7.render(f5);
        this.shape6.render(f5);
        this.shape8.render(f5);
        this.shape8_3.render(f5);
    }

    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z)
    {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
