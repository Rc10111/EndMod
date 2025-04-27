package com.endmod.event;
//package com.example.classdesign.event;

import com.endmod.item.EnderLeggings;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import java.util.Random;

public class PlayerDamageCallback {
    private static final Random random = new Random();
    private static final double TELEPORT_RANGE = 8.0;
    private static final double TELEPORT_CHANCE = 0.2;

    public static void register() {
        // 使用 ALLOW_DAMAGE 事件（返回boolean决定是否允许伤害）
        ServerLivingEntityEvents.ALLOW_DAMAGE.register((entity, source,amount) -> {
            if (shouldTeleportPlayer(entity)) {
                teleportInRange((PlayerEntity)entity, TELEPORT_RANGE);
                return false; // 允许伤害发生（即使传送了）
            }
            return true; // 默认允许伤害

//            if (entity instanceof PlayerEntity player &&
//                    player.getInventory().getArmorStack(1).getItem() instanceof EnderLeggings) {
//
//                // 1. 允许原始伤害事件通过（但会被后续覆盖）
//                // 2. 立即手动造成50%伤害
//
//
//                // 3. 取消原始伤害事件
//                return false;
//            }
//            return true; // 其他情况正常处理
        });
    }

    private static boolean shouldTeleportPlayer(LivingEntity entity) {
        return entity instanceof PlayerEntity player &&
                !player.getWorld().isClient() &&
                player.getInventory().getArmorStack(1).getItem() instanceof EnderLeggings &&
                random.nextDouble() < TELEPORT_CHANCE;
    }


    private static void teleportInRange(PlayerEntity player, double range) {
        if (player.getWorld() instanceof ServerWorld world) {
            Vec3d originalPos = player.getPos();

            // 生成16格范围内的随机位置
            Vec3d targetPos = originalPos.add(
                    (random.nextDouble() - 0.5) * 2 * range,
                    0,
                    (random.nextDouble() - 0.5) * 2 * range
            );

            // 寻找安全位置
            BlockPos safePos = findSafePosition(world, targetPos, player);
            if (safePos != null) {
                // ✅ 正确的teleport方法调用（1.20.1版本）
                player.teleport(
//                        world,
                        safePos.getX() + 0.5, // 中心对准方块中心
                        safePos.getY(),
                        safePos.getZ() + 0.5
//                        player.getYaw(),  // 保持原有水平朝向
//                        player.getPitch() // 保持原有垂直朝向
                );

                // 播放传送音效
                player.playSound(SoundEvents.ENTITY_ENDERMAN_TELEPORT, 1.0F, 1.0F);

                // 可选：添加粒子效果
                world.spawnParticles(ParticleTypes.PORTAL,
                        player.getX(), player.getY(), player.getZ(),
                        50, 0.5, 0.5, 0.5, 0.1);
            }
        }
    }
//    private static void teleportInRange(PlayerEntity player, double range) {
//        ServerWorld world = (ServerWorld) player.getWorld();
//        Vec3d originalPos = player.getPos();
//
//        // 生成16格范围内的随机位置
//        Vec3d targetPos = originalPos.add(
//                (random.nextDouble() - 0.5) * 2 * range,
//                0,
//                (random.nextDouble() - 0.5) * 2 * range
//        );
//
//        // 寻找安全位置并传送
//        BlockPos safePos = findSafePosition(world, targetPos);
//        if (safePos != null) {
//            player.teleport(world,
//                    safePos.getX() + 0.5,
//                    safePos.getY(),
//                    safePos.getZ() + 0.5,
//                    player.getYaw(),
//                    player.getPitch());
//            player.playSound(SoundEvents.ENTITY_ENDERMAN_TELEPORT, 1.0F, 1.0F);
//        }
//    }


    private static BlockPos findSafePosition(ServerWorld world, Vec3d targetPos, PlayerEntity player) {
        BlockPos.Mutable mutablePos = new BlockPos.Mutable(
                targetPos.x, targetPos.y, targetPos.z
        );

        for (int dy = 0; dy < 8; dy++) {
            // 向上搜索
            mutablePos.setY((int)targetPos.y + dy);
            if (isSafePosition(world, mutablePos, player)) {
                return mutablePos.toImmutable();
            }

            // 向下搜索
            mutablePos.setY((int)targetPos.y - dy);
            if (isSafePosition(world, mutablePos, player)) {
                return mutablePos.toImmutable();
            }
        }
        return null;
    }
//    private static BlockPos findSafePosition(ServerWorld world, Vec3d targetPos) {
//        BlockPos.Mutable mutablePos = new BlockPos.Mutable(
//                targetPos.x, targetPos.y, targetPos.z
//        );
//
//        // 垂直搜索范围（±16格）
//        for (int dy = 0; dy < 16; dy++) {
//            // 向上搜索
//            mutablePos.setY((int)targetPos.y + dy);
//            if (isSafePosition(world, mutablePos)) {
//                return mutablePos.toImmutable();
//            }
//
//            // 向下搜索
//            mutablePos.setY((int)targetPos.y - dy);
//            if (isSafePosition(world, mutablePos)) {
//                return mutablePos.toImmutable();
//            }
//        }
//        return null;
//    }


