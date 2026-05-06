package com.ngocrong.map;

import com.ngocrong.clan.Clan;
import com.ngocrong.consts.MapName;
import com.ngocrong.map.tzone.Zone;
import com.ngocrong.map.tzone.ZKhiGas;
import com.ngocrong.mob.Mob;
import com.ngocrong.model.MessageTime;
import com.ngocrong.user.Player;
import com.ngocrong.bot.boss.khigas.DrLychee;

import java.util.List;

public class KhiGas extends IMap<ZKhiGas> {

    public static final long POWER_CAN_JOIN = 2_000_000_000L;
    public static final int[] MAPS = {
            MapName.THANH_PHO_SANTA_2,
            MapName.SA_MAC,
            MapName.VUNG_DAT_BANG_GIA,
            MapName.HANH_TINH_BONG_TOI,
            MapName.LAU_DAI_LYCHEE
    };

    private final short level;
    private final Clan clan;
    private final String openMemberName;
    private final long openedAt;
    private boolean bossSpawned;
    private boolean winner;

    public KhiGas(short level, Clan clan, String openMemberName) {
        super(1800);
        this.level = level;
        this.clan = clan;
        this.openMemberName = openMemberName;
        this.openedAt = System.currentTimeMillis();
        for (int mapID : MAPS) {
            TMap map = MapManager.getInstance().getMap(mapID);
            ZKhiGas z = new ZKhiGas(this, map, map.autoIncrease++);
            map.addZone(z);
            zones.add(z);
        }
    }

    public void enter(Player player) {
        player.setX((short) 156);
        player.setY((short) 228);
        enterMap(MapName.THANH_PHO_SANTA_2, player);
    }

    public void enterMap(int mapID, Player player) {
        Zone z = getZone(mapID);
        if (z != null) {
            z.enter(player);
        }
    }

    public short getLevel() {
        return level;
    }

    public boolean canMove(int currentMap, int targetMap) {
        switch (currentMap) {
            case MapName.THANH_PHO_SANTA_2:
                return targetMap == MapName.SA_MAC;
            case MapName.SA_MAC:
                return targetMap == MapName.THANH_PHO_SANTA_2
                        || (targetMap == MapName.VUNG_DAT_BANG_GIA && isMapCleared(currentMap));
            case MapName.VUNG_DAT_BANG_GIA:
                return targetMap == MapName.SA_MAC
                        || (targetMap == MapName.HANH_TINH_BONG_TOI && isMapCleared(currentMap));
            case MapName.HANH_TINH_BONG_TOI:
                return targetMap == MapName.VUNG_DAT_BANG_GIA
                        || (targetMap == MapName.LAU_DAI_LYCHEE && isMapCleared(currentMap));
            case MapName.LAU_DAI_LYCHEE:
                return targetMap == MapName.HANH_TINH_BONG_TOI;
            default:
                return false;
        }
    }

    public boolean isMapCleared(int mapID) {
        Zone z = getZone(mapID);
        if (z == null) {
            return false;
        }
        List<Mob> mobs = z.getListMob();
        for (Mob mob : mobs) {
            if (!mob.isDead()) {
                return false;
            }
        }
        List<Player> bosses = z.getListChar(Zone.TYPE_BOSS);
        for (Player boss : bosses) {
            if (!boss.isDead()) {
                return false;
            }
        }
        return true;
    }

    public void onBossKilled(Player killer) {
        if (winner) {
            return;
        }
        winner = true;
        countDown = 30;
        updateMessageTimeForMember();
        sendServerMessage("Dr Lychee đã bị tiêu diệt, 30 giây nữa bạn sẽ được đưa về làng");
    }

    public void updateMessageTimeForMember() {
        for (Zone zone : zones) {
            List<Player> list = zone.getListChar(Zone.TYPE_HUMAN);
            for (Player player : list) {
                try {
                    player.setTimeForMessageTime(MessageTime.KHI_GAS, (short) countDown);
                } catch (Exception ignored) {
                }
            }
        }
    }

    @Override
    public void update() {
        if (!bossSpawned && isMapCleared(MapName.LAU_DAI_LYCHEE)) {
            Zone zone = getZone(MapName.LAU_DAI_LYCHEE);
            if (zone != null) {
                DrLychee boss = new DrLychee(this);
                boss.setLocation(zone);
                bossSpawned = true;
                sendServerMessage("Dr Lychee đã xuất hiện");
            }
        }
        super.update();
    }

    @Override
    public void close() {
        if (clan != null) {
            clan.khiGas = null;
        }
        for (Zone zone : zones) {
            List<Player> list = zone.getListChar(Zone.TYPE_HUMAN);
            for (Player player : list) {
                try {
                    player.service.sendThongBao("Khí gas hủy diệt đã kết thúc");
                    player.goHome();
                } catch (Exception ignored) {
                }
            }
            zone.map.removeZone(zone);
        }
    }
}
