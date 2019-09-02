package com.revivalmodding.revivalcore.network.packets;

import com.revivalmodding.revivalcore.core.abilities.IAbilityCap;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.util.UUID;

public class PacketSyncAbilities implements IMessage {

    private NBTTagCompound comp;
    private UUID player;

    public PacketSyncAbilities() {}

    public PacketSyncAbilities(NBTTagCompound comp, UUID uuid) {
        this.comp = comp;
        this.player = uuid;
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeTag(buf, comp);
        ByteBufUtils.writeUTF8String(buf, player.toString());
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        comp = ByteBufUtils.readTag(buf);
        player = UUID.fromString(ByteBufUtils.readUTF8String(buf));
    }

    public static class Handler implements IMessageHandler<PacketSyncAbilities, IMessage> {

        @Override
        public IMessage onMessage(PacketSyncAbilities message, MessageContext ctx) {
            Minecraft.getMinecraft().addScheduledTask(() -> {
                EntityPlayer receiver = Minecraft.getMinecraft().player;
                EntityPlayer fromClient = receiver.world.getPlayerEntityByUUID(message.player);
                if(fromClient != null) {
                    IAbilityCap.Impl.get(fromClient).deserializeNBT(message.comp);
                }
            });
            return null;
        }
    }
}
