package com.endmod.util;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;

public class TeleportationHelper {

    public static BlockPos findRandomTeleportPos(ServerWorld world, BlockPos center, int radius) {
        Random random = world.getRandom();

        for (int i = 0; i < 16; i++) {
            int x = center.getX() + random.nextInt(radius * 2) - radius;
            int z = center.getZ() + random.nextInt(radius * 2) - radius;

            BlockPos.Mutable mutablePos = new BlockPos.Mutable(x, world.getTopY(), z);

            while (mutablePos.getY() > world.getBottomY()) {
                BlockState state = world.getBlockState(mutablePos);

                if (isSafePosition(world, mutablePos)) {
                    return mutablePos.toImmutable();
                }

                mutablePos.move(0, -1, 0);
            }
        }

        return null;
    }
    //落点安全判断
    private static boolean isSafePosition(ServerWorld world, BlockPos pos) {
        BlockState state = world.getBlockState(pos);
        BlockState stateAbove = world.getBlockState(pos.up());
        BlockState stateBelow = world.getBlockState(pos.down());

        return state.isAir() &&
                stateAbove.isAir() &&
                !stateBelow.isAir() &&
                !stateBelow.isOf(Blocks.LAVA) &&
                !stateBelow.isOf(Blocks.MAGMA_BLOCK) &&
                !stateBelow.isOf(Blocks.CAMPFIRE) &&
                !stateBelow.isOf(Blocks.FIRE);
    }
}