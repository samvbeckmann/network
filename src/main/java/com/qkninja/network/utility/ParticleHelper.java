package com.qkninja.network.utility;

import com.qkninja.network.client.particle.EntityFXSpark;
import com.qkninja.network.handler.DistanceHandler;
import com.qkninja.network.reference.ConfigValues;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

/**
 * dilithium-transportation, class made on 2015-07-16
 *
 * @author Sam Beckmann
 */
public class ParticleHelper
{
    /**
     * Spawns a particle to travel along the paths of all active connections.
     *
     * @param locs array of locations to send particles to
     */
    public static void spawnSpark(World world, int xCoord, int yCoord, int zCoord, DistanceHandler[] locs)
    {
        for (DistanceHandler handler : locs)
        {
            double x = handler.getX() - xCoord;
            double y = handler.getY() - yCoord;
            double z = handler.getZ() - zCoord;
            Vec3 vector = Vec3.createVectorHelper(x, y, z);
            double distance = vector.lengthVector();

            vector = vector.normalize();

            double xMotion = vector.xCoord / ConfigValues.sparkSpeedFactor;
            double yMotion = vector.yCoord / ConfigValues.sparkSpeedFactor;
            double zMotion = vector.zCoord / ConfigValues.sparkSpeedFactor;
            int lifespan = (int) (distance * ConfigValues.sparkSpeedFactor);

            EntityFX spark = new EntityFXSpark(world, xCoord + 0.5D, yCoord + 0.5D, zCoord + 0.5D, xMotion, yMotion, zMotion, lifespan, 1.0F, false);
            Minecraft.getMinecraft().effectRenderer.addEffect(spark);
        }
    }
}
