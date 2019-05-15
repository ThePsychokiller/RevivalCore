package com.revivalmodding.revivalcore.core.common.suits;

import java.awt.Color;

import javax.annotation.Nullable;
import javax.vecmath.Vector3f;

import com.revivalmodding.revivalcore.core.registry.SuitRegistry;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class AbstractSuit
{
	private final String name;
	
	public AbstractSuit(String name)
	{
		this.name = name;
	}
	
    public static AbstractSuit getSuit(EntityPlayer player) {
    	for(int i = 2; i < 6; ++i) {
    		if(!(player.getItemStackFromSlot(EntityEquipmentSlot.values()[i]).getItem() instanceof ItemSuit)) {
    			return null;
    		}
    	}
    	for(String s : SuitRegistry.SUITS.keySet()) {
    		AbstractSuit suit = SuitRegistry.SUITS.get(s);
    		if(suit.isSuitComplete(player)) {
    			return suit;
    		}
    	}
    	return null;
    }
	
	public abstract ItemSuit getHelmet();
	
	public abstract ItemSuit getChest();
	
	public abstract ItemSuit getLeggings();
	
	public abstract ItemSuit getBoots();
	
	public abstract void handleEffects(EntityPlayer player);
	
	public final void tick(EntityPlayer player)
	{
		this.handleEffects(player);
	}
	
	public final ItemSuit[] getSet()
	{
		return new ItemSuit[] {this.getHelmet(), this.getChest(), this.getLeggings(), this.getBoots()};
	}
	
	public final boolean isSuitComplete(EntityPlayer player)
	{
		return player.getItemStackFromSlot(EntityEquipmentSlot.HEAD).getItem() == this.getHelmet() &&
				player.getItemStackFromSlot(EntityEquipmentSlot.CHEST).getItem() == this.getChest() &&
				player.getItemStackFromSlot(EntityEquipmentSlot.LEGS).getItem() == this.getLeggings() &&
				player.getItemStackFromSlot(EntityEquipmentSlot.FEET).getItem() == this.getBoots();
	}
	
	/**
	 * @return Color for trail
	 */
	@Nullable
	@SideOnly(Side.CLIENT)
	public Color getTrailRGB()
	{
		return Color.RED;
	}
}