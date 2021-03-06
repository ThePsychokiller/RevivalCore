package com.revivalmodding.revivalcore.core.common.blocks;

import com.revivalmodding.revivalcore.RevivalCore;
import com.revivalmodding.revivalcore.core.common.items.ItemRegistry;
import com.revivalmodding.revivalcore.core.common.tileentity.IProcessCraftSystem;
import com.revivalmodding.revivalcore.core.common.tileentity.TileEntitySuitMaker;
import com.revivalmodding.revivalcore.util.handlers.GuiHandlerRV;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockSuitMaker extends BlockBasic {

    public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
    protected static final AxisAlignedBB MODEL_NORTH_AABB = new AxisAlignedBB(0, 0, 0, 1.6, 0.6, -0.5);
    protected static final AxisAlignedBB MODEL_SOUTH_AABB = new AxisAlignedBB(0, 0, 0, 2, 0.6, -0.8);
    protected static final AxisAlignedBB MODEL_WEST_AABB = new AxisAlignedBB(0.2, 0, 1, 1, 0.6, -2);
    protected static final AxisAlignedBB MODEL_EAST_AABB = new AxisAlignedBB(-0.2, 0, -1, 1, 0.6, 1);
    protected static final AxisAlignedBB MODEL_UP_AABB = new AxisAlignedBB(0, 0, 0, 1, 0.6, 1);

    public BlockSuitMaker(String name, Material material) {
        super(name, material);
        setCreativeTab(RevivalCore.coretab);
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        if(!playerIn.isSneaking() && !playerIn.world.isRemote)
        {
        	playerIn.openGui(RevivalCore.instance, GuiHandlerRV.GUI_SUITMAKER, worldIn, pos.getX(), pos.getY(), pos.getZ());
        	TileEntity te = worldIn.getTileEntity(pos);
        	((IProcessCraftSystem)te).sync(te);
        }
        return true;
    }

    @Override
    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        super.getDrops(drops, world, pos, state, fortune);
        drops.clear();
        drops.add(new ItemStack(ItemRegistry.CoreItems.SUIT_MAKER));
         TileEntitySuitMaker tileEntitySuitMaker = (TileEntitySuitMaker) world.getTileEntity(pos);
         for(int i = 0; i < tileEntitySuitMaker.getInventory().size(); i++) {
             drops.add(new ItemStack(tileEntitySuitMaker.getStackInSlot(i).getItem()));
         }
    }

    @Nullable
    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
        switch (blockState.getValue(FACING)) {
            case EAST:
                return MODEL_EAST_AABB;
            case WEST:
                return MODEL_WEST_AABB;
            case SOUTH:
                return MODEL_SOUTH_AABB;
            case NORTH:
                return MODEL_NORTH_AABB;
            default:
                return MODEL_UP_AABB;
        }
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(FACING, EnumFacing.byHorizontalIndex(meta));
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        switch (state.getValue(FACING)) {
            case EAST:
                return new AxisAlignedBB(0.2, 0, 0, 1, 0.6, 1);
            case WEST:
                return new AxisAlignedBB(0.2, 0, 1, 1, 0.6, -1.4);
            case SOUTH:
                return new AxisAlignedBB(-0.5, 0, 0, 1.8, 0.6, -0.8);
            case NORTH:
                return new AxisAlignedBB(-0.5, 0, 0, 1.8, 0.6, -0.5);
            default:
                return new AxisAlignedBB(-1, 0, -1, 1, 0.6, 1);
        }
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(FACING).getHorizontalIndex();
    }

    @Override
    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
        return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING);
    }


    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.INVISIBLE;
    }

    @Override
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.TRANSLUCENT;
    }

    @Override
    public boolean isNormalCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TileEntitySuitMaker();
    }

    @Override
    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
        return BlockFaceShape.UNDEFINED;
    }

    @Override
    public boolean addDestroyEffects(World world, BlockPos pos, ParticleManager manager) {
        return super.addDestroyEffects(world, pos, manager);
    }
}
