package net.dawn.dawnweapons;

import com.mojang.datafixers.util.Pair;
import net.fabricmc.fabric.api.renderer.v1.model.FabricBakedModel;
import net.fabricmc.fabric.api.renderer.v1.render.RenderContext;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.model.Model;
import net.minecraft.client.render.model.*;
import net.minecraft.client.render.model.json.JsonUnbakedModel;
import net.minecraft.client.render.model.json.ModelOverrideList;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resource.Resource;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockRenderView;
import org.jetbrains.annotations.Nullable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;

public class ItemBakedModel implements FabricBakedModel, BakedModel, UnbakedModel {

    private static final HashMap<String, FabricBakedModel> PART_MODELS = new HashMap<>();
    private final ModelIdentifier modelIdentifier;

    public ItemBakedModel(ModelIdentifier modelIdentifier)
    {
        this.modelIdentifier = modelIdentifier;
    }

    @Override
    public void emitItemQuads(ItemStack itemStack, Supplier<Random> supplier, RenderContext renderContext) {
        String tool = "blade";

        String id = "wood_" + tool;

        CompoundTag partTag = itemStack.getSubTag("Parts");
        if(partTag != null)
        {
            id = partTag.getString("bladePart") + "_" + tool;
        }

        PART_MODELS.get(id).emitItemQuads(null, null, renderContext);
    }

    public static ModelTransformation loadTransformFromJson(Identifier location)
    {
        try {
            return JsonUnbakedModel.deserialize((getReaderForResource(location))).getTransformations();
        } catch (IOException exception) {
            DawnWeapons.LOGGER.warn("Can't load resource " + location);
            exception.printStackTrace();
            return null;
        }
    }
    public static ModelTransformation loadTransformFromJsonString(String json)
    {
        return JsonUnbakedModel.deserialize((json)).getTransformations();
    }

    public static Reader getReaderForResource(Identifier location) throws IOException {
        Identifier file = new Identifier(location.getNamespace(), location.getPath() + ".json");
        Resource resource = MinecraftClient.getInstance().getResourceManager().getResource(file);
        return new BufferedReader(new InputStreamReader(resource.getInputStream()));
    }

    @Override
    public @Nullable BakedModel bake(ModelLoader loader, Function<SpriteIdentifier, Sprite> textureGetter, ModelBakeSettings rotationContainer, Identifier modelId) {
        if(PART_MODELS.isEmpty())
        {
            for(String id : DawnWeaponsClient.RENDERING_PARTS)
            {
                PART_MODELS.put(id, (FabricBakedModel) loader.bake(DawnWeaponsClient.inventoryModelID(id), ModelRotation.X0_Y0));
            }
        }
        return this;
    }

    @Override
    public boolean isVanillaAdapter() {
        return false;
    }

    @Override
    public void emitBlockQuads(BlockRenderView blockRenderView, BlockState blockState, BlockPos blockPos, Supplier<Random> supplier, RenderContext renderContext) {

    }

    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction face, Random random) {
        return Collections.emptyList();
    }

    @Override
    public boolean useAmbientOcclusion() {
        return false;
    }

    @Override
    public boolean hasDepth() {
        return false;
    }

    @Override
    public boolean isSideLit() {
        return false;
    }

    @Override
    public boolean isBuiltin() {
        return false;
    }

    @Override
    public Sprite getSprite() {
        return MinecraftClient.getInstance().getSpriteAtlas(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE).apply(new Identifier("block/cobblestone"));
    }

    @Override
    public ModelTransformation getTransformation() {
        /*String model = modelIdentifier.getNamespace() + ":" + modelIdentifier.getPath();
        if(ItemRegistry.MODELS.containsKey(model)) {
            String json = ItemRegistry.MODELS.get(model);
            return loadTransformFromJsonString(json);
        }*/
        return loadTransformFromJson(new Identifier("minecraft:models/item/handheld"));
    }

    @Override
    public ModelOverrideList getOverrides() {
        return ModelOverrideList.EMPTY;
    }

    @Override
    public Collection<Identifier> getModelDependencies() {
        return Collections.emptyList();
    }

    @Override
    public Collection<SpriteIdentifier> getTextureDependencies(Function<Identifier, UnbakedModel> unbakedModelGetter, Set<Pair<String, String>> unresolvedTextureReferences) {
        return Collections.emptyList();
    }
}
