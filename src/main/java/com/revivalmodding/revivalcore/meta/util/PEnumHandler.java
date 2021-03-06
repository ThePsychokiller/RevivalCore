package com.revivalmodding.revivalcore.meta.util;

import net.minecraft.util.IStringSerializable;

public class PEnumHandler {

    public enum MetaPower implements IStringSerializable {

        SPEEDSTER("speedster", 0);

        private int ID;
        private String name;

        MetaPower(String name, int ID) {
            this.name = name;
            this.ID = ID;
        }

        public int getID() {
            return ID;
        }

        @Override
        public String getName() {
            return this.name;
        }
    }
}
