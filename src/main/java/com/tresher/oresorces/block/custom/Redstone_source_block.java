package com.tresher.oresorces.block.custom;

import com.tresher.oresorces.block.entity.Source_blockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RedStoneOreBlock;
import net.minecraft.world.level.block.RedstoneTorchBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class Redstone_source_block extends Source_block {
    public static final BooleanProperty LIT = RedstoneTorchBlock.LIT;

    public Redstone_source_block(Properties properties){
        super(properties);
        this.registerDefaultState(this.defaultBlockState()
                .setValue(LIT,false)
        );
    }
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(LIT,AGE);
    }
    @Override
    protected boolean isRandomlyTicking(BlockState state) {
        return state.getValue(AGE) < MAX_AGE || state.getValue(LIT);
    }

    @Override
    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (state.getValue(AGE)>=MAX_AGE){
            level.setBlockAndUpdate(pos,state.setValue(LIT, false));
            return;
        }
        if(level.getBlockEntity(pos) instanceof Source_blockEntity source_blockEntity){
            int age = source_blockEntity.getAge(level.getGameTime());
            if(age>=state.getValue(AGE)){
                level.setBlockAndUpdate(pos,state
                        .setValue( AGE,Math.min(MAX_AGE,age ))
                        .setValue(LIT, false)
                );
                return;
            }
        }
        if (state.getValue(LIT)) {
            level.setBlock(pos, state.setValue(LIT, false), 3);
        }
    }
    @Override
    public boolean onDestroyedByPlayer(BlockState state, Level level, BlockPos pos, Player player, boolean willHarvest, FluidState fluid) {
        if(player.isCreative())
            return super.onDestroyedByPlayer(state, level, pos, player, willHarvest, fluid);

        int currentAGE=state.getValue(AGE);
        if(currentAGE>=1){
            if (player.getMainHandItem().is(net.minecraft.tags.ItemTags.PICKAXES))
                dropResources(state, level, pos, null, player, player.getMainHandItem());
            if(currentAGE==MAX_AGE) {
                level.setBlockAndUpdate(pos, state.setValue(AGE, currentAGE - 1));
                if(level.getBlockEntity(pos) instanceof Source_blockEntity source_blockEntity){
                    source_blockEntity.setPlacedDay(level.getGameTime(),1,MAX_AGE);
                }
            }
            else{
                if(level.getBlockEntity(pos) instanceof Source_blockEntity source_blockEntity){
                    source_blockEntity.remove1Stage();
                }
                level.setBlockAndUpdate(pos,state
                        .setValue(LIT, currentAGE>1 ? state.getValue(LIT) : false)
                        .setValue(AGE, currentAGE-1)
                );
            }

        }
        return false;

    }


    @Override
    public @Nullable PushReaction getPistonPushReaction(BlockState state) {
        return PushReaction.BLOCK;
    }

    //redstone_ore
    protected void attack(BlockState state, Level level, BlockPos pos, Player player) {
        interact(state, level, pos);
        super.attack(state, level, pos, player);
    }
    //redstone_ore
    public void stepOn(Level level, BlockPos pos, BlockState state, Entity entity) {
        if (!entity.isSteppingCarefully()) {
            interact(state, level, pos);
        }

        super.stepOn(level, pos, state, entity);
    }
    //redstone_ore
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (level.isClientSide) {
            spawnParticles(level, pos);
        } else {
            interact(state, level, pos);
        }

        return stack.getItem() instanceof BlockItem && (new BlockPlaceContext(player, hand, stack, hitResult)).canPlace() ? ItemInteractionResult.SKIP_DEFAULT_BLOCK_INTERACTION : ItemInteractionResult.SUCCESS;
    }
    //redstone_ore
    protected static void interact(BlockState state, Level level, BlockPos pos) {
        if(state.getValue(AGE)==0)return;
        spawnParticles(level, pos);
        if (!(Boolean)state.getValue(LIT)) {
            level.setBlock(pos, (BlockState)state.setValue(LIT, true), 3);
        }

    }
    //redstone_ore
    /*protected void spawnAfterBreak(BlockState state, ServerLevel level, BlockPos pos, ItemStack stack, boolean dropExperience) {
        super.spawnAfterBreak(state, level, pos, stack, dropExperience);
    }*/
    //redstone_ore
    /*public int getExpDrop(BlockState state, LevelAccessor level, BlockPos pos, @Nullable BlockEntity blockEntity, @Nullable Entity breaker, ItemStack tool) {
        return UniformInt.of(1, 5).sample(level.getRandom());
    }*/
    //redstone_ore
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        if (state.getValue(LIT)) {
            spawnParticles(level, pos);
        }

    }
    //redstone_ore
    private static void spawnParticles(Level level, BlockPos pos) {
        double d0 = 0.5625F;
        RandomSource randomsource = level.random;

        for(Direction direction : Direction.values()) {
            BlockPos blockpos = pos.relative(direction);
            if (!level.getBlockState(blockpos).isSolidRender(level, blockpos)) {
                Direction.Axis direction$axis = direction.getAxis();
                double d1 = direction$axis == Direction.Axis.X ? (double)0.5F + (double)0.5625F * (double)direction.getStepX() : (double)randomsource.nextFloat();
                double d2 = direction$axis == Direction.Axis.Y ? (double)0.5F + (double)0.5625F * (double)direction.getStepY() : (double)randomsource.nextFloat();
                double d3 = direction$axis == Direction.Axis.Z ? (double)0.5F + (double)0.5625F * (double)direction.getStepZ() : (double)randomsource.nextFloat();
                level.addParticle(DustParticleOptions.REDSTONE, (double)pos.getX() + d1, (double)pos.getY() + d2, (double)pos.getZ() + d3, (double)0.0F, (double)0.0F, (double)0.0F);
            }
        }

    }

}
