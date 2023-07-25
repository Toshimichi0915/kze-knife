package net.toshimichi.knife;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

@RequiredArgsConstructor
@Getter
public class KnifeModel extends GeoModel<KnifeItem> {

    private final int customModelData;
    private final String name;

    @Override
    public Identifier getModelResource(KnifeItem animatable) {
        return new Identifier(Main.ID, "geo/" + name + ".geo.json");
    }

    @Override
    public Identifier getTextureResource(KnifeItem animatable) {
        return new Identifier(Main.ID, "textures/" + name + ".png");
    }

    @Override
    public Identifier getAnimationResource(KnifeItem animatable) {
        return new Identifier(Main.ID, "animations/" + name + ".animation.json");
    }
}
