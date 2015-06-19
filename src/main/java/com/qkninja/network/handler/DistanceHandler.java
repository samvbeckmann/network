package com.qkninja.network.handler;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChunkCoordinates;

/**
 * dilithium-transportation, class made on 6/15/2015.
 *
 * @author Sam Beckmann
 */
public class DistanceHandler implements Comparable<DistanceHandler>
{
    private int x, y, z;
    private float distance;

    public DistanceHandler(TileEntity tile, ChunkCoordinates coords)
    {
        this.x = coords.posX;
        this.y = coords.posY;
        this.z = coords.posZ;
        this.distance = coords.getDistanceSquared(tile.xCoord, tile.yCoord, tile.zCoord);
    }

    public DistanceHandler(TileEntity tile, int x, int y, int z)
    {
        this(tile, new ChunkCoordinates(x, y, z));
    }

    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }

    public int getZ()
    {
        return z;
    }

    public float getDistance()
    {
        return distance;
    }

    @Override
    public int compareTo(DistanceHandler object)
    {
        if (object.distance > this.distance)
            return 1;
        else if (object.distance == this.distance)
            return 0;
        else
            return -1;
    }

    @Override
    public boolean equals(Object o)
    {
        if (!(o instanceof DistanceHandler))
            return false;
        DistanceHandler other = (DistanceHandler) o;
        return x == other.x && y == other.y && z == other.z && distance == distance;
    }
}
