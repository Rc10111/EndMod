package com.endmod.item;

import com.endmod.effect.ModStatusEffects;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
//末影斗篷类
public class EnderCloakItem extends ArmorItem {
    public EnderCloakItem(ArmorMaterial material, Settings settings) {
        super(material, Type.CHESTPLATE, settings);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (!(entity instanceof PlayerEntity player)) return;

        if (player.getEquippedStack(EquipmentSlot.CHEST).isOf(this)) {
            // 末影和谐效果
            player.addStatusEffect(new StatusEffectInstance(
                    ModStatusEffects.ENDER_HARMONY, 40, 0, false, false));
            // 潜行隐身
            if (player.isSneaking()) {
                player.addStatusEffect(new StatusEffectInstance(
                        StatusEffects.INVISIBILITY, 60, 0, false, false));
            }
        }
    }
}