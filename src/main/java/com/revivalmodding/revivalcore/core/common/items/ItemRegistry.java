package com.revivalmodding.revivalcore.core.common.items;

import com.revivalmodding.revivalcore.RevivalCore;
import com.revivalmodding.revivalcore.core.common.blocks.CoreBlockRegistry;
import com.revivalmodding.revivalcore.core.common.suits.donators.ISimpleSuit;
import com.revivalmodding.revivalcore.core.common.suits.donators.ItemDonatorSuit;
import com.revivalmodding.revivalcore.util.helper.EnumHelper.InjectionTypes;
import net.minecraft.block.Block;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.ArrayList;

@Mod.EventBusSubscriber
public class ItemRegistry {

    public static ItemArmor.ArmorMaterial SUIT_MATERIAL = EnumHelper.addArmorMaterial("suit", "", -1, new int[4], 0, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0.0F);
    public static final EntityEquipmentSlot[] ARMOR = {EntityEquipmentSlot.LEGS, EntityEquipmentSlot.CHEST, EntityEquipmentSlot.HEAD};
    public static ArrayList<ItemBlock> ITEMBLOCK_QUEUE;

    @GameRegistry.ObjectHolder(RevivalCore.MODID)
    public static final class CoreItems {
        public static final Item COFFEE_MUG = null;
        public static final Item SUIT_MAKER = null;
        public static final ItemInjection EMPTY_INJECTION = null;
        public static final ItemInjection SPEEDFORCE_INJECTION = null;
        public static final ItemDonatorSuit GRIMMLAWKE_HEAD = null;
        public static final ItemDonatorSuit GRIMMLAWKE_CHEST = null;
        public static final ItemDonatorSuit GRIMMLAWKE_LEGS = null;
        public static final ItemDonatorSuit ROYAL_ZANO_HEAD = null;
        public static final ItemDonatorSuit ROYAL_ZANO_CHEST = null;
        public static final ItemDonatorSuit ROYAL_ZANO_LEGS = null;
        public static final ItemDonatorSuit DARK_MESSIAH_HEAD = null;
        public static final ItemDonatorSuit DARK_MESSIAH_CHEST = null;
        public static final ItemDonatorSuit DARK_MESSIAH_LEGS = null;
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onItemRegister(RegistryEvent.Register<Item> event) {
        event.getRegistry().registerAll(
                new ItemEatable("coffee_mug", 3, 0F, true),
                new ItemSuitMaker(CoreBlockRegistry.CoreBlocks.SUIT_MAKER),
                new ItemInjection("empty_injection", InjectionTypes.EMPTY)
        );

        registerDonatorSuit(event, "grimmlawke", 100, () -> getSuitForName("grimmlawke"));
        registerDonatorSuit(event, "royal_zano", 10, () -> getSuitForName("royal_zano"));
        registerDonatorSuit(event, "dark_messiah", 6, () -> getSuitForName("dark_messiah"));

        event.getRegistry().registerAll(ITEMBLOCK_QUEUE.toArray(new ItemBlock[0]));
        ITEMBLOCK_QUEUE = null;
    }

    public static void registerItemBlock(Block block) {
        if(ITEMBLOCK_QUEUE == null) {
            throw new IllegalStateException("Attempted to register outside Forge registry event!");
        }
        ItemBlock block1 = new ItemBlock(block);
        block1.setRegistryName(block.getRegistryName());
        ITEMBLOCK_QUEUE.add(block1);
    }

    private static void registerDonatorSuit(RegistryEvent.Register<Item> event, String name, double amount, ISimpleSuit simpleSuit) {
        for(EntityEquipmentSlot slot : ARMOR) {
            ItemDonatorSuit suit = new ItemDonatorSuit(name+"_"+slot.getName(), slot, formatString(name), amount, simpleSuit);
            event.getRegistry().register(suit);
        }
    }

    private static String formatString(String string) {
        return string.substring(0, 1).toUpperCase() + string.substring(1).replaceAll("_", " ");
    }

    private static ResourceLocation getSuitForName(String name) {
        return new ResourceLocation(RevivalCore.MODID + ":textures/suits/" + name + ".png");
    }
}
