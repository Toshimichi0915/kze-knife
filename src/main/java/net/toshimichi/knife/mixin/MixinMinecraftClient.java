package net.toshimichi.knife.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.toshimichi.knife.KnifeItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import software.bernie.geckolib.animatable.GeoItem;

@Mixin(MinecraftClient.class)
public class MixinMinecraftClient {

    @Shadow public ClientPlayerEntity player;

    @Unique private int animationId = 320600;
    @Unique private int prevSlot = -1;
    @Unique private ItemStack prevStack = ItemStack.EMPTY;

    @Unique
    private long newAnimationId(ItemStack stack) {
        NbtCompound tag = stack.getOrCreateNbt();

        long id = animationId++;
        tag.putLong(GeoItem.ID_NBT_KEY, id);

        return id;
    }


    @Inject(at = @At("HEAD"), method = "tick")
    private void startKnifeAnimation(CallbackInfo ci) {
        if (player == null) return;

        int slot = player.getInventory().selectedSlot;
        ItemStack mainHand = player.getInventory().getStack(slot);

        ItemStack prevStack = this.prevStack;
        this.prevStack = mainHand;

        if (prevSlot == slot && prevStack.getItem() instanceof KnifeItem) return;
        prevSlot = slot;

        if (!(mainHand.getItem() instanceof KnifeItem item)) return;

        //TODO possible memory leak for creating a new animation ID every time? We might want to investigate this later
        item.triggerAnim(player, newAnimationId(mainHand), KnifeItem.CONTROLLER_NAME, KnifeItem.NORMAL_ANIMATION_NAME);
    }
}
