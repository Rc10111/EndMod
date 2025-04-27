package com.endmod;

import com.endmod.block.ModBlocks;
import com.endmod.datagen.ModWorldGenerator;
import com.endmod.effect.ModStatusEffects;
import com.endmod.entity.ModEntities;
import com.endmod.event.ModEvents;
import com.endmod.event.PlayerDamageCallback;
import com.endmod.item.ModItemGroups;
import com.endmod.item.ModItems;
import com.endmod.potion.ModPotions;
import com.endmod.world.gen.ModWorldGeneration;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EndMod implements ModInitializer {
	public static final String MOD_ID = "end-mod";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {

		ModPotions.register();//药水注册
		ModEntities.registerentities();//实体注册
		ModItems.registerItems();//物品注册
		ModItemGroups.registerGroups();//物品栏注册
		ModBlocks.registerModblocks();//方块注册
		ModEvents.registerevents();//事件注册
		ModStatusEffects.registerststeeffects();//效果注册
		PlayerDamageCallback.register();//受伤监听注册
		ModWorldGeneration.generateModWorldGen();//世界注册

		LOGGER.info("Hello Fabric world!");
	}
}