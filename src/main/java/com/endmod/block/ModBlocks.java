package com.endmod.block;

import com.endmod.EndMod;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlocks {
    //lootables:战利品列表
    //tags掉落物

    //虚空水晶矿注册
    public static final Block VOID_BEACON_ORE = register("void_beacon_ore", new Block(AbstractBlock.Settings.copy(Blocks.STONE).requiresTool()));
    //方块注册函数
    public static Block register(String id, Block block) {
        registerBlockItems(id, block);
        return Registry.register(Registries.BLOCK,new Identifier(EndMod.MOD_ID, id), block);
    }
    //方块物品注册函数
    public static void registerBlockItems(String id, Block block){
        Registry.register(Registries.ITEM, new Identifier(EndMod.MOD_ID, id),
                new BlockItem(block, new Item.Settings())
                );
    }

    //主类注册函数
    public static void registerModblocks(){
    }

}


