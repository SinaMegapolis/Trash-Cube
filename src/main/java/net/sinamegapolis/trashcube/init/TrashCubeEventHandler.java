package net.sinamegapolis.trashcube.init;

import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(value = Side.CLIENT, modid = "trashcube")
public class TrashCubeEventHandler {

    private TrashCubeEventHandler(){}

    @SubscribeEvent
    public static void onTextureStitch(TextureStitchEvent event){
        TextureMap map = event.getMap();
        map.registerSprite(new ResourceLocation("trashcube","blocks/whitelist"));
        map.registerSprite(new ResourceLocation("trashcube","blocks/notification"));
        map.registerSprite(new ResourceLocation("trashcube","blocks/blacklist"));
    }
}
