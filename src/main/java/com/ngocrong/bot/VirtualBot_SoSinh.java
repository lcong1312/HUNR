package com.ngocrong.bot;

import _HunrProvision.ConfigStudio;
import com.google.gson.Gson;
import com.ngocrong.bot.boss.Cell.XenBoHung;
import com.ngocrong.bot.boss.android.Android13;
import com.ngocrong.bot.boss.android.Android14;
import com.ngocrong.bot.boss.android.Android15;
import com.ngocrong.bot.boss.android.Android19;
import com.ngocrong.bot.boss.android.Android20;
import com.ngocrong.bot.boss.android.KingKong;
import com.ngocrong.bot.boss.android.Pic;
import com.ngocrong.bot.boss.android.Poc;
import com.ngocrong.bot.boss.fide.Fide;
import com.ngocrong.bot.boss.fide.KuKu;
import com.ngocrong.bot.boss.fide.MapDauDinh;
import com.ngocrong.bot.boss.fide.RamBo;
import com.ngocrong.bot.boss.fide.So1;
import com.ngocrong.bot.boss.fide.So2;
import com.ngocrong.bot.boss.fide.So3;
import com.ngocrong.bot.boss.fide.So4;
import com.ngocrong.bot.boss.fide.TieuDoiTruong;
import com.ngocrong.bot.boss.karin.Karin;
import com.ngocrong.bot.boss.karin.TaoPaiPai;
import com.ngocrong.bot.boss.karin.Yajiro;
import com.ngocrong.clan.Clan;
import com.ngocrong.clan.ClanMember;
import com.ngocrong.combine.Combine;
import com.ngocrong.combine.CombineFactory;
import com.ngocrong.combine.CombineType;
import com.ngocrong.combine.PhaLeHoa;
import com.ngocrong.consts.ItemName;
import com.ngocrong.consts.MapName;
import com.ngocrong.consts.MobName;
import com.ngocrong.consts.NpcName;
import com.ngocrong.data.ClanData;
import com.ngocrong.data.ClanMemberData;
import com.ngocrong.item.Item;
import com.ngocrong.item.ItemMap;
import com.ngocrong.item.ItemOption;
import com.ngocrong.item.ItemTemplate;
import com.ngocrong.map.MapManager;
import com.ngocrong.map.TMap;
import com.ngocrong.map.tzone.Zone;
import com.ngocrong.mob.Mob;
import com.ngocrong.model.Npc;
import com.ngocrong.shop.Shop;
import com.ngocrong.task.Task;
import com.ngocrong.user.Info;
import com.ngocrong.user.Player;
import com.ngocrong.util.Utils;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class VirtualBot_SoSinh extends VirtualBot {

    public static int BotSS;
    private static final int BOT_INITIAL_TASK_ID = 1;
    private static final Gson BOT_STATE_GSON = new Gson();
    private static final long BOT_PERSIST_INTERVAL = 60_000L;

    private static final Object BOT_CLAN_LOCK = new Object();
    private static Clan sharedBotClan;
    private static int nextSharedClanId = -10_000;

    private long lastRouteUpdate;
    private long lastTaskUpdate;
    private long lastLootUpdate;
    private long lastEquipUpdate;
    private long lastShopUpdate;
    private long lastCombineUpdate;
    private long lastCosmeticUpdate;
    private long lastClanUpdate;
    private long lastPersistenceUpdate;

    private int persistedMapId = -1;
    private int persistedZoneId = -1;
    private short persistedX;
    private short persistedY;

    public VirtualBot_SoSinh(String name) {
        super(name);
        this.gender = (byte) Utils.nextInt(3);
        this.info.power = Utils.nextInt(1_500_000, 1_500_005);
        this.setInfo(Utils.nextInt(3000, 5000), Long.MAX_VALUE, Utils.nextInt(50, 70), 10000, 1);
        this.gold = 20_000_000_000L;
        this.diamondLock = 500_000;
        this.numberCellBag = 100;
        this.numberCellBox = 30;
        this.itemBag = new Item[this.numberCellBag];
        this.itemBox = new Item[this.numberCellBox];
        this.itemBody = new Item[15];
        setHeadByGender();
        initSkill();
        sortSkill();
        updateTask(BOT_INITIAL_TASK_ID);
        timeSpawn = System.currentTimeMillis();
    }

    private void setHeadByGender() {
        short[][] hair = new short[][]{
                {64, 30, 31},
                {9, 29, 32},
                {6, 27, 28}
        };
        short head = hair[this.gender][Utils.nextInt(hair[this.gender].length)];
        setHeadDefault(head);
        setHead(head);
        ensureStarterBodyItemsLikeNewCharacter();
        setDefaultBody();
        setDefaultLeg();
        updateSkin();
    }

    private void ensureStarterBodyItemsLikeNewCharacter() {
        if (itemBody == null || itemBody.length < 2) {
            return;
        }
        if (itemBody[0] == null) {
            itemBody[0] = createStarterBodyItem(getStarterBodyItemId(), 0);
        }
        if (itemBody[1] == null) {
            itemBody[1] = createStarterBodyItem(getStarterLegItemId(), 1);
        }
    }

    private int getStarterBodyItemId() {
        if (this.gender == 0) {
            return 0;
        }
        if (this.gender == 1) {
            return 1;
        }
        return 2;
    }

    private int getStarterLegItemId() {
        if (this.gender == 0) {
            return 6;
        }
        if (this.gender == 1) {
            return 7;
        }
        return 8;
    }

    private Item createStarterBodyItem(int itemId, int slot) {
        Item item;
        try {
            item = new Item(itemId);
        } catch (Exception e) {
            return null;
        }
        if (item.template == null) {
            return null;
        }
        item.indexUI = slot;
        item.quantity = 1;
        item.setDefaultOptions();
        return item;
    }

    @Override
    public void update() {
        super.update();
        if (info == null) {
            return;
        }
        this.info.mp = this.info.mpFull = Long.MAX_VALUE;
        long now = System.currentTimeMillis();

        if (now - lastTaskUpdate >= 1000) {
            lastTaskUpdate = now;
            ensureTaskInitialized();
            autoProgressTask();
        }

        int targetMapId = resolveTargetMap();
        if (now - lastRouteUpdate >= 1500) {
            lastRouteUpdate = now;
            if (moveToTargetMap(targetMapId)) {
                return;
            }
        }
        if (zone == null) {
            return;
        }

        if (now - lastPersistenceUpdate >= BOT_PERSIST_INTERVAL) {
            lastPersistenceUpdate = now;
            VirtualBotSoSinhPersistence.saveBot(this);
        }

        if (now - lastClanUpdate >= 5000) {
            lastClanUpdate = now;
            ensureClanMembership();
        }

        if (now - lastShopUpdate >= 5000) {
            lastShopUpdate = now;
            ensureBotResources();
            buyShopEquipmentForPower();
        }

        if (now - lastCosmeticUpdate >= 15000) {
            lastCosmeticUpdate = now;
            ensureMilestoneSantaCosmetics();
        }

        if (now - lastEquipUpdate >= 2000) {
            lastEquipUpdate = now;
            equipBestItemsFromBag();
        }

        if (now - lastCombineUpdate >= 12000) {
            lastCombineUpdate = now;
            runSimpleCombineRules();
        }

        if (now - lastLootUpdate >= 1000) {
            lastLootUpdate = now;
            pickNearbyUsefulItems();
        }

        if (talkToQuestNpcIfNeeded()) {
            return;
        }

        attackQuestTargetOrMob();
    }

    private void ensureTaskInitialized() {
        if (taskMain == null) {
            updateTask(BOT_INITIAL_TASK_ID);
        }
    }

    private void autoProgressTask() {
        if (taskMain == null || zone == null || zone.map == null) {
            return;
        }
        applyMapTriggerTasks();
        applyPowerGateTasks();
        applyBotSpecificTaskShortcuts();
    }

    private void applyMapTriggerTasks() {
        if (taskMain == null || zone == null || zone.map == null) {
            return;
        }
        int mapId = zone.map.mapID;
        Task task = taskMain;
        if (task.id == 0 && task.index == 1 && getTaskMap(task, 1) == mapId) {
            taskNext();
            return;
        }
        if (mapId == 47 && task.id == 8 && task.index == 3) {
            updateTask(9);
            return;
        }
        if (mapId == 46 && task.id == 9 && task.index == 2) {
            taskNext();
            return;
        }
        if (task.id == 11 && task.index == 0) {
            int masterMap = (new int[]{5, 13, 20})[gender];
            if (mapId == masterMap) {
                taskNext();
                return;
            }
        }
        if (mapId == 93 && task.id == 22 && task.index == 1) {
            taskNext();
            return;
        }
        if (mapId == 97 && task.id == 24 && task.index == 0) {
            taskNext();
            return;
        }
        if (mapId == 104 && task.id == 23 && task.index == 0) {
            taskNext();
            return;
        }
        if (mapId == 100 && task.id == 25 && task.index == 0) {
            taskNext();
            return;
        }
        if (mapId == 103 && task.id == 26 && task.index == 1) {
            taskNext();
            return;
        }
        if (mapId == MapName.CUA_AI_1 && task.id == 27 && task.index == 0) {
            taskNext();
        }
    }

    private void applyPowerGateTasks() {
        if (taskMain == null) {
            return;
        }
        if (taskMain.id == 7 && taskMain.index == 0 && info.power >= 16000) {
            taskNext();
            return;
        }
        if (taskMain.id == 8 && taskMain.index == 0 && info.power >= 40000) {
            taskNext();
            return;
        }
        if (taskMain.id == 14 && taskMain.index == 0 && info.power >= 200000) {
            taskNext();
            return;
        }
        if (taskMain.id == 15 && taskMain.index == 0 && info.power >= 500000) {
            taskNext();
            return;
        }
        if (taskMain.id == 20 && taskMain.index == 0 && info.power >= 600000000) {
            taskNext();
            return;
        }
        if (taskMain.id == 21 && taskMain.index == 0 && info.power >= 2000000000L) {
            taskNext();
            return;
        }
        if (taskMain.id == 12 && taskMain.index == 0 && clan != null) {
            taskNext();
        }
    }

    private void applyBotSpecificTaskShortcuts() {
        if (taskMain == null || zone == null || zone.map == null) {
            return;
        }
        int mapId = zone.map.mapID;

        if (taskMain.id == 0 && taskMain.index == 0) {
            taskNext();
            return;
        }
        if (taskMain.id == 0 && (taskMain.index == 3 || taskMain.index == 4) && zone.map.isHome()) {
            taskNext();
            return;
        }
        if (taskMain.id == 3 && taskMain.index == 0) {
            taskNext();
            return;
        }
        if (taskMain.id == 10 && taskMain.index == 0 && mapId == 46) {
            taskNext();
            return;
        }
        if (taskMain.id == 10 && taskMain.index == 1 && mapId == 47) {
            taskNext();
        }
    }

    private int resolveTargetMap() {
        if (taskMain == null) {
            return getFallbackTrainMap();
        }
        int botSpecificMap = resolveBotSpecificTaskMap();
        if (botSpecificMap != -1) {
            return botSpecificMap;
        }
        int npcId = getCurrentTaskNpcId();
        if (npcId != -1 && zone != null && zone.findNpcByID(npcId) != null) {
            return zone.map.mapID;
        }
        int taskMap = getTaskMap(taskMain, taskMain.index);
        if (taskMap != -1) {
            return taskMap;
        }
        int specialMap = resolveSpecialTaskMap();
        if (specialMap != -1) {
            return specialMap;
        }
        return getFallbackTrainMap();
    }

    private int resolveBotSpecificTaskMap() {
        if (taskMain == null) {
            return -1;
        }
        if (taskMain.id == 1 && taskMain.index == 0) {
            return getVillageMapByGender();
        }
        return -1;
    }

    private int getVillageMapByGender() {
        switch (this.gender) {
            case 0:
                return MapName.LANG_ARU;
            case 1:
                return MapName.LANG_MORI;
            default:
                return MapName.LANG_KAKAROT;
        }
    }

    private int resolveSpecialTaskMap() {
        if (taskMain == null) {
            return -1;
        }
        switch (taskMain.id) {
            case 10:
                if (taskMain.index == 0) {
                    return 46;
                }
                if (taskMain.index == 1 || taskMain.index == 2) {
                    return 47;
                }
                break;
            case 19:
                if (taskMain.index == 0) {
                    return findMapWithQuestBoss(new int[]{68, 69, 70, 71, 72}, 68);
                }
                if (taskMain.index == 1) {
                    return findMapWithQuestBoss(new int[]{63, 64, 65, 66, 67}, 64);
                }
                if (taskMain.index == 2) {
                    return findMapWithQuestBoss(new int[]{73, 74, 75, 76, 77}, 73);
                }
                if (taskMain.index == 3) {
                    return 19;
                }
                break;
            case 20:
                if (taskMain.index >= 1 && taskMain.index <= 5) {
                    return findMapWithQuestBoss(new int[]{79, 81, 82, 83}, 79);
                }
                if (taskMain.index == 6) {
                    return 19;
                }
                break;
            case 21:
                if (taskMain.index >= 1 && taskMain.index <= 3) {
                    return findMapWithQuestBoss(new int[]{80}, 80);
                }
                if (taskMain.index == 4) {
                    return 19;
                }
                break;
            case 22:
                if (taskMain.index >= 2 && taskMain.index <= 4) {
                    return 93;
                }
                break;
            case 23:
                if (taskMain.index >= 1 && taskMain.index <= 3) {
                    return 104;
                }
                break;
            case 24:
                if (taskMain.index >= 1 && taskMain.index <= 4) {
                    return 97;
                }
                break;
            case 25:
                if (taskMain.index >= 1 && taskMain.index <= 4) {
                    return 100;
                }
                break;
            case 26:
                if (taskMain.index >= 2 && taskMain.index <= 3) {
                    return 103;
                }
                break;
            default:
                break;
        }
        return -1;
    }

    private int findMapWithQuestBoss(int[] mapIds, int fallback) {
        for (int mapId : mapIds) {
            TMap map = MapManager.getInstance().getMap(mapId);
            if (map == null) {
                continue;
            }
            Zone zoneWithBoss = findZoneWithQuestBoss(map);
            if (zoneWithBoss != null) {
                return mapId;
            }
        }
        return fallback;
    }

    private boolean moveToTargetMap(int targetMapId) {
        TMap map = MapManager.getInstance().getMap(targetMapId);
        if (map == null) {
            return false;
        }
        Zone targetZone = findPreferredZone(map);
        if (targetZone == null) {
            return false;
        }
        if (zone != null && zone.map != null && zone.map.mapID == targetMapId && zone.zoneID == targetZone.zoneID) {
            return false;
        }
        if (zone != null) {
            zone.leave(this);
        }
        setX((short) (targetZone.map.width / 2));
        setY(targetZone.map.collisionLand(getX(), (short) 24));
        targetZone.enter(this);
        return true;
    }

    private Zone findPreferredZone(TMap map) {
        int mobTemplateId = getPreferredMobTemplateId();

        Zone keepCurrent = getCurrentZoneIfSuitable(map, mobTemplateId);
        if (keepCurrent != null) {
            return keepCurrent;
        }

        Zone bossZone = findZoneWithQuestBoss(map);
        if (bossZone != null) {
            return bossZone;
        }

        Zone anyBossZone = findZoneWithAnyBoss(map);
        if (anyBossZone != null) {
            return anyBossZone;
        }

        if (mobTemplateId != -1) {
            Zone mobZone = findZoneWithMob(map, mobTemplateId);
            if (mobZone != null) {
                return mobZone;
            }
        }

        Zone leastCrowded = findLeastCrowdedZone(map);
        if (leastCrowded != null) {
            return leastCrowded;
        }

        return map.getMinPlayerZone();
    }

    private Zone getCurrentZoneIfSuitable(TMap map, int mobTemplateId) {
        if (zone == null || zone.map == null || map == null || zone.map.mapID != map.mapID) {
            return null;
        }
        if (isZoneCrowded(zone)) {
            return null;
        }
        if (findQuestBossTarget(zone) != null) {
            return zone;
        }
        if (findAnyBossTarget(zone) != null) {
            return zone;
        }
        if (mobTemplateId == -1 || zoneHasAliveMob(zone, mobTemplateId)) {
            return zone;
        }
        return null;
    }

    private Zone findZoneWithQuestBoss(TMap map) {
        if (map == null) {
            return null;
        }
        Zone best = null;
        int bestLoad = Integer.MAX_VALUE;
        synchronized (map.zones) {
            for (Zone z : map.zones) {
                if (z == null) {
                    continue;
                }
                Player bossTarget = findQuestBossTarget(z);
                if (bossTarget == null) {
                    continue;
                }
                int zoneLoad = getZoneLoad(z);
                if (isBetterZoneCandidate(z, zoneLoad, best, bestLoad)) {
                    best = z;
                    bestLoad = zoneLoad;
                }
            }
        }
        return best;
    }

    private Zone findZoneWithAnyBoss(TMap map) {
        if (map == null) {
            return null;
        }
        Zone best = null;
        int bestLoad = Integer.MAX_VALUE;
        synchronized (map.zones) {
            for (Zone z : map.zones) {
                if (z == null) {
                    continue;
                }
                Player bossTarget = findAnyBossTarget(z);
                if (bossTarget == null) {
                    continue;
                }
                int zoneLoad = getZoneLoad(z);
                if (isBetterZoneCandidate(z, zoneLoad, best, bestLoad)) {
                    best = z;
                    bestLoad = zoneLoad;
                }
            }
        }
        return best;
    }

    private Zone findZoneWithMob(TMap map, int mobTemplateId) {
        if (map == null || mobTemplateId == -1) {
            return null;
        }
        Zone best = null;
        int bestLoad = Integer.MAX_VALUE;
        synchronized (map.zones) {
            for (Zone z : map.zones) {
                if (z == null) {
                    continue;
                }
                if (!zoneHasAliveMob(z, mobTemplateId)) {
                    continue;
                }
                int zoneLoad = getZoneLoad(z);
                if (isBetterZoneCandidate(z, zoneLoad, best, bestLoad)) {
                    best = z;
                    bestLoad = zoneLoad;
                }
            }
        }
        return best;
    }

    private Zone findLeastCrowdedZone(TMap map) {
        if (map == null) {
            return null;
        }
        Zone best = null;
        int bestLoad = Integer.MAX_VALUE;
        synchronized (map.zones) {
            for (Zone z : map.zones) {
                if (z == null) {
                    continue;
                }
                int zoneLoad = getZoneLoad(z);
                if (isBetterZoneCandidate(z, zoneLoad, best, bestLoad)) {
                    best = z;
                    bestLoad = zoneLoad;
                }
            }
        }
        return best;
    }

    private boolean zoneHasAliveMob(Zone z, int mobTemplateId) {
        if (z == null || mobTemplateId == -1) {
            return false;
        }
        for (Mob mob : z.getListMob()) {
            if (mob != null && mob.templateId == mobTemplateId && mob.hp > 0 && mob.status != 0 && mob.status != 1) {
                return true;
            }
        }
        return false;
    }

    private int getZoneLoad(Zone z) {
        if (z == null) {
            return Integer.MAX_VALUE;
        }
        int load = z.getNumPlayer();
        if (load < 0 && z.getPlayers() != null) {
            load = z.getPlayers().size();
        }
        return Math.max(load, 0);
    }

    private boolean isZoneCrowded(Zone z) {
        return z != null && z.getPts() == Zone.PTS_RED;
    }

    private boolean isBetterZoneCandidate(Zone candidate, int candidateLoad, Zone currentBest, int bestLoad) {
        if (candidate == null) {
            return false;
        }
        if (currentBest == null) {
            return true;
        }
        boolean candidateCrowded = isZoneCrowded(candidate);
        boolean bestCrowded = isZoneCrowded(currentBest);
        if (candidateCrowded != bestCrowded) {
            return !candidateCrowded;
        }
        if (candidateLoad != bestLoad) {
            return candidateLoad < bestLoad;
        }
        return candidate.zoneID < currentBest.zoneID;
    }

    private int getFallbackTrainMap() {
        long power = info != null ? info.power : 0;
        if (power < 5_000_000L) {
            return 27;
        }
        if (power < 20_000_000L) {
            return 31;
        }
        if (power < 100_000_000L) {
            return 35;
        }
        if (power < 300_000_000L) {
            return 30;
        }
        if (power < 1_000_000_000L) {
            return 34;
        }
        if (power < 2_000_000_000L) {
            return 38;
        }
        return this.gender == 0 ? 6 : this.gender == 1 ? 10 : 19;
    }

    private boolean talkToQuestNpcIfNeeded() {
        int npcId = getCurrentTaskNpcId();
        if (npcId == -1 || zone == null) {
            return false;
        }
        Npc npc = zone.findNpcByID(npcId);
        if (npc == null) {
            return false;
        }
        if (Math.abs(getX() - npc.x) > 60 || Math.abs(getY() - npc.y) > 80) {
            moveTo(npc.x, npc.y);
            return true;
        }
        return taskTalk(npc);
    }

    private void ensureClanMembership() {
        if (taskMain == null || taskMain.id < 11 || clan != null) {
            return;
        }
        synchronized (BOT_CLAN_LOCK) {
            if (sharedBotClan == null || sharedBotClan.getNumberMember() == 0) {
                sharedBotClan = createSharedBotClan();
            }
            if (sharedBotClan.getMember(this.id) == null) {
                byte role = sharedBotClan.getNumberMember() == 0 ? (byte) 0 : (byte) 2;
                ClanMemberData memberData = new ClanMemberData(sharedBotClan.id, this, role);
                memberData.id = (long) this.id;
                ClanMember member = new ClanMember(memberData);
                sharedBotClan.addMember(member);
                if (sharedBotClan.getNumberMember() == 1) {
                    sharedBotClan.leaderID = this.id;
                    sharedBotClan.leaderName = this.name;
                }
            }
            this.clan = sharedBotClan;
            this.clanID = sharedBotClan.id;
        }
    }

    private Clan createSharedBotClan() {
        ClanData clanData = new ClanData("BOT_AUTO_" + Math.abs(nextSharedClanId), (byte) 0, this);
        clanData.id = nextSharedClanId--;
        clanData.maxMember = 100;
        clanData.slogan = "Bot auto progression";
        return new Clan(clanData);
    }

    private void removeFromSharedClan() {
        synchronized (BOT_CLAN_LOCK) {
            Clan clanRef = sharedBotClan;
            if (clanRef == null) {
                return;
            }
            boolean emptyAfterRemove = false;
            clanRef.lockMember.writeLock().lock();
            try {
                clanRef.members.removeIf(member -> member != null && member.playerID == this.id);
                if (clanRef.members.isEmpty()) {
                    emptyAfterRemove = true;
                } else if (clanRef.leaderID == this.id) {
                    ClanMember nextLeader = clanRef.members.get(0);
                    nextLeader.role = 0;
                    clanRef.leaderID = nextLeader.playerID;
                    clanRef.leaderName = nextLeader.name;
                }
            } finally {
                clanRef.lockMember.writeLock().unlock();
            }
            if (emptyAfterRemove && sharedBotClan == clanRef) {
                sharedBotClan = null;
            }
        }
    }

    private void ensureBotResources() {
        if (this.gold < 2_000_000_000L) {
            this.gold = 20_000_000_000L;
        }
    }

    private void buyShopEquipmentForPower() {
        Shop equipmentShop = Shop.getShop(-1);
        if (equipmentShop == null) {
            return;
        }
        this.shop = equipmentShop;
        for (int itemType = 0; itemType <= 4; itemType++) {
            ItemTemplate bestTemplate = getBestEquipmentTemplate(equipmentShop, itemType);
            if (bestTemplate == null) {
                continue;
            }
            Item candidate = createItemFromTemplate(bestTemplate);
            if (candidate == null) {
                continue;
            }
            long currentScore = getBestCurrentScoreForType(itemType);
            long candidateScore = getItemScore(candidate);
            if (candidateScore > currentScore && !hasItemInBagOrBody(bestTemplate.id)) {
                buyItem(0, bestTemplate.id, 1);
            }
        }
        buyUpgradeStonesIfNeeded(equipmentShop);
    }

    private ItemTemplate getBestEquipmentTemplate(Shop equipmentShop, int itemType) {
        ItemTemplate best = null;
        for (ItemTemplate template : equipmentShop.getListItem(this)) {
            if (template == null || template.type != itemType || template.require > info.power) {
                continue;
            }
            if (best == null) {
                best = template;
                continue;
            }
            if (template.level > best.level
                    || (template.level == best.level && template.require > best.require)
                    || (template.level == best.level && template.require == best.require && template.id > best.id)) {
                best = template;
            }
        }
        return best;
    }

    private void buyUpgradeStonesIfNeeded(Shop equipmentShop) {
        this.shop = equipmentShop;
        ensureStackItem(ItemName.DA_LUC_BAO, 30);
        ensureStackItem(ItemName.DA_SAPHIA, 30);
        ensureStackItem(ItemName.DA_RUBY, 30);
        ensureStackItem(ItemName.DA_TITAN, 30);
        ensureStackItem(ItemName.DA_THACH_ANH_TIM, 30);
    }

    private void ensureStackItem(int itemId, int targetQuantity) {
        int current = getItemQuantityInBag(itemId);
        if (current >= targetQuantity) {
            return;
        }
        buyItem(0, itemId, targetQuantity - current);
    }

    private void ensureMilestoneSantaCosmetics() {
        Shop santaShop = Shop.getShop(NpcName.BUNMA_TET);
        if (santaShop == null) {
            return;
        }
        if (info.power >= 1_500_000L) {
            ensureRandomSantaCosmetic(santaShop, Item.TYPE_HAIR, 5);
        }
        if (info.power >= 20_000_000L) {
            ensureRandomSantaCosmetic(santaShop, Item.TYPE_PET_BAY, 11);
        }
        if (info.power >= 100_000_000L) {
            ensureRandomSantaCosmetic(santaShop, Item.TYPE_DANH_HIEU, 12);
        }
    }

    private void ensureRandomSantaCosmetic(Shop santaShop, int desiredType, int bodySlot) {
        Item equipped = bodySlot >= 0 && bodySlot < itemBody.length ? itemBody[bodySlot] : null;
        if (equipped != null
                && equipped.template != null
                && isSantaCosmeticType(equipped.template.type, desiredType)
                && isSantaItemId(equipped.id, desiredType)) {
            return;
        }
        List<ItemTemplate> candidates = new ArrayList<>();
        for (ItemTemplate template : santaShop.getListItem(this)) {
            if (template == null) {
                continue;
            }
            if (isSantaCosmeticType(template.type, desiredType)) {
                candidates.add(template);
            }
        }
        if (candidates.isEmpty()) {
            return;
        }
        ItemTemplate template = candidates.get(Utils.nextInt(candidates.size()));
        if (hasItemInBagOrBody(template.id)) {
            equipMatchingBagItem(template.id);
            return;
        }
        Item cosmetic = createItemFromTemplate(template);
        if (cosmetic != null && addItem(cosmetic)) {
            equipMatchingBagItem(template.id);
        }
    }

    private boolean isSantaCosmeticType(int actualType, int desiredType) {
        if (desiredType == Item.TYPE_PET_BAY) {
            return actualType == Item.TYPE_PET_BAY
                    || actualType == Item.TYPE_PET_BAY_BAC_1
                    || actualType == Item.TYPE_PET_BAY_BAC_2;
        }
        return actualType == desiredType;
    }

    private void equipBestItemsFromBag() {
        for (int bodyType = 0; bodyType <= 4; bodyType++) {
            int bestBagIndex = findBestBagItemIndexForType(bodyType);
            if (bestBagIndex == -1) {
                continue;
            }
            Item bagItem = itemBag[bestBagIndex];
            Item bodyItem = itemBody[bodyType];
            if (bodyItem == null || getItemScore(bagItem) > getItemScore(bodyItem)) {
                itemBagToBody(bestBagIndex);
            }
        }
        equipBagCosmeticSlot(5, Item.TYPE_HAIR);
        equipBagCosmeticSlot(11, Item.TYPE_PET_BAY);
        equipBagCosmeticSlot(12, Item.TYPE_DANH_HIEU);
    }

    private void equipBagCosmeticSlot(int bodySlot, int desiredType) {
        int bestBagIndex = -1;
        long bestScore = Long.MIN_VALUE;
        for (int i = 0; i < itemBag.length; i++) {
            Item item = itemBag[i];
            if (item == null || item.template == null) {
                continue;
            }
            if (!isSantaCosmeticType(item.template.type, desiredType)) {
                continue;
            }
            if (!isSantaItemId(item.id, desiredType)) {
                continue;
            }
            long score = getItemScore(item);
            if (score > bestScore) {
                bestScore = score;
                bestBagIndex = i;
            }
        }
        if (bestBagIndex == -1) {
            return;
        }
        Item current = itemBody[bodySlot];
        if (current == null || bestScore > getItemScore(current)) {
            itemBagToBody(bestBagIndex);
        }
    }

    private void runSimpleCombineRules() {
        if (zone == null) {
            return;
        }
        for (int bodyIndex = 0; bodyIndex <= 4; bodyIndex++) {
            if (tryUpgradeEquippedItem(bodyIndex)) {
                return;
            }
        }
        for (int bodyIndex = 0; bodyIndex <= 4; bodyIndex++) {
            if (tryStarUpEquippedItem(bodyIndex)) {
                return;
            }
        }
    }

    private boolean tryUpgradeEquippedItem(int bodyIndex) {
        Item equipped = itemBody[bodyIndex];
        if (equipped == null || equipped.template == null) {
            return false;
        }
        int targetUpgrade = getTargetUpgradeLevel();
        int currentUpgrade = getOptionValue(equipped, 72);
        if (currentUpgrade >= targetUpgrade || getSlotNullInBag() == 0) {
            return false;
        }
        int stoneId = getUpgradeStoneId(equipped.template.type);
        if (stoneId == -1) {
            return false;
        }
        int requiredStone = equipped.template.level + currentUpgrade + 1;
        if (getItemQuantityInBag(stoneId) < requiredStone) {
            ensureStackItem(stoneId, requiredStone + 10);
        }
        itemBodyToBag(bodyIndex);
        int itemIndex = findBestBagItemIndexForType(equipped.template.type);
        int stoneIndex = getIndexBagById(stoneId);
        if (itemIndex == -1 || stoneIndex == -1) {
            equipBestItemsFromBag();
            return false;
        }
        Combine combine = CombineFactory.getCombine(CombineType.NANG_CAP);
        combine.setPlayer(this);
        ArrayList<Byte> items = new ArrayList<>();
        items.add((byte) itemIndex);
        items.add((byte) stoneIndex);
        combine.setItemCombine(items);
        combine.combine();
        equipBestItemsFromBag();
        return true;
    }

    private boolean tryStarUpEquippedItem(int bodyIndex) {
        Item equipped = itemBody[bodyIndex];
        if (equipped == null || equipped.template == null || getSlotNullInBag() == 0) {
            return false;
        }
        int currentStar = getOptionValue(equipped, 107);
        int targetStar = getTargetStarLevel();
        if (currentStar >= targetStar) {
            return false;
        }
        itemBodyToBag(bodyIndex);
        int itemIndex = findBestBagItemIndexForType(equipped.template.type);
        if (itemIndex == -1) {
            equipBestItemsFromBag();
            return false;
        }
        PhaLeHoa phaLeHoa = (PhaLeHoa) CombineFactory.getCombine(CombineType.PHA_LE_HOA);
        phaLeHoa.setPlayer(this);
        phaLeHoa.count = 1;
        ArrayList<Byte> items = new ArrayList<>();
        items.add((byte) itemIndex);
        phaLeHoa.setItemCombine(items);
        phaLeHoa.combine();
        equipBestItemsFromBag();
        return true;
    }

    private int getTargetUpgradeLevel() {
        if (info.power >= 1_000_000_000L) {
            return 5;
        }
        if (info.power >= 300_000_000L) {
            return 4;
        }
        if (info.power >= 50_000_000L) {
            return 3;
        }
        if (info.power >= 5_000_000L) {
            return 2;
        }
        return 1;
    }

    private int getTargetStarLevel() {
        if (info.power >= 500_000_000L) {
            return 3;
        }
        if (info.power >= 100_000_000L) {
            return 2;
        }
        if (info.power >= 20_000_000L) {
            return 1;
        }
        return 0;
    }

    private int getUpgradeStoneId(int itemType) {
        switch (itemType) {
            case Item.TYPE_AO:
                return ItemName.DA_TITAN;
            case Item.TYPE_QUAN:
                return ItemName.DA_RUBY;
            case Item.TYPE_GANGTAY:
                return ItemName.DA_THACH_ANH_TIM;
            case Item.TYPE_GIAY:
                return ItemName.DA_SAPHIA;
            case Item.TYPE_RADA:
                return ItemName.DA_LUC_BAO;
            default:
                return -1;
        }
    }

    private void pickNearbyUsefulItems() {
        if (zone == null) {
            return;
        }
        ItemMap nearest = null;
        int bestDistance = Integer.MAX_VALUE;
        for (ItemMap itemMap : zone.getListItemMap()) {
            if (!shouldLootItem(itemMap)) {
                continue;
            }
            int distance = Utils.getDistance(getX(), getY(), itemMap.x, itemMap.y);
            if (distance < bestDistance) {
                bestDistance = distance;
                nearest = itemMap;
            }
        }
        if (nearest == null) {
            return;
        }
        if (bestDistance > 80) {
            moveTo(nearest.x, nearest.y);
            return;
        }
        pickItem(nearest, bestDistance);
    }

    private boolean shouldLootItem(ItemMap itemMap) {
        if (itemMap == null || itemMap.isPickedUp || itemMap.item == null || itemMap.item.template == null) {
            return false;
        }
        boolean isOwnItem = itemMap.playerID == -1 || itemMap.playerID == this.id;
        boolean contestable = System.currentTimeMillis() - itemMap.throwTime >= 10000
                && itemMap.item.template.type != Item.TYPE_NHIEMVU;
        if (!isOwnItem && !contestable) {
            return false;
        }
        Item item = itemMap.item;
        int type = item.template.type;
        if (type == Item.TYPE_GOLD || type == Item.TYPE_DIAMOND || type == Item.TYPE_DIAMOND_LOCK
                || type == Item.TYPE_NGOCRONG || type == Item.TYPE_NHIEMVU) {
            return true;
        }
        if (type >= Item.TYPE_AO && type <= Item.TYPE_RADA) {
            return true;
        }
        if (item.id == ItemName.DUI_GA
                || item.id == ItemName.DUA_BE
                || item.id == ItemName.TRUYEN_TRANH
                || item.id == ItemName.DA_LUC_BAO
                || item.id == ItemName.DA_SAPHIA
                || item.id == ItemName.DA_RUBY
                || item.id == ItemName.DA_TITAN
                || item.id == ItemName.DA_THACH_ANH_TIM
                || item.id == ItemName.DA_BAO_VE_987) {
            return true;
        }
        return false;
    }

    private void attackQuestTargetOrMob() {
        if (zone == null || skills == null || skills.isEmpty()) {
            return;
        }
        Player questBoss = findQuestBossTarget(zone);
        if (questBoss != null) {
            mobFocus = null;
            attackPlayerTarget(questBoss);
            return;
        }
        Player anyBoss = findAnyBossTarget(zone);
        if (anyBoss != null) {
            mobFocus = null;
            attackPlayerTarget(anyBoss);
            return;
        }
        Mob preferredMob = findPreferredMob(zone);
        if (preferredMob != null) {
            mobFocus = preferredMob;
        }
        attack();
    }

    private void attackPlayerTarget(Player target) {
        if (target == null || target.isDead()) {
            return;
        }
        this.select = skills.get(0);
        this.select.manaUse = 0;
        if (Math.abs(this.getX() - target.getX()) > select.dx * 1.2
                || Math.abs(this.getY() - target.getY()) > select.dy * 1.2) {
            if (meCanMove()) {
                moveTo(target.getX(), target.getY());
            }
            return;
        }
        if (meCanAttack() && System.currentTimeMillis() - lastAtt >= 550) {
            lastAtt = System.currentTimeMillis();
            zone.attackPlayer(this, target);
        }
    }

    private Player findQuestBossTarget(Zone targetZone) {
        if (taskMain == null || targetZone == null) {
            return null;
        }
        for (Player player : targetZone.getListChar(Zone.TYPE_BOSS)) {
            if (player != null && isQuestBossTarget(player)) {
                return player;
            }
        }
        return null;
    }

    private Player findAnyBossTarget(Zone targetZone) {
        if (targetZone == null) {
            return null;
        }
        Player nearest = null;
        int bestDistance = Integer.MAX_VALUE;
        for (Player player : targetZone.getListChar(Zone.TYPE_BOSS)) {
            if (player == null || player == this || player.isDead()) {
                continue;
            }
            int distance = Utils.getDistance(getX(), getY(), player.getX(), player.getY());
            if (distance < bestDistance) {
                bestDistance = distance;
                nearest = player;
            }
        }
        return nearest;
    }

    private boolean isQuestBossTarget(Player target) {
        if (target == null || taskMain == null) {
            return false;
        }
        switch (taskMain.id) {
            case 19:
                return (taskMain.index == 0 && target instanceof KuKu)
                        || (taskMain.index == 1 && target instanceof MapDauDinh)
                        || (taskMain.index == 2 && target instanceof RamBo);
            case 20:
                return (taskMain.index == 1 && target instanceof So4)
                        || (taskMain.index == 2 && target instanceof So3)
                        || (taskMain.index == 3 && target instanceof So2)
                        || (taskMain.index == 4 && target instanceof So1)
                        || (taskMain.index == 5 && target instanceof TieuDoiTruong);
            case 21:
                return taskMain.index >= 1 && taskMain.index <= 3 && target instanceof Fide;
            case 22:
                return (taskMain.index == 2 && target instanceof Android19)
                        || (taskMain.index == 3 && target instanceof Android20);
            case 23:
                return (taskMain.index == 1 && target instanceof Android15)
                        || (taskMain.index == 2 && target instanceof Android14)
                        || (taskMain.index == 3 && target instanceof Android13);
            case 24:
                return (taskMain.index == 1 && target instanceof Poc)
                        || (taskMain.index == 2 && target instanceof Pic)
                        || (taskMain.index == 3 && target instanceof KingKong);
            case 25:
                return taskMain.index >= 1 && taskMain.index <= 3 && target instanceof XenBoHung;
            case 10:
                return (taskMain.index == 0 && (target instanceof Karin || target instanceof Yajiro))
                        || (taskMain.index == 1 && target instanceof TaoPaiPai);
            default:
                return false;
        }
    }

    private Mob findPreferredMob(Zone targetZone) {
        int templateId = getPreferredMobTemplateId();
        if (templateId == -1 || targetZone == null) {
            return null;
        }
        Mob result = null;
        int bestDistance = Integer.MAX_VALUE;
        for (Mob mob : targetZone.getListMob()) {
            if (mob == null || mob.templateId != templateId || mob.hp <= 0 || mob.status == 0 || mob.status == 1) {
                continue;
            }
            int distance = Utils.getDistance(getX(), getY(), mob.x, mob.y);
            if (distance < bestDistance) {
                bestDistance = distance;
                result = mob;
            }
        }
        return result;
    }

    private int getPreferredMobTemplateId() {
        if (taskMain == null) {
            return -1;
        }
        switch (taskMain.id) {
            case 1:
                return taskMain.index == 0 ? MobName.MOC_NHAN : -1;
            case 2:
                return taskMain.index == 0 ? (new int[]{MobName.KHUNG_LONG, MobName.LON_LOI, MobName.QUY_DAT})[gender] : -1;
            case 6:
                if (taskMain.index <= 2) {
                    int[][] mobTask = {
                            {MobName.KHUNG_LONG_ME, MobName.LON_LOI_ME, MobName.QUY_DAT_ME},
                            {MobName.LON_LOI_ME, MobName.QUY_DAT_ME, MobName.KHUNG_LONG_ME},
                            {MobName.QUY_DAT_ME, MobName.KHUNG_LONG_ME, MobName.LON_LOI_ME}
                    };
                    return mobTask[gender][taskMain.index];
                }
                return -1;
            case 7:
                return taskMain.index == 1 ? (new int[]{MobName.THAN_LAN_BAY, MobName.PHI_LONG, MobName.QUY_BAY})[gender] : -1;
            case 8:
                return taskMain.index == 1 ? (new int[]{MobName.PHI_LONG_ME, MobName.QUY_BAY_ME, MobName.THAN_LAN_ME})[gender] : -1;
            case 13:
                if (taskMain.index < 3) {
                    return (new int[]{MobName.HEO_RUNG, MobName.HEO_DA_XANH, MobName.HEO_XAYDA})[taskMain.index];
                }
                return -1;
            case 14:
                return taskMain.index == 1 ? (new int[]{MobName.OC_MUON_HON, MobName.OC_SEN, MobName.HEO_XAYDA_ME})[gender] : -1;
            case 15:
                if (taskMain.index > 0 && taskMain.index < 4) {
                    return (new int[]{MobName.BULON, MobName.UKULELE, MobName.QUY_MAP})[taskMain.index - 1];
                }
                return -1;
            case 16:
                if (taskMain.index < 3) {
                    return (new int[]{MobName.TAMBOURINE, MobName.DRUM, MobName.AKKUMAN})[taskMain.index];
                }
                return -1;
            case 18:
                if (taskMain.index < 5) {
                    return (new int[]{MobName.NAPPA, MobName.SOLDIER, MobName.APPULE, MobName.RASPBERRY, MobName.THAN_LAN_XANH})[taskMain.index];
                }
                return -1;
            case 22:
                return taskMain.index == 4 ? MobName.XEN_CON_CAP_3 : -1;
            case 24:
                return taskMain.index == 4 ? MobName.XEN_CON_CAP__5 : -1;
            case 25:
                return taskMain.index == 4 ? MobName.XEN_CON_CAP__8 : -1;
            default:
                return -1;
        }
    }

    private int getCurrentTaskNpcId() {
        if (taskMain == null || taskMain.tasks == null || taskMain.index < 0 || taskMain.index >= taskMain.tasks.length) {
            return -1;
        }
        return taskMain.tasks[taskMain.index];
    }

    private int getTaskMap(Task task, int index) {
        if (task == null || task.mapTasks == null || index < 0 || index >= task.mapTasks.length) {
            return -1;
        }
        return task.mapTasks[index];
    }

    private int findBestBagItemIndexForType(int itemType) {
        int bestIndex = -1;
        long bestScore = Long.MIN_VALUE;
        for (int i = 0; i < itemBag.length; i++) {
            Item item = itemBag[i];
            if (item == null || item.template == null || item.template.type != itemType) {
                continue;
            }
            if (item.require > info.power) {
                continue;
            }
            long score = getItemScore(item);
            if (score > bestScore) {
                bestScore = score;
                bestIndex = i;
            }
        }
        return bestIndex;
    }

    private long getBestCurrentScoreForType(int itemType) {
        long best = Long.MIN_VALUE;
        Item equipped = itemType >= 0 && itemType < itemBody.length ? itemBody[itemType] : null;
        if (equipped != null) {
            best = Math.max(best, getItemScore(equipped));
        }
        int bagIndex = findBestBagItemIndexForType(itemType);
        if (bagIndex != -1) {
            best = Math.max(best, getItemScore(itemBag[bagIndex]));
        }
        return best == Long.MIN_VALUE ? 0 : best;
    }

    private long getItemScore(Item item) {
        if (item == null || item.template == null) {
            return Long.MIN_VALUE;
        }
        long score = 0;
        score += (long) item.template.level * 1_000_000L;
        score += item.template.require;
        score += (long) getOptionValue(item, 72) * 50_000L;
        score += (long) getOptionValue(item, 107) * 25_000L;
        if (item.options != null) {
            for (ItemOption option : item.options) {
                if (option == null || option.optionTemplate == null) {
                    continue;
                }
                int optionId = option.optionTemplate.id;
                if (optionId == 72 || optionId == 107 || optionId == 30 || optionId == 31 || optionId == 93 || optionId == 102) {
                    continue;
                }
                score += option.param;
            }
        }
        return score;
    }

    private int getOptionValue(Item item, int optionId) {
        if (item == null || item.options == null) {
            return 0;
        }
        for (ItemOption option : item.options) {
            if (option != null && option.optionTemplate != null && option.optionTemplate.id == optionId) {
                return option.param;
            }
        }
        return 0;
    }

    private boolean hasItemInBagOrBody(int itemId) {
        if (itemBody != null) {
            for (Item item : itemBody) {
                if (item != null && item.id == itemId) {
                    return true;
                }
            }
        }
        if (itemBag != null) {
            for (Item item : itemBag) {
                if (item != null && item.id == itemId) {
                    return true;
                }
            }
        }
        return false;
    }

    private int getItemQuantityInBag(int itemId) {
        int quantity = 0;
        if (itemBag == null) {
            return 0;
        }
        for (Item item : itemBag) {
            if (item != null && item.id == itemId) {
                quantity += Math.max(item.quantity, 1);
            }
        }
        return quantity;
    }

    private void equipMatchingBagItem(int itemId) {
        if (itemBag == null) {
            return;
        }
        for (int i = 0; i < itemBag.length; i++) {
            if (itemBag[i] != null && itemBag[i].id == itemId) {
                itemBagToBody(i);
                return;
            }
        }
    }

    private Item createItemFromTemplate(ItemTemplate template) {
        if (template == null) {
            return null;
        }
        Item item = new Item(template.id);
        item.template = template;
        item.require = template.require;
        item.quantity = 1;
        if (item.options != null) {
            item.options.clear();
        }
        if (template.options != null) {
            for (ItemOption option : template.options) {
                if (option != null && option.optionTemplate != null) {
                    item.addItemOption(new ItemOption(option.optionTemplate.id, option.param));
                }
            }
        }
        return item;
    }

    private boolean isSantaItemId(int itemId, int desiredType) {
        Shop santaShop = Shop.getShop(NpcName.BUNMA_TET);
        if (santaShop == null) {
            return false;
        }
        for (ItemTemplate template : santaShop.getListItem(this)) {
            if (template != null && template.id == itemId && isSantaCosmeticType(template.type, desiredType)) {
                return true;
            }
        }
        return false;
    }

    String toPersistentJson() {
        PersistentState state = new PersistentState();
        state.name = this.name;
        state.gender = this.gender;
        state.gold = this.gold;
        state.diamondLock = this.diamondLock;
        state.timeSpawn = this.timeSpawn;
        state.head = this.getHead();
        state.body = this.getBody();
        state.leg = this.getLeg();
        state.mapId = this.zone != null && this.zone.map != null ? this.zone.map.mapID : getFallbackTrainMap();
        state.zoneId = this.zone != null ? this.zone.zoneID : -1;
        state.x = this.getX();
        state.y = this.getY();
        state.info = this.info;
        state.task = captureTaskSnapshot(this.taskMain);
        state.bagItems = captureItemSnapshots(this.itemBag);
        state.bodyItems = captureItemSnapshots(this.itemBody);
        state.boxItems = captureItemSnapshots(this.itemBox);
        return BOT_STATE_GSON.toJson(state);
    }

    boolean restoreFromPersistentJson(String json) {
        if (json == null || json.isEmpty()) {
            return false;
        }
        PersistentState state;
        try {
            state = BOT_STATE_GSON.fromJson(json, PersistentState.class);
        } catch (Exception e) {
            return false;
        }
        if (state == null) {
            return false;
        }
        applyPersistentState(state);
        return true;
    }

    boolean spawnFromPersistedLocation() {
        int mapId = persistedMapId;
        if (MapManager.getInstance().getMap(mapId) == null) {
            mapId = getFallbackTrainMap();
        }
        TMap map = MapManager.getInstance().getMap(mapId);
        if (map == null) {
            return false;
        }
        int zoneId = persistedZoneId;
        Zone targetZone = zoneId >= 0 ? map.getZoneByID(zoneId) : null;
        if (targetZone == null) {
            targetZone = map.getMinPlayerZone();
        }
        if (targetZone == null) {
            return false;
        }
        short targetX = persistedX > 0 ? persistedX : (short) (map.width / 2);
        targetX = clampX(targetX, map);
        short targetY = resolveY(persistedY, map, targetX);
        setX(targetX);
        setY(targetY);
        targetZone.enter(this);
        return true;
    }

    private void applyPersistentState(PersistentState state) {
        if (state.name != null && !state.name.isEmpty()) {
            this.name = state.name;
        }
        if (state.gender >= 0 && state.gender <= 2) {
            this.gender = (byte) state.gender;
        }
        if (state.gold > 0) {
            this.gold = state.gold;
        }
        if (state.diamondLock >= 0) {
            this.diamondLock = state.diamondLock;
        }
        if (state.timeSpawn > 0) {
            this.timeSpawn = state.timeSpawn;
        }

        this.persistedMapId = state.mapId;
        this.persistedZoneId = state.zoneId;
        this.persistedX = state.x;
        this.persistedY = state.y;

        restoreArrayFromSnapshot(state.bagItems, this.itemBag);
        restoreArrayFromSnapshot(state.bodyItems, this.itemBody);
        restoreArrayFromSnapshot(state.boxItems, this.itemBox);
        restoreInfoSnapshot(state.info);
        restoreTaskSnapshot(state.task);
        ensureStarterBodyItemsLikeNewCharacter();

        if (state.head > 0) {
            this.setHead((short) state.head);
            this.setHeadDefault((short) state.head);
        } else {
            setHeadByGender();
        }
        if (state.body > 0) {
            this.setBody((short) state.body);
        } else {
            this.setDefaultBody();
        }
        if (state.leg > 0) {
            this.setLeg((short) state.leg);
        } else {
            this.setDefaultLeg();
        }
        updateSkin();
    }

    @Override
    public void setDefaultHead() {
        setHead(this.getHeadDefault());
    }

    @Override
    public void setDefaultLeg() {
        if (this.gender == 0) {
            setLeg((short) 58);
            return;
        }
        if (this.gender == 1) {
            setLeg((short) 60);
            return;
        }
        setLeg((short) 58);
    }

    @Override
    public void setDefaultBody() {
        if (this.gender == 0) {
            setBody((short) 57);
            return;
        }
        if (this.gender == 1) {
            setBody((short) 59);
            return;
        }
        setBody((short) 57);
    }

    private void restoreInfoSnapshot(Info infoSnapshot) {
        if (infoSnapshot == null) {
            return;
        }
        this.info = infoSnapshot;
        this.info.setChar(this);
        this.info.setPowerLimited();
        this.info.setStamina();
        this.info.setInfo();
        this.info.recovery(Info.ALL, 100, false);
    }

    private void restoreTaskSnapshot(TaskSnapshot snapshot) {
        if (snapshot == null || snapshot.id < 0) {
            return;
        }
        try {
            Task task = new Task();
            task.id = Math.max(snapshot.id, BOT_INITIAL_TASK_ID);
            task.index = Math.max(snapshot.index, 0);
            task.count = Math.max(snapshot.count, 0);
            task.lastTask = snapshot.lastTask > 0 ? snapshot.lastTask : System.currentTimeMillis();
            task.initTask(this.gender);
            if (task.tasks != null && task.tasks.length > 0 && task.index >= task.tasks.length) {
                task.index = task.tasks.length - 1;
                task.count = 0;
            }
            this.taskMain = task;
            this.setListAccessMap();
        } catch (Exception ignored) {
        }
    }

    private TaskSnapshot captureTaskSnapshot(Task task) {
        if (task == null) {
            return null;
        }
        TaskSnapshot snapshot = new TaskSnapshot();
        snapshot.id = task.id;
        snapshot.index = task.index;
        snapshot.count = task.count;
        snapshot.lastTask = task.lastTask;
        return snapshot;
    }

    private List<ItemSlotSnapshot> captureItemSnapshots(Item[] source) {
        List<ItemSlotSnapshot> snapshots = new ArrayList<>();
        if (source == null) {
            return snapshots;
        }
        for (int i = 0; i < source.length; i++) {
            Item copied = copyItemForSnapshot(source[i], i);
            if (copied == null) {
                continue;
            }
            ItemSlotSnapshot snapshot = new ItemSlotSnapshot();
            snapshot.slot = i;
            snapshot.item = copied;
            snapshots.add(snapshot);
        }
        return snapshots;
    }

    private Item copyItemForSnapshot(Item source, int index) {
        if (source == null) {
            return null;
        }
        Item copied;
        try {
            copied = new Item(source.id);
        } catch (Exception e) {
            return null;
        }
        if (copied.template == null) {
            return null;
        }
        copied.indexUI = index;
        copied.quantity = Math.max(source.quantity, 1);
        if (!copied.template.isUpToUp() && copied.quantity > 1) {
            copied.quantity = 1;
        }
        if (copied.options != null) {
            copied.options.clear();
        }
        if (source.options != null) {
            for (ItemOption option : source.options) {
                if (option == null) {
                    continue;
                }
                int optionId = option.id > 0 ? option.id
                        : option.optionTemplate != null ? option.optionTemplate.id : -1;
                if (optionId < 0) {
                    continue;
                }
                copied.addItemOption(new ItemOption(optionId, option.param));
            }
        }
        return copied;
    }

    private void restoreArrayFromSnapshot(List<ItemSlotSnapshot> snapshots, Item[] target) {
        if (target == null) {
            return;
        }
        Arrays.fill(target, null);
        if (snapshots == null) {
            return;
        }
        for (ItemSlotSnapshot snapshot : snapshots) {
            if (snapshot == null || snapshot.item == null) {
                continue;
            }
            int slot = snapshot.slot;
            if (slot < 0 || slot >= target.length) {
                continue;
            }
            target[slot] = normalizeSnapshotItem(snapshot.item, slot);
        }
    }

    private Item normalizeSnapshotItem(Item source, int slot) {
        if (source == null) {
            return null;
        }
        Item normalized;
        try {
            normalized = new Item(source.id);
        } catch (Exception e) {
            return null;
        }
        if (normalized.template == null) {
            return null;
        }
        normalized.indexUI = slot;
        normalized.quantity = Math.max(source.quantity, 1);
        if (!normalized.template.isUpToUp() && normalized.quantity > 1) {
            normalized.quantity = 1;
        }
        if (normalized.options != null) {
            normalized.options.clear();
        }
        if (source.options != null) {
            for (ItemOption option : source.options) {
                if (option == null) {
                    continue;
                }
                int optionId = option.id > 0 ? option.id
                        : option.optionTemplate != null ? option.optionTemplate.id : -1;
                if (optionId < 0) {
                    continue;
                }
                normalized.addItemOption(new ItemOption(optionId, option.param));
            }
        }
        return normalized;
    }

    private short clampX(short x, TMap map) {
        if (map == null || map.width <= 0) {
            return x;
        }
        int minX = 24;
        int maxX = Math.max(24, map.width - 24);
        return (short) Math.max(minX, Math.min(maxX, x));
    }

    private short resolveY(short y, TMap map, short x) {
        if (map == null) {
            return y;
        }
        short baseY = y > 0 ? y : (short) 24;
        return map.collisionLand(x, baseY);
    }

    private static class PersistentState {

        int version = 1;
        String name;
        int gender = -1;
        long gold;
        int diamondLock;
        long timeSpawn;
        int head = -1;
        int body = -1;
        int leg = -1;
        int mapId = -1;
        int zoneId = -1;
        short x;
        short y;
        Info info;
        TaskSnapshot task;
        List<ItemSlotSnapshot> bagItems;
        List<ItemSlotSnapshot> bodyItems;
        List<ItemSlotSnapshot> boxItems;
    }

    private static class TaskSnapshot {

        int id;
        int index;
        int count;
        long lastTask;
    }

    private static class ItemSlotSnapshot {

        int slot;
        Item item;
    }

    @Override
    void setBag1() {
        byte[] bag = new byte[]{19, 20, 21, 22, 105, 106};
        this.clanID = Utils.nextInt(0, 100);
        this.setBag(bag[Utils.nextInt(bag.length)]);
        this.name = Utils.getAbbre("[" + ConfigStudio.SLOGAN_BOTSOSINH + "]") + this.name;
    }

    @Override
    public boolean isBoss() {
        return false;
    }

    @Override
    public boolean isHuman() {
        return true;
    }

    @Override
    public void close() {
        VirtualBotSoSinhPersistence.deleteBotByName(this.name);
        removeFromSharedClan();
        super.close();
    }
}
