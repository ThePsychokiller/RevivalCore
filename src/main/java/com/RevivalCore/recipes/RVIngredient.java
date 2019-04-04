package com.RevivalCore.recipes;

import net.minecraft.item.ItemStack;

public class RVIngredient
{
	public final int index;
	public final ItemStack ingredient;
	
	public RVIngredient(int slotIndex, ItemStack ingredient)
	{
		this.index = slotIndex;
		this.ingredient = ingredient;
	}
}