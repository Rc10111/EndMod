package com.endmod.effect;
//package com.example.classdesign.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffects;

public class ShulkerEffectImmunity extends StatusEffect {
    public ShulkerEffectImmunity() {
        super(StatusEffectCategory.BENEFICIAL, 0x5D3FD3); // 紫色
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return duration == 1;
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        if (entity.hasStatusEffect(StatusEffects.LEVITATION)) {
            entity.removeStatusEffect(StatusEffects.LEVITATION);
        }
    }
}