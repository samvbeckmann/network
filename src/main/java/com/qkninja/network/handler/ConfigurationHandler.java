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
        // Create the configuration object from the given configuration file
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
        ConfigValues.difficulty = configuration.getInt(Messages.Configuration.DIFFICULTY, Configuration.CATEGORY_GENERAL, 1, 0, 2, Messages.Configuration.DIFFICULTY_DESCRIPTION);
        ConfigValues.transportDelay = configuration.getInt(Messages.Configuration.TRANSPORT_DELAY, Configuration.CATEGORY_GENERAL, 20, 1, 1000, Messages.Configuration.TRANSPORT_DELAY_DESCRIPTION);
        ConfigValues.maxDistanceSq = configuration.getInt(Messages.Configuration.MAX_DISTANCE_SQUARED, Configuration.CATEGORY_GENERAL, 256, 1, 4096, Messages.Configuration.MAX_DISTANCE_DESCRIPTION);

        if (configuration.hasChanged())
        {
            configuration.save();
        }
    }
}
