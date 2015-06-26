package com.qkninja.network.client.particle;

import net.minecraft.client.particle.EntityFX;
import net.minecraft.world.World;

/**
 * dilithium-transportation, class made on 6/22/2015.
 *
 * @author Sam Beckmann
 */
public class EntityBluesmokeFX extends EntityFX
{
    protected EntityBluesmokeFX(World world, double xPos, double yPos, double zPos)
    {
        super(world, xPos, yPos, zPos);
    }

    public EntityBluesmokeFX(World world, double xPos, double yPos, double zPos, double xMotion, double yMotion, double zMotion)
    {
        super(world, xPos, yPos, zPos, xMotion, yMotion, zMotion);

    }
}
