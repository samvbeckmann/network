package com.qkninja.network.client.particle;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

/**
 * dilithium-transportation, class made on 6/22/2015.
 *
 * @author Sam Beckmann
 */
@SideOnly(Side.CLIENT)
public class EntityFXBlueSmoke extends EntityFX
{
    float smokeParticleScale;
    //    private ResourceLocation texture =
    public EntityFXBlueSmoke(World world, double posX, double posY, double posZ, double motionX, double motionY, double motionZ)
    {
        this(world, posX, posY, posZ, motionX, motionY, motionZ, 1.0F);
    }

    public EntityFXBlueSmoke(World world, double xPos, double yPos, double zPos, double motionX, double motionY, double motionZ, float var8)
    {
        super(world, xPos, yPos, zPos, 0.0D, 0.0D, 0.0D);
        this.motionX = 0.0D;
        this.motionY = 0.0D;
        this.motionZ = 0.0D;
        this.particleRed = 102 / 255;
        this.particleGreen = 150 / 255;
        this.particleBlue = 180 / 255;
        this.particleScale *= 0.75F;
        this.particleScale *= var8;
        this.smokeParticleScale = this.particleScale;
        this.particleMaxAge = (int) (Math.random() * 40 + 80);
        this.noClip = false;
    }

    @Override
    public void renderParticle(Tessellator tessellator, float p_70539_2_, float p_70539_3_, float p_70539_4_, float p_70539_5_, float p_70539_6_, float p_70539_7_)
    {
        float f6 = ((float)this.particleAge + p_70539_2_) / (float)this.particleMaxAge * 32.0F;

        if (f6 < 0.0F)
        {
            f6 = 0.0F;
        }

        if (f6 > 1.0F)
        {
            f6 = 1.0F;
        }

        this.particleScale = this.smokeParticleScale * f6;
        super.renderParticle(tessellator, p_70539_2_, p_70539_3_, p_70539_4_, p_70539_5_, p_70539_6_, p_70539_7_);
    }

    /**
     * Called to update the entity's position/logic.
     */
    @Override
    public void onUpdate()
    {
        if (this.particleAge++ >= this.particleMaxAge)
        {
            this.setDead();
        }

        this.setParticleTextureIndex(7 - this.particleAge * 8 / this.particleMaxAge);
    }
}
