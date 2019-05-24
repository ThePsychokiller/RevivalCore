package com.revivalmodding.revivalcore.core.common.items;

import com.revivalmodding.revivalcore.meta.capability.CapabilityMeta;
import com.revivalmodding.revivalcore.meta.util.MetaHelper;
import com.revivalmodding.revivalcore.util.helper.EnumHelper.InjectionTypes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class ItemInjection extends Item {

    InjectionTypes types;

    public ItemInjection(String name, InjectionTypes injectionType) {
        setTranslationKey(name);
        setRegistryName(name);
        setMaxStackSize(1);
        this.types = injectionType;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer player, EnumHand handIn) {
        ItemStack stack = player.getHeldItem(handIn);
        if (types.getName() != InjectionTypes.EMPTY.name()) {
            if (!MetaHelper.hasPowers(player)) {
                MetaHelper.setMetaPower(player, types.getName());
                changeItemInjection(player, stack, InjectionTypes.EMPTY);
            }
        } else {
            if (MetaHelper.hasPowers(player)) {
                MetaHelper.setEmptyPower(player);
                changeItemInjection(player, stack, InjectionTypes.valueOf(MetaHelper.getMetaPowerName(CapabilityMeta.get(player).getMetaPower())));
            }
        }
        return super.onItemRightClick(worldIn, player, handIn);
    }

    private void changeItemInjection(EntityPlayer player, ItemStack stack, InjectionTypes type) {
        for (Item item : CoreItems.ITEM_LIST) {
            if (player.isCreative()) return;

            if (item instanceof ItemInjection) {
                if (((ItemInjection) item).types.getName() == this.types.getName()) {
                    stack.shrink(1);
                }

                if (((ItemInjection) item).types.getName() == type.getName()) {
                    player.addItemStackToInventory(new ItemStack(item));
                }
            }
        }
    }
}
