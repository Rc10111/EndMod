package com.endmod.item;
//package com.example.classdesign.item.custom;

import com.endmod.effect.ModStatusEffects;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ShulkerBootsItem extends ArmorItem {
    public ShulkerBootsItem(ArmorMaterial material, Settings settings) {
        super(material, Type.BOOTS, settings);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, net.minecraft.entity.Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, world, entity, slot, selected);

        if (!world.isClient && entity instanceof PlayerEntity player) {
            // 检查是否穿上了鞋子
            if (player.getEquippedStack(EquipmentSlot.FEET).isOf(this)) {
                // 添加免疫效果
                player.addStatusEffect(new StatusEffectInstance(ModStatusEffects.SHULKER_IMMUNITY, 200, 0, true, false));
            }
        }
    }

    @Override
    public void onCraft(ItemStack stack, World world, PlayerEntity player) {
        super.onCraft(stack, world, player);
        if (!world.isClient) {
            player.addStatusEffect(new StatusEffectInstance(ModStatusEffects.SHULKER_IMMUNITY, 200, 0, true, false));
        }
    }
}