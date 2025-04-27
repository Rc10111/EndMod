package com.endmod.effect;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
//模组效果注册
public class ModStatusEffects {
    //和谐效果注册
    public static final StatusEffect ENDER_HARMONY = register(
            "ender_harmony",
            new EnderHarmonyEffect()
    );
    //漂浮免疫效果注册
    public static final StatusEffect SHULKER_IMMUNITY = register(
            "shulker_immunity",
            new ShulkerEffectImmunity()
    );
    //注册函数
    private static <T extends StatusEffect> T register(String name, T effect) {
        return Registry.register(Registries.STATUS_EFFECT,
                new Identifier("end-mod", name), effect);
    }
    //主类调用注册
    public static void registerststeeffects() {

    }
}