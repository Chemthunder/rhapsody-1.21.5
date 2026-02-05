package net.chemthunder.rhapsody.ported.api;

import net.minecraft.item.ItemStack;

/*
Ported from Nitrogne
 */
public interface ColorableItem {
    int startColor(ItemStack stack);
    int endColor(ItemStack stack);
    int backgroundColor(ItemStack stack);
}