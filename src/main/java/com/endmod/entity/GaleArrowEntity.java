package com.endmod.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.world.World;

public class GaleArrowEntity extends PersistentProjectileEntity {
    public GaleArrowEntity(World world, LivingEntity owner) {
        super(ModEntities.GALE_ARROW, owner, world);
    }

    public GaleArrowEntity(EntityType<? extends GaleArrowEntity> type, World world) {
        super(type, world);
    }

    @Override
    protected void onHit(LivingEntity target) {
        World world = this.getWorld(); //获取世界引用
        // 弹起目标
        target.addVelocity(0, 1.5, 0);
        // 添加漂浮效果
        target.addStatusEffect(new StatusEffectInstance(
                StatusEffects.LEVITATION,
                60, 0,
                false, false, true
        ));
        // 粒子效果
        if (world != null && !world.isClient()) {
            for (int i = 0; i < 20; i++) {
                world.addParticle(
                        ParticleTypes.CLOUD,
                        target.getX(),
                        target.getY() + target.getHeight() / 2.0,
                        target.getZ(),
                        (random.nextDouble() - 0.5) * 0.2,
                        random.nextDouble() * 0.3,
                        (random.nextDouble() - 0.5) * 0.2
                );
            }
        }
        super.onHit(target);
    }

    @Override
    protected ItemStack asItemStack() {
        return ItemStack.EMPTY;
    }
}