    private static boolean isSafePosition(ServerWorld world, BlockPos pos, PlayerEntity player) {
        // 检查脚下有固体方块且头部有足够空间
        return world.getBlockState(pos.down()).isSolidBlock(world, pos.down()) &&
                world.isSpaceEmpty(player, player.getBoundingBox().offset(
                        pos.getX() - player.getX(),
                        pos.getY() - player.getY(),
                        pos.getZ() - player.getZ()
                ));
    }


//    private static boolean isSafePosition(ServerWorld world, BlockPos pos) {
////        PlayerEntity player = new P;
//        return world.getBlockState(pos.down()).isSolidBlock(world, pos.down()) &&
//                world.isSpaceEmpty(player, player.getBoundingBox().offset(
//                        pos.getX() - player.getX(),
//                        pos.getY() - player.getY(),
//                        pos.getZ() - player.getZ()
//                ));
//    }
}
//
//import com.classdesign.Classdesign;
//import com.classdesign.item.EnderLeggings;
//import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
//import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
//import net.minecraft.entity.Entity;
//import net.minecraft.entity.player.PlayerEntity;
//import net.minecraft.item.ItemStack;
//import net.minecraft.util.ActionResult;
//import net.minecraft.util.Hand;
//import net.minecraft.util.hit.EntityHitResult;
//import net.minecraft.util.math.Vec3d;
//import net.minecraft.world.World;
//import org.jetbrains.annotations.Nullable;
//
//import java.util.Random;
//
//public class PlayerDamageCallback {
//    private static final Random random = new Random();
//    private static final double TELEPORT_RANGE = 16.0;
//    private static final double TELEPORT_CHANCE = 1.0; // 10% chance
//
//    public static void register() {
////        AttackEntityCallback.EVENT.register(PlayerDamageCallback::onPlayerAttack);
//
//        // 重要：添加注册确认日志
//        Classdesign.LOGGER.info("注册监听.");
//
//        // 改用更可靠的LivingEntityDamageCallback
//        ServerLivingEntityEvents.ALLOW_DAMAGE.register((entity, source, amount) -> {
//            if (entity instanceof PlayerEntity player && !player.getWorld().isClient()) {
//                Classdesign.LOGGER.info("D发生上海r");
//
//                ItemStack leggings = player.getInventory().getArmorStack(1);
//                if (leggings.getItem() instanceof EnderLeggings) {
//                    if (new Random().nextDouble() < 1.0) {
//                        teleportPlayer(player);
//                    }
//                }
//            }
//            return true;
//        });
//    }
//
////    private static ActionResult onPlayerAttack(PlayerEntity player, World world, Hand hand, Entity entity, @Nullable EntityHitResult hitResult) {
////        System.out.println("gls;dg");
////        if (!world.isClient()) {
////            ItemStack leggings = player.getInventory().getArmorStack(1); // 1 is the slot for leggings
////
////            if (leggings.getItem() instanceof com.classdesign.item.EnderLeggings) {
////                System.out.println("穿上装备");
////                double d = random.nextDouble();
////                System.out.println(d);
////                if (d < TELEPORT_CHANCE) {
////                    teleportPlayer(player);
////                }
////            }
////        }
////        return ActionResult.PASS;
////    }
//
//    private static void teleportPlayer(PlayerEntity player) {
//        // Generate random offset
//        double offsetX = (random.nextDouble() - 0.5) * 2 * TELEPORT_RANGE;
//        double offsetZ = (random.nextDouble() - 0.5) * 2 * TELEPORT_RANGE;
//
//        // Find safe position
//        Vec3d newPos = findSafeTeleportPosition(player, player.getPos().add(offsetX, 0, offsetZ));
//
//        // Play sound effects
//        player.playSound(net.minecraft.sound.SoundEvents.ENTITY_ENDERMAN_TELEPORT, 1.0F, 1.0F);
//
//        // Teleport player
//        player.teleport(newPos.x, newPos.y, newPos.z);
//
//        // Play sound effects at new location
//        player.playSound(net.minecraft.sound.SoundEvents.ENTITY_ENDERMAN_TELEPORT, 1.0F, 1.0F);
//    }
//
//    private static Vec3d findSafeTeleportPosition(PlayerEntity player, Vec3d targetPos) {
//        World world = player.getEntityWorld();
//
//        // Check if the target position is safe
//        for (int attempts = 0; attempts < 10; attempts++) {
//            // Find a Y position that has space for the player
//            System.out.println("cahsjo"+attempts);
//            for (int y = 0; y < world.getHeight(); y++) {
//                if (world.isSpaceEmpty(player, player.getBoundingBox().offset(targetPos.x - player.getX(), y - player.getY(), targetPos.z - player.getZ()))) {
//                    return new Vec3d(targetPos.x, y, targetPos.z);
//                }
//            }
//
//            // If not found, try a slightly different position
//            targetPos = targetPos.add(
//                    (random.nextDouble() - 0.5) * 4,
//                    0,
//                    (random.nextDouble() - 0.5) * 4
//            );
//        }
//
//        // If all else fails, return original position (no teleport)
//        return player.getPos();
//    }
//}