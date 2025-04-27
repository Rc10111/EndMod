package com.endmod.item;

//package com.example.classdesign.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ChorusFruitJerkyItem extends Item {
    public static final FoodComponent FOOD_COMPONENT = new FoodComponent.Builder()
            .hunger(2)  // 比紫颂果(4)低的饱食度
            .saturationModifier(0.3f)
            .alwaysEdible()  // 任何时候都可以吃
            .snack()  // 快速食用
            .build();
//    public static final FoodComponent FOOD_COMPONENT = new FoodComponent.Builder()
//            .hunger(2)  // 比紫颂果(4)低的饱食度
//            .saturationModifier(0.3f)
//            .alwaysEdible()  // 任何时候都可以吃
//            .snack()  // 快速食用
//            .statusEffect(new StatusEffectInstance(StatusEffects.LEVITATION, 20, 0), 1.0F)  // 保持紫颂果的漂浮效果
//            .build();
//
//    public ChorusFruitJerkyItem(Settings settings) {
//        super(settings);
//    }
    public ChorusFruitJerkyItem(Settings settings) {
        super(settings);
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        ItemStack result = super.finishUsing(stack, world, user);

        if (!world.isClient && user instanceof PlayerEntity) {
            this.randomTeleport((PlayerEntity)user, 5);  // 5格范围内随机传送
        }

        return result;
    }

    private void randomTeleport(PlayerEntity player, int range) {
        if (!player.getWorld().isClient()) {
            double x = player.getX() + (player.getRandom().nextDouble() - 0.5) * range;
            double y = player.getY() + (player.getRandom().nextInt(range * 2) - range);
            double z = player.getZ() + (player.getRandom().nextDouble() - 0.5) * range;

            // 确保不会传送到太高的地方或地下
            y = Math.min(y, player.getWorld().getHeight());
            y = Math.max(y, player.getWorld().getBottomY() + 1);

            BlockPos.Mutable pos = new BlockPos.Mutable(x, y, z);

            // 寻找安全的位置
            while (pos.getY() > player.getWorld().getBottomY() &&
                    !player.getWorld().getBlockState(pos).blocksMovement()) {
                pos.move(0, -1, 0);
            }
            pos.move(0, 1, 0);

            // 使用新的 teleport 方法
            boolean teleportSuccess = player.teleport(x, pos.getY(), z, true);
            if (teleportSuccess) {
                player.playSound(SoundEvents.ITEM_CHORUS_FRUIT_TELEPORT, 1.0F, 1.0F);
            }
//            else System.out.println("fmsdf");
        }
    }
}