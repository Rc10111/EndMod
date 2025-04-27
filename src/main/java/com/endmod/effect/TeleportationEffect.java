package com.endmod.effect;

import com.endmod.EndMod;
import com.endmod.util.TeleportationHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import java.util.Set;

public class TeleportationEffect extends StatusEffect {
    public TeleportationEffect() {
        super(StatusEffectCategory.BENEFICIAL, 0x00FF00);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return duration == 2; // 只在最后2tick执行
    }

    @Override
    public void onApplied(LivingEntity entity, AttributeContainer attributes, int amplifier) {
        super.onApplied(entity, attributes, amplifier);
        EndMod.LOGGER.info("效果已应用到实体: {}", entity.getName().getString());
    }



    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        if (!entity.getWorld().isClient()) {
            ServerWorld world = (ServerWorld) entity.getWorld();
            Vec3d currentPos = entity.getPos();

            BlockPos newPos = TeleportationHelper.findRandomTeleportPos(
                    world,
                    BlockPos.ofFloored(currentPos),
                    16
            );

            if (newPos != null) {
                boolean success = entity.teleport(
                        world,
                        newPos.getX() + 0.5,
                        newPos.getY(),
                        newPos.getZ() + 0.5,
                        Set.of(),
                        entity.getYaw(),
                        entity.getPitch()
                );

                if (success) {
                    // 不再立即移除效果，让客户端自然处理效果结束
                    // 改为设置极短的持续时间
                    entity.addStatusEffect(new StatusEffectInstance(
                            this,
                            1,  // 只持续1tick
                            amplifier,
                            false,
                            false,
                            false
                    ));
                }
            }
        }
    }
}