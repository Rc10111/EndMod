package com.endmod.item;
//package com.example.classdesign.item;

import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ArmorMaterials;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Lazy;

public class EnderLeggings extends ArmorItem {
    public EnderLeggings(Settings settings) {
        super(ArmorMaterials.DIAMOND, Type.LEGGINGS, settings);
    }

    public enum EnderArmorMaterial implements ArmorMaterial {
        INSTANCE;

        private static final int[] BASE_DURABILITY = new int[]{13, 15, 16, 11};
        private static final int[] PROTECTION_VALUES = new int[]{3, 6, 8, 3};
        private final Lazy<Ingredient> repairIngredient = new Lazy<>(() -> Ingredient.ofItems(Items.ENDER_PEARL));

        @Override
        public int getDurability(Type type) {
            return BASE_DURABILITY[type.getEquipmentSlot().getEntitySlotId()] * 35;
        }

        @Override
        public int getProtection(Type type) {
            return PROTECTION_VALUES[type.getEquipmentSlot().getEntitySlotId()];
        }

        @Override
        public int getEnchantability() {
            return 15;
        }

        @Override
        public SoundEvent getEquipSound() {
            return SoundEvents.ITEM_ARMOR_EQUIP_NETHERITE;
        }

        @Override
        public Ingredient getRepairIngredient() {
            return this.repairIngredient.get();
        }

        @Override
        public String getName() {
            return "ender";
        }

        @Override
        public float getToughness() {
            return 2.0F;
        }

        @Override
        public float getKnockbackResistance() {
            return 0.1F;
        }
    }
}