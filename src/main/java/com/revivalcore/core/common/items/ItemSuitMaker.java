package com.revivalcore.core.common.items;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;

public class ItemSuitMaker extends ItemBlock {

    public ItemSuitMaker(Block block) {
        super(block);
        this.setRegistryName(block.getRegistryName()+ "_item");
        this.setMaxStackSize(1);
        this.setMaxDamage(0);
        this.setHasSubtypes(false);

        CoreItems.ITEM_LIST.add(this);
    }
}