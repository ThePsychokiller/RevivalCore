package com.revivalmodding.revivalcore.core.client.trail;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public class TrailOptionalData {

    private final Trail parent;

    public int[] stageColors;
    private int secondaryColor;

    public TrailOptionalData(Trail parent) {
        this(parent, -1);
    }

    public TrailOptionalData(Trail parent, int secondaryColor) {
        this(parent, secondaryColor, null);
    }

    public TrailOptionalData(Trail parent, int secondaryColor, @Nullable int[] stageColors) {
        this.parent = parent;
        this.stageColors = new int[parent.length];
        this.secondaryColor = secondaryColor;
        if(stageColors == null) return;
        if(stageColors.length < parent.length) throw new IllegalArgumentException("Stage color array length must be >= parent trail length!");
        System.arraycopy(stageColors, 0, this.stageColors, 0, parent.length);
    }

    @SideOnly(Side.CLIENT)
    public boolean onTrailRender(Vec3d from, Vec3d to, int primaryColor, int primaryTrailWidth, int trailPart, float alpha) {
        // TODO render secondary trail, but smaller
        int color = this.getColor(trailPart, primaryColor);
        return false;
    }

    public boolean hasSecondaryColor() {
        return secondaryColor > -1;
    }

    public Trail getParent() {
        return parent;
    }

    public void saveToNBT(NBTTagCompound nbt) {
        NBTTagCompound tag = new NBTTagCompound();
        tag.setInteger("secondaryColor", this.secondaryColor);
        if(stageColors != null) {
            NBTTagList colorList = new NBTTagList();
            for(int i = 0; i < stageColors.length; i++) {
                colorList.appendTag(new NBTTagInt(stageColors[i]));
            }
            tag.setTag("colorList", colorList);
        }
        nbt.setTag("trailData", tag);
    }

    public void readFromNBT(NBTTagCompound nbt) {
        NBTTagCompound tag = nbt.hasKey("trailData") ? nbt.getCompoundTag("trailData") : new NBTTagCompound();
        this.secondaryColor = tag.getInteger("secondaryColor");
        this.stageColors = tag.hasKey("colorList") ? new int[0] : null;
        if(stageColors != null) {
            NBTTagList colors = tag.getTagList("colorList", Constants.NBT.TAG_INT);
            this.stageColors = new int[colors.tagCount()];
            for(int i = 0; i < colors.tagCount(); i++) {
                this.stageColors[i] = colors.getIntAt(i);
            }
        }
    }

    private int getColor(int trailPart, int defaultColor) {
        return stageColors == null ? defaultColor : stageColors.length <= trailPart ? defaultColor : stageColors[trailPart];
    }
}
