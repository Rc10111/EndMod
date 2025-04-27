package com.endmod.item;

import com.endmod.EndMod;
import com.endmod.potion.ModPotions;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.*;
import net.minecraft.potion.Potions;
import net.minecraft.recipe.BrewingRecipeRegistry;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

public class ModItems {
    //虚空水晶注册
    public static final Item VOID_BEACON = register("void_beacon", new Item(new Item.Settings()));
    //末影珍珠粉末注册
    public static final Item ENDER_PEARL_POWDER = register("ender_pearl_powder", new Item(new Item.Settings()));
    //末影锭注册
    public static final Item ENDER_INGOT= register("ender_ingot", new Item(new Item.Settings()));
    //紫颂果干注册
    public static final Item CHORUS_FRUIT_JERKY = register("chorus_fruit_jerky",
            new ChorusFruitJerkyItem(new FabricItemSettings().food(ChorusFruitJerkyItem.FOOD_COMPONENT))
    );
    //虚空之刃注册
    public static final Item VOID_EDGE = register("void_edge",
            new VoidEdgeItem(
                    ToolMaterials.NETHERITE, // 材料等级
                    0, // 基础攻击伤害
                    -2.4f, // 攻击速度
                    new FabricItemSettings()
                            .rarity(Rarity.RARE)
                            .maxDamage(2031)
            )
    );
    //末影斗篷注册
    public static final Item ENDER_CLOAK = register(
            "ender_cloak",
            new EnderCloakItem(ArmorMaterials.DIAMOND, new Item.Settings()
                    .maxCount(1)
                    .rarity(Rarity.RARE)
                    .fireproof()
            )
    );
    //末影弓注册
    public static final Item GALE_BOW = register(
            "gale_bow",
            new GaleBowItem(new FabricItemSettings()
                    .maxCount(1)
                    .rarity(Rarity.UNCOMMON)
            )
    );
    //末影护腿注册
    public static final Item ENDER_LEGGINGS = register("ender_leggings",
            new EnderLeggings(new FabricItemSettings()
                    .rarity(Rarity.UNCOMMON)
                    .maxDamage(500)
            )
    );
    //末影鞋注册
    public static final Item SHULKER_BOOTS = register("shulker_boots",
            new ShulkerBootsItem(
                    ArmorMaterials.NETHERITE, // 使用下界合金材料
                    new FabricItemSettings()
                            .rarity(Rarity.UNCOMMON)
                            .maxCount(1)
            )
    );
    //物品注册函数
    public static Item register(String id, Item item){
        return Registry.register(Registries.ITEM,
                RegistryKey.of(Registries.ITEM.getKey(), new Identifier(EndMod.MOD_ID, id)),
                item);
    }
    //主类调用注册所有物品
    public static void registerItems(){
        //药水酿造配方
        BrewingRecipeRegistry.registerPotionRecipe(
                Potions.AWKWARD,
                Items.ENDER_PEARL,
                ModPotions.TELEPORTATION_POTION
        );
    }
}
