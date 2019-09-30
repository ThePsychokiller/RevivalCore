package com.revivalmodding.revivalcore.util.handlers.client;

import com.revivalmodding.revivalcore.meta.capability.CapabilityMeta;
import com.revivalmodding.revivalcore.meta.capability.IMetaCap;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

import java.util.Random;

@Mod.EventBusSubscriber(Side.CLIENT)
public class ClientEventHandler
{

    @SubscribeEvent
    public static void renderPlayerPre(RenderPlayerEvent.Pre e) {
        Random r = new Random();
        EntityPlayer player = e.getEntityPlayer();
        IMetaCap cap = CapabilityMeta.get(player);
        GlStateManager.pushMatrix();

        if (cap.isVibrating()) {
            GlStateManager.enableBlend();
            for (int i = 0; i < 1; i++) {
                GlStateManager.translate((r.nextFloat() - 1F) / 80, 0, (r.nextFloat() - 1F) / 80);
                GlStateManager.color(1, 1, 1, 0.4F);
            }
        }
    }

    @SubscribeEvent
    public static void renderPlayerPost(RenderPlayerEvent.Post e) {
        EntityPlayer player = e.getEntityPlayer();
        IMetaCap cap = CapabilityMeta.get(player);

        if (cap.isVibrating()) {
            GlStateManager.disableBlend();
        }
        GlStateManager.popMatrix();
    }
}