package com.endmod.item;

//import com.classdesign.Classdesign;
import com.endmod.EndMod;
import com.endmod.block.ModBlocks;
import com.endmod.potion.ModPotions;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItemGroups {
    //自定义新物品栏, 方法1:
    public static final RegistryKey<ItemGroup> END_MOD = register("end_mod");

    private static RegistryKey<ItemGroup> register(String id) {
        return RegistryKey.of(RegistryKeys.ITEM_GROUP, new Identifier(EndMod.MOD_ID, id));
    }

    public static void registerGroups(){
        Registry.register(
                Registries.ITEM_GROUP,
                END_MOD,
                ItemGroup.create(ItemGroup.Row.TOP, 7)
                        .displayName(Text.translatable("itemGroup.end_mod"))
                        .icon(() -> new ItemStack(ModItems.VOID_BEACON))
                        .entries((displayContext, entries) -> {
                            entries.add(ModItems.VOID_BEACON);
                            entries.add(ModItems.ENDER_PEARL_POWDER);
                            entries.add(ModItems.ENDER_INGOT);
                            entries.add(ModItems.CHORUS_FRUIT_JERKY);
                            entries.add(ModItems.ENDER_CLOAK);
                            entries.add(ModItems.ENDER_LEGGINGS);
                            entries.add(ModItems.SHULKER_BOOTS);
                            entries.add(ModItems.VOID_EDGE);
                            entries.add(ModItems.GALE_BOW);

                            entries.add(ModBlocks.VOID_BEACON_ORE);
                        })
                        .build()
        );
    }
}
