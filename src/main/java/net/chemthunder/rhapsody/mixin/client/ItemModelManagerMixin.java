package net.chemthunder.rhapsody.mixin.client;

import net.chemthunder.rhapsody.impl.index.RhapsodyItems;
import net.minecraft.client.item.ItemModelManager;
import net.minecraft.client.render.item.ItemRenderState;
import net.minecraft.client.render.item.model.ItemModel;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemDisplayContext;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Function;

@Mixin(ItemModelManager.class)
public abstract class ItemModelManagerMixin {
    @Shadow
    @Final
    private Function<Identifier, ItemModel> modelGetter;

    @Inject(method = "update", at = @At("HEAD"), cancellable = true)
    private void gilded$blockingItemModels(ItemRenderState renderState, ItemStack stack, ItemDisplayContext displayContext, World world, LivingEntity entity, int seed, CallbackInfo ci) {
        if (stack != null && !stack.isEmpty() && stack.isOf(RhapsodyItems.HYACINTH)) {
            ci.cancel();
            Identifier id = Registries.ITEM.getId(stack.getItem());
            if (entity != null && entity.isUsingItem()) {
                this.modelGetter.apply(Identifier.of(id.getNamespace(), id.getPath() + "_blocking"))
                        .update(renderState, stack, (ItemModelManager) (Object) this, displayContext, world instanceof ClientWorld clientWorld ? clientWorld : null, entity, seed);
            } else {
                this.modelGetter.apply(id)
                        .update(renderState, stack, (ItemModelManager) (Object) this, displayContext, world instanceof ClientWorld clientWorld ? clientWorld : null, entity, seed);
            }
        }
    }
}