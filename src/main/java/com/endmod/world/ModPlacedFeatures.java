package com.endmod.world;

import com.endmod.EndMod;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.placementmodifier.HeightRangePlacementModifier;
import net.minecraft.world.gen.placementmodifier.PlacementModifier;

import java.util.List;

public class ModPlacedFeatures {
    public static final RegistryKey<PlacedFeature> VOID_BEACON_ORE_KEY_PLACEDKEY = registerKey("void_beacon_ore_placed");


    public static void bootstrap(Registerable<PlacedFeature> featureRegisterable){
        var configuredlookup = featureRegisterable.getRegistryLookup(RegistryKeys.CONFIGURED_FEATURE);

        register(featureRegisterable, VOID_BEACON_ORE_KEY_PLACEDKEY, configuredlookup.getOrThrow(ModConfiguredFeatures.VOID_BEACON_ORE_KEY),
                ModOrePlacement.modifiersWithCount(5,//矿脉密度 一区块内5个矿脉
                        HeightRangePlacementModifier.uniform(YOffset.fixed(-50), YOffset.fixed(80))));
    }

    public static RegistryKey<PlacedFeature> registerKey(String id) {
        return RegistryKey.of(RegistryKeys.PLACED_FEATURE, new Identifier(EndMod.MOD_ID, id));
    }

    public static void register(
            Registerable<PlacedFeature> featureRegisterable,
            RegistryKey<PlacedFeature> key,
            RegistryEntry<ConfiguredFeature<?, ?>> feature,
            List<PlacementModifier> modifiers
    ) {
        featureRegisterable.register(key, new PlacedFeature(feature, List.copyOf(modifiers)));
    }
}
