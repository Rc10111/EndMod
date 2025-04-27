package com.endmod.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.Heightmap;
import net.minecraft.world.World;
//虚空之刃类
public class VoidEdgeItem extends SwordItem {
    private static final float TELEPORT_CHANCE = 0.5f;
    //构造函数
    public VoidEdgeItem(ToolMaterial material, int attackDamage, float attackSpeed, Settings settings) {
        super(material, attackDamage, attackSpeed, settings);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        float i = target.getRandom().nextFloat();
        System.out.println(i);
        if (!target.getWorld().isClient() && i < TELEPORT_CHANCE) {
            teleportTarget(target);
        }
        return super.postHit(stack, target, attacker);
    }
    //随机传送函数
    private void teleportTarget(LivingEntity target) {
        World world = target.getWorld();
        double x = target.getX() + (target.getRandom().nextDouble() - 0.5) * 16.0;
        double z = target.getZ() + (target.getRandom().nextDouble() - 0.5) * 16.0;
        double y = world.getTopY(Heightmap.Type.MOTION_BLOCKING, (int)x, (int)z) + 1.0;

        // 确保 Y 坐标合法
        y = MathHelper.clamp(y, world.getBottomY(), world.getTopY() - 1);
        if (target.hasVehicle()) {
            target.stopRiding();
        }
        boolean teleportSuccess = target.teleport((int)x, (int)y, (int)z, true);
//        //传送日志
//        if (!teleportSuccess) {
//            System.out.println("传送失败！原因: " +
//                    (y < world.getBottomY() ? "Y值过低" :
//                            y > world.getTopY() ? "Y值过高" :
//                                    target.hasVehicle()?"fhlsdf":"未知"));
//
//            System.out.println(x+" "+y+" "+z);
//        }
        if (teleportSuccess) {
            // 播放音效
            world.playSound(null, target.getX(), target.getY(), target.getZ(),
                    SoundEvents.ENTITY_ENDERMAN_TELEPORT, SoundCategory.PLAYERS, 1.0F, 1.0F);
            target.playSound(SoundEvents.ENTITY_ENDERMAN_TELEPORT, 1.0F, 1.0F);
            // 生成粒子
            if (world instanceof ServerWorld serverWorld) {
                serverWorld.spawnParticles(ParticleTypes.PORTAL,
                        target.getX(), target.getY() + 1.0, target.getZ(),
                        30,
                        0.5, 1.0, 0.5,
                        0.05);
            }
        }
    }
}
