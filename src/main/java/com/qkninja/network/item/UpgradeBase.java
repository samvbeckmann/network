package com.qkninja.network.item;

import net.minecraft.client.model.ModelBase;
import net.minecraft.util.ResourceLocation;

/**
 * Generic Nexus upgrade.
 *
 * @author QKninja
 */
public abstract class UpgradeBase extends ItemNetwork implements INexusUpgrade
{
    protected ModelBase model;
    protected ResourceLocation texture;

    public UpgradeBase(String name, ModelBase model, ResourceLocation texture)
    {
        super();
        setUnlocalizedName(name);
        this.model = model;
        this.texture = texture;
    }

    @Override
    public ResourceLocation getTexture()
    {
        return texture;
    }

    @Override
    public ModelBase getModel()
    {
        return model;
    }
}
