package com.endmod.item;

import com.endmod.entity.GaleArrowEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
//末影弓类
public class GaleBowItem extends BowItem {
    //构造函数
    public GaleBowItem(Settings settings) {
        super(settings);
    }

    @Override
    //末影弓使用逻辑
    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        if (!(user instanceof PlayerEntity player)) return;
        // 计算蓄力强度 (0.0-1.0)
        float pullProgress = BowItem.getPullProgress(this.getMaxUseTime(stack) - remainingUseTicks);
        if (pullProgress < 0.1f) return; // 不足最小蓄力

        if (!world.isClient) {
            // 创建自定义箭
            GaleArrowEntity arrow = new GaleArrowEntity(world, player);
            arrow.setVelocity(player, player.getPitch(), player.getYaw(), 0.0f, pullProgress * 3.0f, 1.0f);

            // 设置箭的伤害和属性
            arrow.setDamage(2.0 * pullProgress);

            // 发射箭
            world.spawnEntity(arrow);
        }
        //调用父类方法处理音效并消耗箭矢
        super.onStoppedUsing(stack, world, user, remainingUseTicks);
    }
}