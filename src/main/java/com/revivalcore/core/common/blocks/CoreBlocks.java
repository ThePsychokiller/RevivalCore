package com.revivalcore.core.common.blocks;

import com.revivalcore.RevivalCore;
import com.revivalcore.core.common.items.CoreItems;
import com.revivalcore.util.helper.IHaveItem;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemBlock;

import java.util.ArrayList;
import java.util.List;

public class CoreBlocks {
    public static final List<Block> BLOCK_LIST = new ArrayList<Block>();

    public static Block SUIT_MAKER = RegisterBlock(new BlockSuitMaker( Material.ROCK), "suit_maker", true);


    public static Block RegisterBlock(Block block, String name, boolean tab) {
        block.setRegistryName(name);
        block.setTranslationKey(name);
        CoreBlocks.BLOCK_LIST.add(block);

        if (block instanceof IHaveItem) {
            ItemBlock itemBlock = (ItemBlock) new ItemBlock(block).setRegistryName(name);

            if (tab) {
                block.setCreativeTab(RevivalCore.coretab);
            }
            CoreItems.registerRender(itemBlock);
            CoreItems.ITEM_LIST.add(itemBlock);

        }
        return block;
    }
}