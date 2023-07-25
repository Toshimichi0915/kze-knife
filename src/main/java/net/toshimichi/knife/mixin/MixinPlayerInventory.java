package net.toshimichi.knife.mixin;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.HoeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.collection.DefaultedList;
import net.toshimichi.knife.KnifeItem;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerInventory.class)
public class MixinPlayerInventory {

    @Final @Shadow public DefaultedList<ItemStack> main;

    @Inject(at = @At("HEAD"), method = "setStack", cancellable = true)
    private void swapKnife(int slot, ItemStack stack, CallbackInfo ci) {
        if (!(stack.getItem() instanceof HoeItem)) return;

        NbtCompound nbt = stack.getNbt();
        if (nbt == null) return;

        KnifeItem item = KnifeItem.fromCustomModelData(nbt.getInt("CustomModelData"));
        if (item == null) return;

        ItemStack knife = item.getDefaultStack();
        knife.setNbt(nbt);

        main.set(slot, knife);
        ci.cancel();
    }
}
