package com.revivalmodding.revivalcore.core.common.tileentity;

import com.revivalmodding.revivalcore.core.recipes.RVRecipe;
import com.revivalmodding.revivalcore.core.registry.SuitMakerRecipeRegistry;
import com.revivalmodding.revivalcore.network.NetworkManager;
import com.revivalmodding.revivalcore.network.packets.PacketSyncProcessTE;
import com.revivalmodding.revivalcore.util.helper.RVHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Set;

public class TileEntitySuitMaker extends TileEntityRC implements IProcessCraftSystem<RVRecipe>, ITickable
{
    public static final TextComponentTranslation NAME = new TextComponentTranslation("container.suitMaker");
    protected NonNullList<ItemStack> inventory = NonNullList.<ItemStack>withSize(21, ItemStack.EMPTY);
    private boolean isProcessing;
    private int processTime = 0;
    @Nullable
    public RVRecipe currRecipe = null;

    @Override
    public ITextComponent getDisplayName()
    {
        return NAME;
    }

    @Override
    public String getName()
    {
        return NAME.getKey();
    }

    @Override
    public NonNullList<ItemStack> getInventory()
    {
        return inventory;
    }

    @Override
    public void setInventory(NonNullList<ItemStack> inv)
    {
        this.inventory = inv;
    }

    @Override
    public int getOutput()
    {
        return 0;
    }

    @Override
    public Set<RVRecipe> getRegistry()
    {
        return SuitMakerRecipeRegistry.RECIPES;
    }

    @Override
    public void closeInventory(EntityPlayer player)
    {
        InventoryHelper.dropInventoryItems(world, pos, this);
    }

    @Override
    public AxisAlignedBB getRenderBoundingBox() {
        net.minecraft.util.math.AxisAlignedBB bb = INFINITE_EXTENT_AABB;
        return bb;
    }

    @Override
    public void onLoad() {
        super.onLoad();
    }

    @Override
    public boolean canRenderBreaking() {
        return true;
    }

    @Override
    public void setProcessing(boolean processing)
    {
        this.isProcessing = processing;
    }

    @Override
    public boolean isProcessing()
    {
        return isProcessing;
    }

    @Override
    public int getProcessTimer()
    {
        return processTime;
    }

    @Override
    public RVRecipe getRecipe()
    {
        return currRecipe;
    }

    @Override
    public void process()
    {
        ++this.processTime;
    }

    @Override
    public void resetProcessTimer()
    {
        this.processTime = 0;
    }

    @Override
    public void setRecipe(RVRecipe recipe)
    {
        this.currRecipe = recipe;
    }

    @Override
    public void setProcessTimer(int timer)
    {
        this.processTime = timer;
    }

    @Override
    public int getSizeInventory()
    {
        return 21;
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);
        compound.setBoolean("isProcessing", isProcessing);
        compound.setInteger("processTime", processTime);
        compound.setString("currentRecipe", currRecipe == null ? "" : currRecipe.getName());
        return compound;
    }

    public static void sync(IProcessCraftSystem te, BlockPos pos) {
        NetworkManager.INSTANCE.sendToAll(new PacketSyncProcessTE(te, pos));
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);
        setProcessing(compound.getBoolean("isProcessing"));
        setProcessTimer(compound.getInteger("processTime"));
        setRecipe(RVRecipe.getRecipeFromName(compound.getString("currentRecipe")));
    }

    @Override
    public void update()
    {
        if(this.isProcessing())
        {
        	if(world.isRemote)
        		RVHelper.spawnParticles(world, EnumParticleTypes.SMOKE_NORMAL, pos, 0.15, 4);
        	
            this.process();

            if(this.getProcessTimer() >= this.getRecipe().getCraftTime())
            {
                this.onProcessFinished(this);
            }

            if(this.isInvalid()) {
                sync(this, pos);
            }
        }
    }
}