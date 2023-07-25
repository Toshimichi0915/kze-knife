package net.toshimichi.knife;

import lombok.Data;
import lombok.Getter;
import net.minecraft.item.HoeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ToolMaterials;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.client.RenderProvider;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoItemRenderer;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.function.Consumer;
import java.util.function.Supplier;

@Getter
public class KnifeItem extends HoeItem implements GeoItem {

    public static final KnifeItem CROWBAR = new KnifeItem(new KnifeModel(1040, "crowbar"));
    public static final KnifeItem PEN_KNIFE = new KnifeItem(new KnifeModel(1131, "pen_knife"));
    public static final KnifeItem RAW_FISH = new KnifeItem(new KnifeModel(1171, "raw_fish"));
    public static final KnifeItem SURVIVAL_KNIFE = new KnifeItem(new KnifeModel(1031, "survival_knife"));
    public static final KnifeItem TETRA_KNIFE = new KnifeItem(new KnifeModel(1141, "tetra_knife"));
    public static final KnifeItem[] KNIVES = new KnifeItem[]{CROWBAR, PEN_KNIFE, RAW_FISH, SURVIVAL_KNIFE, TETRA_KNIFE};

    public static final String CONTROLLER_NAME = "knife_controller";
    public static final String NORMAL_ANIMATION_NAME = "normal";
    public static final RawAnimation NORMAL_ANIMATION = RawAnimation.begin()
            .thenPlay("pick1")
            .thenLoop("idle");

    private final KnifeModel model;
    private final AnimatableInstanceCache animatableInstanceCache = GeckoLibUtil.createInstanceCache(this);
    private final Supplier<Object> renderProvider = GeoItem.makeRenderer(this);

    public KnifeItem(KnifeModel model) {
        super(ToolMaterials.DIAMOND, -3, 0.0f, new Item.Settings());
        this.model = model;
    }

    @Override
    public void createRenderer(Consumer<Object> consumer) {
        consumer.accept(new KnifeRenderProvider(new KnifeItemRenderer(model)));
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        AnimationController<?> controller = new AnimationController<>(
                this,
                CONTROLLER_NAME,
                0,
                state -> PlayState.STOP)
                .triggerableAnim(NORMAL_ANIMATION_NAME, NORMAL_ANIMATION);

        controllers.add(controller);
    }

    public static KnifeItem fromCustomModelData(int data) {
        for (KnifeItem knife : KNIVES) {
            if (knife.getModel().getCustomModelData() == data) {
                return knife;
            }
        }

        return null;
    }

    private static class KnifeItemRenderer extends GeoItemRenderer<KnifeItem> {

        public KnifeItemRenderer(GeoModel<KnifeItem> model) {
            super(model);
        }
    }

    @Data
    private static class KnifeRenderProvider implements RenderProvider {

        private final KnifeItemRenderer customRenderer;
    }
}
