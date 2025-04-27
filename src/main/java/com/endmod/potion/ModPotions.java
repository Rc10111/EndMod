package com.endmod.potion;

import com.endmod.EndMod;
import com.endmod.effect.TeleportationEffect;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.potion.Potion;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;

public class ModPotions {
    //瞬移效果
    public static final StatusEffect TELEPORTATION_EFFECT = Registry.register(
            Registries.STATUS_EFFECT,
            new Identifier(EndMod.MOD_ID, "teleportation"),
            new TeleportationEffect()
    );
    //药水注册
    public static final Potion TELEPORTATION_POTION = registerPotion(
            "teleportation_potion",
            new Potion(
                    new StatusEffectInstance(TELEPORTATION_EFFECT, 40) // 40 ticks = 2秒
            )
    );

    private static Potion registerPotion(String id, Potion potion) {
        return Registry.register(Registries.POTION,
                RegistryKey.of(Registries.POTION.getKey(), new Identifier(EndMod.MOD_ID, id)),
                potion
        );
    }

    //主类注册函数
    public static void register() {
    }
}