package net.toshimichi.knife;

import net.fabricmc.api.ModInitializer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class Main implements ModInitializer {

    public static String ID = "kze_knife";

    @Override
    public void onInitialize() {
        for (KnifeItem knife : KnifeItem.KNIVES) {
            Registry.register(Registries.ITEM, new Identifier(ID, knife.getModel().getName()), knife);
        }
    }
}
