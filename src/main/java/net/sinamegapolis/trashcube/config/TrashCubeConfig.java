package net.sinamegapolis.trashcube.config;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = "trashcube")
@Config(modid = "trashcube")
public class TrashCubeConfig {
    @Config.Comment("Maximum size for each stack of of each TrashCube's inventory slot (default:48)")
    @Config.RangeInt(min = 1, max = 64)
    public static int maxStackSize = 48;

    @Config.Comment("The number of inventory slots TrashCube has.(default:3)(make sure to pick up all of your TrashCubes in your saves before changing this value unless minecraft can crash!")
    @Config.RangeInt(min = 1, max = 10)
    public static int slotAmount = 3;

    @SubscribeEvent
    public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event)
    {
        if(event.getModID().equals("trashcube"))
        {
            ConfigManager.sync("trashcube", Config.Type.INSTANCE);
        }
    }
}
