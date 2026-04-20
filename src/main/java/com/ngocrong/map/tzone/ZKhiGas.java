package com.ngocrong.map.tzone;

import com.ngocrong.consts.MobName;
import com.ngocrong.map.KhiGas;
import com.ngocrong.map.MapService;
import com.ngocrong.map.TMap;
import com.ngocrong.mob.Mob;
import com.ngocrong.mob.MobCoordinate;
import com.ngocrong.mob.MobFactory;
import com.ngocrong.mob.MobTemplate;
import com.ngocrong.mob.MobType;
import com.ngocrong.model.MessageTime;
import com.ngocrong.model.Npc;
import com.ngocrong.user.Player;

import java.util.ArrayList;
import java.util.Arrays;

public class ZKhiGas extends Zone {

    private final KhiGas khiGas;

    public ZKhiGas(KhiGas khiGas, TMap map, int zoneId) {
        super(map, zoneId);
        this.khiGas = khiGas;
        initial2();
    }

    @Override
    public void initial() {
    }

    public void initial2() {
        long now = System.currentTimeMillis();
        service = new MapService(this);
        Arrays.fill(lastUpdates, now);
        this.numPlayer = 0;
        this.maxPlayer = 15;
        this.isHadSuperMob = false;
        this.mobs = new ArrayList<>();
        this.npcs = new ArrayList<>();
        this.players = new ArrayList<>();
        this.items = new ArrayList<>();
        this.satellites = new ArrayList<>();
        this.waitForRespawn = new ArrayList<>();
        for (MobCoordinate mobCoordinate : this.map.mobs) {
            Mob mob = MobFactory.getMob(MobType.MOB);
            mob.setMobId(allCountMob--);
            byte templateID = mobCoordinate.getTemplateID();
            MobTemplate template = Mob.getMobTemplate(templateID);
            mob.setX(mobCoordinate.getX());
            mob.setY(mobCoordinate.getY());
            mob.setTemplateId(templateID);
            mob.setLevel(template.level);
            long hpDefault = template.hp * khiGas.getLevel();
            if (templateID == MobName.CO_MAY_HUY_DIET) {
                hpDefault *= 2;
            }
            if (mob.getLevel() == 10) {
                hpDefault *= 10;
            }
            mob.setHpDefault(hpDefault);
            mob.setDefault();
            mob.setZone(this);
            addMob(mob);
        }
        for (Npc npc : this.map.npcs) {
            Npc n = npc.clone();
            addNpc(n);
        }
    }

    @Override
    public void respawn(ArrayList<Mob> mobs) {
    }

    @Override
    public void enter(Player player) {
        super.enter(player);
        if (player.isHuman()) {
            MessageTime ms = new MessageTime(MessageTime.KHI_GAS, "Khí gas hủy diệt", (short) khiGas.getCountDown());
            player.addMessageTime(ms);
        }
    }

    @Override
    public void leave(Player player) {
        super.leave(player);
        if (player.isHuman()) {
            player.setTimeForMessageTime(MessageTime.KHI_GAS, (short) 0);
        }
    }
}
