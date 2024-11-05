package org.politpig.townyDuels.data;

import org.bukkit.Location;

public class ArenaData {
        private String name;
        private String world;
        private Location pos1;
        private Location pos2;
        private Location corner1;
        private Location corner2;

        public ArenaData(String name, String world, Location pos1, Location pos2, Location corner1, Location corner2) {
            this.name = name;
            this.world = world;
            this.pos1 = pos1;
            this.pos2 = pos2;
            this.corner1 = corner1;
            this.corner2 = corner2;
        }

        public String getName() {
            return name;
        }

        public String getWorld() {
            return world;
        }

        public Location getPos1() {
            return pos1;
        }

        public Location getPos2() {
            return pos2;
        }

        public Location getCorner1() {
            return corner1;
        }

        public Location getCorner2() {
            return corner2;
        }

        public void setPos1(Location pos1) {
            this.pos1 = pos1;
        }

        public void setPos2(Location pos2) {
            this.pos2 = pos2;
        }

        public boolean hasSpawns() {
            return pos1 != null && pos2 != null;
        }
    }

