package net.sinamegapolis.trashcube;

import net.minecraft.init.Blocks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.sinamegapolis.trashcube.proxy.CommonProxy;
import net.sinamegapolis.trashcube.utill.GuiHandler;
import org.apache.logging.log4j.Logger;

@Mod(modid = TrashCube.MODID, name = TrashCube.NAME, version = TrashCube.VERSION)
public class TrashCube
{
    public static final String MODID = "trashcube";
    public static final String NAME = "TrashCube";
    public static final String VERSION = "1.0";

    private static Logger logger;

    @SidedProxy(clientSide = "net.sinamegapolis.trashcube.proxy.ClientProxy", serverSide = "net.sinamegapolis.trashcube.proxy.CommonProxy")
    public static CommonProxy PROXY;

    @Mod.Instance("trashcube")
    public static TrashCube instance;

    @EventHandler
    public void preInit(FMLPreInitializationEvent e){
        PROXY.preInit(e);
        PROXY.registerRenderers();
        NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler());
    }
    @EventHandler
    public void init(FMLInitializationEvent e) {
        MinecraftForge.EVENT_BUS.register(this);
        PROXY.init(e);
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent e) {
        PROXY.postInit(e);
    }
}
