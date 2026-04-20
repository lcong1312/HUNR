package com.ngocrong.bot.boss.khigas;

import _HunrProvision.boss.Boss;
import com.ngocrong.consts.ItemName;
import com.ngocrong.item.Item;
import com.ngocrong.item.ItemMap;
import com.ngocrong.item.ItemOption;
import com.ngocrong.map.KhiGas;
import com.ngocrong.map.tzone.Zone;
import com.ngocrong.skill.SkillName;
import com.ngocrong.skill.Skills;
import com.ngocrong.user.Info;
import com.ngocrong.user.Player;
import com.ngocrong.util.Utils;
import org.apache.log4j.Logger;

import java.util.ArrayList;

public class DrLychee extends Boss {

    private static final Logger logger = Logger.getLogger(DrLychee.class);

    private final KhiGas khiGas;
    private boolean rewarded;

    public DrLychee(KhiGas khiGas) {
        super();
        this.khiGas = khiGas;
        this.limit = -1;
        this.name = "Dr Lychee";
        this.isShow = false;
        this.rewarded = false;
        this.setTypePK((byte) 5);
        this.point = 5;
        long hp = 10_000_000L * khiGas.getLevel();
        long dame = 100_000L * khiGas.getLevel();
        setInfo(hp, 1_000_000L, dame, 100, 10);
        info.percentMiss = 10;
        this.limitDame = Math.max(1, this.info.originalHP / 20);
    }

    @Override
    public void initSkill() {
        try {
            skills = new ArrayList<>();
            skills.add(Skills.getSkill((byte) SkillName.CHIEU_DAM_DEMON, (byte) 7).clone());
            skills.add(Skills.getSkill((byte) SkillName.CHIEU_ANTOMIC, (byte) 7).clone());
            skills.add(Skills.getSkill((byte) SkillName.TAI_TAO_NANG_LUONG, (byte) 7).clone());
        } catch (CloneNotSupportedException ex) {
            logger.error("init skill err", ex);
        }
    }

    @Override
    public void setInfo(long hp, long mp, long dame, int def, int crit) {
        info.originalHP = hp;
        info.originalMP = mp;
        info.originalDamage = dame;
        info.originalDefense = def;
        info.originalCritical = crit;
        info.setInfo();
        info.recovery(Info.ALL, 100, false);
        info.percentMiss = 10;
    }

    @Override
    public void setLocation(Zone zone) {
        setX((short) 513);
        setY((short) 480);
        zone.enter(this);
    }

    @Override
    public void setDefaultHead() {
        setHead((short) 742);
    }

    @Override
    public void setDefaultBody() {
        setBody((short) 743);
    }

    @Override
    public void setDefaultLeg() {
        setLeg((short) 744);
    }

    @Override
    public void throwItem(Object obj) {
        if (rewarded) {
            return;
        }
        rewarded = true;
        Player killer = obj instanceof Player ? (Player) obj : null;
        if (zone != null && khiGas.getLevel() >= 30) {
            int[] itemIds = {
                    ItemName.CAI_TRANG_HATCHIYACK,
                    ItemName.CAI_TRANG_HATCHIYACK,
                    ItemName.CAI_TRANG_DR_LYCHEE,
                    ItemName.CAI_TRANG_DR_LYCHEE
            };
            int[] xOffsets = {-90, -30, 30, 90};
            for (int i = 0; i < itemIds.length; i++) {
                Item item = new Item(itemIds[i]);
                item.setDefaultOptions();
                item.addItemOption(new ItemOption(77, Utils.nextInt(10, 30)));
                item.addItemOption(new ItemOption(103, Utils.nextInt(10, 30)));
                item.addItemOption(new ItemOption(50, Utils.nextInt(10, 30)));
                item.addItemOption(new ItemOption(14, Utils.nextInt(0, 8)));
                item.addItemOption(new ItemOption(94, Utils.nextInt(0, 8)));
                if (!Utils.isTrue(1, 1000)) {
                    item.addItemOption(new ItemOption(93, Utils.nextInt(1, 7)));
                }
                ItemMap itemMap = new ItemMap(zone.autoIncrease++);
                itemMap.item = item;
                itemMap.playerID = -1;
                int x = getX() + xOffsets[i];
                if (x < 30) {
                    x = 30;
                } else if (x > zone.map.width - 30) {
                    x = zone.map.width - 30;
                }
                itemMap.x = (short) x;
                itemMap.y = zone.map.collisionLand(itemMap.x, getY());
                zone.addItemMap(itemMap);
                zone.service.addItemMap(itemMap);
            }
        }
        khiGas.onBossKilled(killer);
    }

    @Override
    public void sendNotificationWhenAppear(String map) {
    }

    @Override
    public void sendNotificationWhenDead(String name) {
    }
}
