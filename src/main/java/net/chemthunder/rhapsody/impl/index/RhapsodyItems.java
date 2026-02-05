package net.chemthunder.rhapsody.impl.index;

import net.chemthunder.rhapsody.impl.Rhapsody;
import net.chemthunder.rhapsody.impl.item.HyacinthItem;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.Items;
import net.minecraft.item.ToolMaterial;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;

import java.util.function.Function;

import static net.acoyt.acornlib.api.util.ItemUtils.modifyItemNameColor;

public interface RhapsodyItems {
    Item HYACINTH = create("hyacinth", HyacinthItem::new, new Item.Settings().attributeModifiers(HyacinthItem.createAttributeModifiers()).fireproof().maxCount(1));

    static Item create(String name, Function<Item.Settings, Item> factory, Item.Settings settings) {
        return Items.register(RegistryKey.of(RegistryKeys.ITEM, Rhapsody.id(name)), factory, settings);
    }

    static void init() {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(RhapsodyItems::addCombatEntries);

        modifyItemNameColor(HYACINTH, 0x2b1f2e);
    }

    private static void addCombatEntries(FabricItemGroupEntries entries) {
        entries.addAfter(Items.CROSSBOW, HYACINTH);
    }
}
