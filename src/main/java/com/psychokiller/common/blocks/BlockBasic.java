package com.psychokiller.common.blocks;

import java.util.List;

import com.psychokiller.revivalcore.Registries;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class BlockBasic extends Block
{
	private String[] description = new String[0];
	
	public BlockBasic(String name, Material material) 
	{
		super(material);
		setTranslationKey(name);
		setRegistryName(name);
		
		// automatically registers item block for this instance of block
		Registries.Registry.registerItemBlock(this);
	}
	
	public BlockBasic(String name)
	{
		this(name, Material.ROCK);
	}
	
	public BlockBasic(String name, Material material, String... description)
	{
		this(name, material);
		this.description = description;
	}
	
	public BlockBasic addDescription(String... strings)
	{
		this.description = strings;
		return this;
	}
	
	@Override
	public void addInformation(ItemStack stack, World player, List<String> tooltip, ITooltipFlag advanced)
	{
		if(description.length > 0)
		{
			for(String s : description)
			{
				tooltip.add(s);
			}
		}
	}
}
