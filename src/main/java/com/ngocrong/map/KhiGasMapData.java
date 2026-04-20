package com.ngocrong.map;

import com.ngocrong.consts.MapName;
import com.ngocrong.mob.MobCoordinate;
import com.ngocrong.model.Waypoint;

public final class KhiGasMapData {

    private KhiGasMapData() {
    }

    public static boolean applyFallback(TMap map) {
        if (!needsFallback(map)) {
            return false;
        }
        switch (map.mapID) {
            case MapName.SA_MAC:
                apply(map, 0, 3, 2, 5, new Waypoint[]{
                        waypoint("Thành phố Santa", 0, 768, 24, 792, MapName.THANH_PHO_SANTA_2, 1436, 336),
                        waypoint("Vùng đất băng giá", 1512, 768, 1536, 792, MapName.VUNG_DAT_BANG_GIA, 1545, 168)
                }, new int[][]{
                        {76, 1308, 432, 80000},
                        {73, 348, 504, 50000},
                        {73, 492, 792, 50000},
                        {73, 132, 792, 50000},
                        {73, 996, 792, 50000},
                        {73, 1428, 792, 50000},
                        {75, 540, 408, 60000},
                        {75, 348, 432, 60000},
                        {75, 180, 672, 60000},
                        {75, 876, 240, 60000},
                        {75, 996, 672, 60000},
                        {75, 1212, 744, 60000},
                        {75, 1260, 384, 60000},
                        {75, 1380, 384, 60000},
                        {76, 708, 240, 80000},
                        {73, 660, 240, 50000},
                        {73, 852, 360, 50000}
                });
                return true;
            case MapName.LAU_DAI_LYCHEE:
                apply(map, 2, 15, 8, 5, new Waypoint[]{
                        waypoint("Hành tinh bóng tối", 0, 456, 24, 480, MapName.HANH_TINH_BONG_TOI, 1609, 288)
                }, new int[][]{
                        {73, 708, 480, 50000},
                        {73, 444, 480, 50000},
                        {73, 180, 480, 50000},
                        {74, 300, 480, 55000},
                        {74, 588, 480, 55000},
                        {74, 852, 480, 55000},
                        {75, 228, 384, 60000},
                        {75, 396, 384, 60000},
                        {75, 564, 384, 60000},
                        {75, 732, 384, 60000},
                        {75, 852, 384, 60000}
                });
                return true;
            case MapName.THANH_PHO_SANTA_2:
                apply(map, 2, 11, 0, 5, new Waypoint[]{
                        waypoint("Sa mạc", 1512, 312, 1536, 336, MapName.SA_MAC, 94, 792)
                }, new int[][]{
                        {74, 1380, 336, 55000},
                        {74, 1188, 336, 55000},
                        {74, 996, 336, 55000},
                        {74, 828, 336, 55000},
                        {74, 684, 336, 55000},
                        {74, 564, 336, 55000},
                        {74, 420, 336, 55000},
                        {74, 228, 336, 55000},
                        {76, 1284, 336, 80000},
                        {75, 1428, 216, 60000},
                        {75, 1212, 216, 60000},
                        {75, 1020, 216, 60000},
                        {75, 804, 216, 60000},
                        {75, 612, 216, 60000},
                        {75, 420, 216, 60000}
                });
                return true;
            case MapName.HANH_TINH_BONG_TOI:
                apply(map, 2, 12, 11, 5, new Waypoint[]{
                        waypoint("Lâu đài Lychee", 1656, 264, 1680, 288, MapName.LAU_DAI_LYCHEE, 94, 480),
                        waypoint("Vùng đất băng giá", 0, 264, 24, 288, MapName.VUNG_DAT_BANG_GIA, 104, 168)
                }, new int[][]{
                        {76, 900, 360, 80000},
                        {73, 588, 360, 50000},
                        {73, 1164, 360, 50000},
                        {74, 1068, 360, 55000},
                        {74, 756, 360, 55000},
                        {75, 804, 264, 60000},
                        {75, 972, 264, 60000},
                        {75, 660, 264, 60000},
                        {75, 1116, 264, 60000}
                });
                return true;
            case MapName.VUNG_DAT_BANG_GIA:
                apply(map, 2, 25, 8, 5, new Waypoint[]{
                        waypoint("Hành tinh bóng tối", 0, 144, 24, 168, MapName.HANH_TINH_BONG_TOI, 112, 288),
                        waypoint("Sa mạc", 1608, 144, 1632, 168, MapName.SA_MAC, 1462, 792)
                }, new int[][]{
                        {76, 852, 432, 80000},
                        {73, 1260, 432, 50000},
                        {73, 1500, 432, 50000},
                        {73, 1404, 168, 50000},
                        {73, 1524, 168, 50000},
                        {73, 1044, 432, 50000},
                        {73, 924, 432, 50000},
                        {73, 156, 168, 50000},
                        {73, 396, 168, 50000},
                        {74, 276, 168, 55000},
                        {74, 180, 432, 55000},
                        {74, 324, 432, 55000},
                        {74, 492, 432, 55000},
                        {74, 804, 432, 55000},
                        {74, 1452, 168, 55000},
                        {74, 1404, 432, 55000},
                        {75, 1452, 96, 60000},
                        {75, 1332, 360, 60000},
                        {75, 1500, 360, 60000},
                        {75, 1044, 360, 60000},
                        {75, 828, 360, 60000},
                        {75, 1116, 72, 60000},
                        {75, 732, 72, 60000},
                        {75, 204, 96, 60000},
                        {75, 348, 96, 60000},
                        {75, 492, 96, 60000},
                        {75, 420, 360, 60000},
                        {75, 228, 360, 60000},
                        {75, 564, 360, 60000},
                        {76, 300, 168, 80000}
                });
                return true;
            default:
                return false;
        }
    }

    private static boolean needsFallback(TMap map) {
        return map != null
                && map.isKhiGas()
                && (map.tileID <= 0
                || map.waypoints == null
                || map.waypoints.length == 0
                || map.mobs == null
                || map.mobs.length == 0);
    }

    private static void apply(TMap map, int planet, int tileId, int bgId, int typeMap, Waypoint[] waypoints, int[][] mobData) {
        map.planet = planet;
        map.tileID = tileId;
        map.bgID = bgId;
        map.typeMap = typeMap;
        map.bgType = 0;
        map.waypoints = waypoints;
        map.mobs = createMobs(mobData);
    }

    private static Waypoint waypoint(String name, int minX, int minY, int maxX, int maxY, int next, int x, int y) {
        Waypoint waypoint = new Waypoint();
        waypoint.name = name;
        waypoint.minX = (short) minX;
        waypoint.minY = (short) minY;
        waypoint.maxX = (short) maxX;
        waypoint.maxY = (short) maxY;
        waypoint.next = next;
        waypoint.x = (short) x;
        waypoint.y = (short) y;
        waypoint.isEnter = false;
        waypoint.isOffline = false;
        return waypoint;
    }

    private static MobCoordinate[] createMobs(int[][] mobData) {
        MobCoordinate[] mobs = new MobCoordinate[mobData.length];
        for (int i = 0; i < mobData.length; i++) {
            int[] data = mobData[i];
            MobCoordinate mob = new MobCoordinate();
            mob.setTemplateID((byte) data[0]);
            mob.setX((short) data[1]);
            mob.setY((short) data[2]);
            mob.setHpMax(data[3]);
            mobs[i] = mob;
        }
        return mobs;
    }
}
