package com.endmod.util;

import com.endmod.potion.ModPotions;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;

public class TeleportationUtil {
    public static void applyTeleportEffect(LivingEntity entity) {
        if (!entity.getWorld().isClient()) {
            // 应用可见的短暂效果(客户端能看到Buff图标)
            entity.addStatusEffect(new StatusEffectInstance(
                    ModPotions.TELEPORTATION_EFFECT,
                    20,  // 20 ticks = 1秒
                    0,
                    false,
                    true,  // 显示粒子
                    true   // 显示图标
            ));
        }
    }
}