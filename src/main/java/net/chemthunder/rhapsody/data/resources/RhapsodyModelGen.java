package net.chemthunder.rhapsody.data.resources;

import net.chemthunder.rhapsody.impl.Rhapsody;
import net.chemthunder.rhapsody.impl.index.RhapsodyItems;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.client.data.BlockStateModelGenerator;
import net.minecraft.client.data.ItemModelGenerator;
import net.minecraft.client.data.ItemModels;
import net.minecraft.client.data.Models;
import net.minecraft.client.render.item.model.ItemModel;
import net.minecraft.client.render.item.property.select.DisplayContextProperty;
import net.minecraft.item.Item;
import net.minecraft.item.ItemDisplayContext;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

import java.util.Arrays;

import static net.chemthunder.rhapsody.impl.index.RhapsodyItems.*;

public class RhapsodyModelGen extends FabricModelProvider {
    public RhapsodyModelGen(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {

    }

    @Override
    public void generateItemModels(ItemModelGenerator generator) {
        generator.output.accept(HYACINTH, createSimpleGuiVarying(HYACINTH, generator));
    }

    public ItemModel.Unbaked createSimpleGuiVarying(Item item, ItemModelGenerator generator) {
        generator.upload(item, Models.GENERATED);
        Identifier id = Registries.ITEM.getId(item);
        return ItemModels.select(
                new DisplayContextProperty(),
                ItemModels.basic(Rhapsody.id("item/" + id.getPath() + "_handheld")),
                ItemModels.switchCase(
                        Arrays.asList(
                                ItemDisplayContext.GUI,
                                ItemDisplayContext.GROUND,
                                ItemDisplayContext.FIXED
                        ),
                        ItemModels.basic(Rhapsody.id("item/" + id.getPath()))
                )
        );
    }
}
