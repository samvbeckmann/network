package com.qkninja.network.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

/**
 * UpgradeRing - QKninja
 * Created using Tabula 4.1.1
 */
public class ModelUpgrade extends ModelBase {
    public ModelRenderer Low1;
    public ModelRenderer High1;
    public ModelRenderer Low2;
    public ModelRenderer High2;
    public ModelRenderer Low3;
    public ModelRenderer High3;
    public ModelRenderer Low4;
    public ModelRenderer High4;

    public ModelUpgrade() {
        this.textureWidth = 10;
        this.textureHeight = 2;
        this.Low1 = new ModelRenderer(this, 0, 0);
        this.Low1.setRotationPoint(0.0F, 15.62F, 0.0F);
        this.Low1.addBox(-2.0F, 0.0F, 3.83F, 4, 1, 1, 0.0F);
        this.High2 = new ModelRenderer(this, 0, 0);
        this.High2.setRotationPoint(0.0F, 15.38F, 0.0F);
        this.High2.addBox(-2.0F, 0.0F, 3.83F, 4, 1, 1, 0.0F);
        this.setRotateAngle(High2, 0.0F, 2.356194490192345F, 0.0F);
        this.High4 = new ModelRenderer(this, 0, 0);
        this.High4.setRotationPoint(0.0F, 15.38F, 0.0F);
        this.High4.addBox(-2.0F, 0.0F, 3.83F, 4, 1, 1, 0.0F);
        this.setRotateAngle(High4, 0.0F, -0.7853981633974483F, 0.0F);
        this.Low2 = new ModelRenderer(this, 0, 0);
        this.Low2.setRotationPoint(0.0F, 15.62F, 0.0F);
        this.Low2.addBox(-2.0F, 0.0F, 3.83F, 4, 1, 1, 0.0F);
        this.setRotateAngle(Low2, 0.0F, 1.5707963267948966F, 0.0F);
        this.High1 = new ModelRenderer(this, 0, 0);
        this.High1.setRotationPoint(0.0F, 15.38F, 0.0F);
        this.High1.addBox(-2.0F, 0.0F, 3.83F, 4, 1, 1, 0.0F);
        this.setRotateAngle(High1, 0.0F, 0.7853981633974483F, 0.0F);
        this.Low3 = new ModelRenderer(this, 0, 0);
        this.Low3.setRotationPoint(0.0F, 15.62F, 0.0F);
        this.Low3.addBox(-2.0F, 0.0F, 3.83F, 4, 1, 1, 0.0F);
        this.setRotateAngle(Low3, 0.0F, 3.141592653589793F, 0.0F);
        this.High3 = new ModelRenderer(this, 0, 0);
        this.High3.setRotationPoint(0.0F, 15.38F, 0.0F);
        this.High3.addBox(-2.0F, 0.0F, 3.83F, 4, 1, 1, 0.0F);
        this.setRotateAngle(High3, 0.0F, -2.356194490192345F, 0.0F);
        this.Low4 = new ModelRenderer(this, 0, 0);
        this.Low4.setRotationPoint(0.0F, 15.62F, 0.0F);
        this.Low4.addBox(-2.0F, 0.0F, 3.83F, 4, 1, 1, 0.0F);
        this.setRotateAngle(Low4, 0.0F, -1.5707963267948966F, 0.0F);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) { 
        this.Low1.render(f5);
        this.High2.render(f5);
        this.High4.render(f5);
        this.Low2.render(f5);
        this.High1.render(f5);
        this.Low3.render(f5);
        this.High3.render(f5);
        this.Low4.render(f5);
    }

    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
