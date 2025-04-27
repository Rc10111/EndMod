package com.endmod.entity;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEntities {
    //末影弓箭实体注册
    public static final EntityType<GaleArrowEntity> GALE_ARROW = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier("end-mod", "gale_arrow"),
            FabricEntityTypeBuilder.<GaleArrowEntity>create(SpawnGroup.MISC, GaleArrowEntity::new)
                    .dimensions(EntityDimensions.fixed(0.5F, 0.5F))
                    .trackRangeBlocks(4)
                    .trackedUpdateRate(20)
                    .build()
    );
    //主类注册
    public static void registerentities() {
    }
}