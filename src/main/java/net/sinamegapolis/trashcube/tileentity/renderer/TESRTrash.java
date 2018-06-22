package net.sinamegapolis.trashcube.tileentity.renderer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.client.model.animation.FastTESR;
import net.minecraftforge.client.model.pipeline.LightUtil;
import net.minecraftforge.common.model.IModelPart;
import net.minecraftforge.common.model.IModelState;
import net.minecraftforge.common.model.TRSRTransformation;
import net.sinamegapolis.trashcube.othersutill.ModelHandle;
import net.sinamegapolis.trashcube.tileentity.TileEntityTrash;


import javax.annotation.Nullable;
import javax.vecmath.Vector3f;
import java.util.Optional;

public class TESRTrash extends FastTESR<TileEntityTrash> {
    @Override
    public void renderTileEntityFast(TileEntityTrash te, double x, double y, double z, float partialTicks, int destroyStage, float partial, BufferBuilder buffer) {
        float pix = 1f/16;
        if (!(te instanceof TileEntityTrash)) return; // should never happen
        try {
            if (te.isnModuleAttached())
                this.renderModule((float) x, (float) y, (float) z, "module_notification", DefaultVertexFormats.BLOCK, buffer, null);
            if (te.isbModuleAttached())
                this.renderModule((float) x, (float) y, (float) z, "module_blacklist", DefaultVertexFormats.BLOCK, buffer, null);
            if (te.iswModuleAttached())
                this.renderModule((float) x, (float) y, (float) z, "module_whitelist", DefaultVertexFormats.BLOCK, buffer, null);
        }catch (NullPointerException e){
            e.printStackTrace();
        }
    }

    private boolean renderModule(float x, float y, float z, String moduleType, VertexFormat vf, BufferBuilder buffer, @Nullable EnumFacing side){
        try {
            IModel model = ModelLoaderRegistry.getModel(new ResourceLocation("trashcube","block/"+moduleType));
            IBakedModel bakedModel = model.bake(new TRSRTransformation(new Vector3f(x,y,z),null,null,null), vf, ModelLoader.defaultTextureGetter());
            for (BakedQuad bakedquad : bakedModel.getQuads(null, side, 0)) {
                buffer.addVertexData(bakedquad.getVertexData());
            }
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
