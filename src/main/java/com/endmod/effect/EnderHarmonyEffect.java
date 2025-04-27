package com.endmod.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.mob.EndermanEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import java.util.List;

//清除末影人敌意效果类
public class EnderHarmonyEffect extends StatusEffect {
    public EnderHarmonyEffect() {
        super(StatusEffectCategory.BENEFICIAL, 0x9932CC);
    }

    @Override
    //触发频率
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return duration % 10 == 0; // 每秒触发一次
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        if (!(entity instanceof PlayerEntity)) return;
        // 遍历周围末影人并清除仇恨
        List<EndermanEntity> endermen = entity.getWorld().getEntitiesByClass(
                EndermanEntity.class,
                entity.getBoundingBox().expand(10),
                enderman -> enderman.getTarget() == entity
        );
        for (EndermanEntity enderman : endermen) {
            enderman.setTarget(null); // 强制取消攻击目标
        }
        // 粒子效果
        if (entity.getWorld() instanceof ServerWorld world) {
            world.spawnParticles(ParticleTypes.PORTAL,
                    entity.getX(), entity.getY() + 1, entity.getZ(),
                    1, 0.3, 0.5, 0.3, 0.01);
        }
    }
}