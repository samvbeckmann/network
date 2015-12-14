package com.qkninja.network.handler;

import com.qkninja.network.reference.ConfigValues;
import com.qkninja.network.reference.Messages;
import com.qkninja.network.reference.Reference;
import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.common.config.Configuration;

import java.io.File;

/**
 * A handler used to make the configuration file.
 *
 * @author QKninja
 */
public class ConfigurationHandler
{
    public static Configuration configuration;

    public static void init(File configFile)
    {
        /* Create the configuration object from the given configuration file */
        if (configuration == null)
        {
            configuration = new Configuration(configFile);
            loadConfiguration();
        }
    }

    @SubscribeEvent
    public void onConfigurationChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event)
    {
        if (event.modID.equalsIgnoreCase(Reference.MOD_ID))
        {
            loadConfiguration();
        }
    }

    private static void loadConfiguration()
    {
        ConfigValues.difficulty = configuration.getInt(Messages.Configuration.DIFFICULTY,
                Configuration.CATEGORY_GENERAL, 1, 0, 2, Messages.Configuration.DIFFICULTY_DESCRIPTION);
        ConfigValues.transportDelay = configuration.getInt(Messages.Configuration.TRANSPORT_DELAY,
                Configuration.CATEGORY_GENERAL, 20, 1, 1000, Messages.Configuration.TRANSPORT_DELAY_DESCRIPTION);
        ConfigValues.maxDistanceSq = configuration.getInt(Messages.Configuration.MAX_DISTANCE_SQUARED,
                Configuration.CATEGORY_GENERAL, 256, 1, 4096, Messages.Configuration.MAX_DISTANCE_DESCRIPTION);
        ConfigValues.sparkSpeedFactor = configuration.getInt(Messages.Configuration.SPARK_SPEED_FACTOR,
                Configuration.CATEGORY_GENERAL, 10, 1, 1000, Messages.Configuration.SPARK_SPEED_DESCRIPTION);
        ConfigValues.maxUpgradeNumber = configuration.getInt(Messages.Configuration.MAX_UPGRADE_NUMBER,
                Configuration.CATEGORY_GENERAL, 3, 0, 10, Messages.Configuration.MAX_UPGRADE_DESCRIPTION);

        ConfigValues.standardNumberItemPickup = configuration.getInt(Messages.Configuration.STANDARD_ITEM_PICKUP,
                Configuration.CATEGORY_GENERAL, 1, 1, 64, Messages.Configuration.STANDARD_ITEM_PICKUP_DESCRIPTION);
        ConfigValues.highCapacityItemPickup = configuration.getInt(Messages.Configuration.HICAP_ITEM_PICKUP,
                Configuration.CATEGORY_GENERAL, 64, 1, 64, Messages.Configuration.HICAP_ITEM_PICKUP_DESCRIPTION);
        ConfigValues.standardFluidPickup = configuration.getInt(Messages.Configuration.STANDARD_FLUID_PICKUP,
                Configuration.CATEGORY_GENERAL, 100, 1, 1000, Messages.Configuration.STANDARD_FLUID_PICKUP_DESCRIPTION);
        ConfigValues.highCapacityFluidPickup = configuration.getInt(Messages.Configuration.HICAP_FLUID_PICKUP,
                Configuration.CATEGORY_GENERAL, 1000, 1, 1000, Messages.Configuration.HICAP_FLUID_PICKUP_DESCRIPTION);

        if (configuration.hasChanged())
        {
            configuration.save();
        }
    }
}
