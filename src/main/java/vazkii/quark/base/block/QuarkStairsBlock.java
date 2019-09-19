package vazkii.quark.base.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.StairsBlock;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;
import vazkii.arl.util.RegistryHelper;
import vazkii.quark.base.module.Module;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Objects;
import java.util.function.Supplier;

public class QuarkStairsBlock extends StairsBlock implements IQuarkBlock {

	private final IQuarkBlock parent;
    private Supplier<Boolean> enabledSupplier = () -> true;

    public QuarkStairsBlock(IQuarkBlock parent) {
		super(parent.getBlock().getDefaultState(), Block.Properties.from(parent.getBlock()));
		
		this.parent = parent;
		RegistryHelper.registerBlock(this, Objects.toString(parent.getBlock().getRegistryName()) + "_stairs");
		RegistryHelper.setCreativeTab(this, ItemGroup.BUILDING_BLOCKS);
	}
	
	@Override
	public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> items) {
		if(group == ItemGroup.SEARCH || isEnabled())
			super.fillItemGroup(group, items);
	}

    @Nullable
    @Override
    public Module getModule() {
        return parent.getModule();
    }

    @Override
    public QuarkStairsBlock setCondition(Supplier<Boolean> enabledSupplier) {
        this.enabledSupplier = enabledSupplier;
        return this;
    }

    @Override
    public boolean doesConditionApply() {
        return enabledSupplier.get();
    }

    @Nullable
    @Override
    public float[] getBeaconColorMultiplier(BlockState state, IWorldReader world, BlockPos pos, BlockPos beaconPos) {
        return parent.getBlock().getBeaconColorMultiplier(parent.getBlock().getDefaultState(), world, pos, beaconPos);
    }

    @Nonnull
    public BlockRenderLayer getRenderLayer() {
        return parent.getBlock().getRenderLayer();
    }

}
