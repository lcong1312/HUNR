package com.ngocrong;

import com.ngocrong.repository.*;
import com.ngocrong.security.MatrixChallengePC;
import com.ngocrong.server.DragonBall;
import com.ngocrong.server.DropRateService;
import com.ngocrong.server.mysql.SchemaCompatibility;
import com.ngocrong.util.VietnamTime;
import _HunrProvision.services.BoMongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.TimeZone;

@SpringBootApplication
public class Run implements CommandLineRunner {

    public static void main(String[] args) {
        TimeZone vietnamTimeZone = VietnamTime.timeZone();
        TimeZone.setDefault(vietnamTimeZone);
        System.setProperty("user.timezone", vietnamTimeZone.getID());
        SpringApplication.run(Run.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        try (Connection connection = dataSource.getConnection()) {
            SchemaCompatibility.ensureMinimumSchema(connection);
        }

        GameRepository.getInstance().user = userDataRepository;
        GameRepository.getInstance().player = playerDataRepository;
        GameRepository.getInstance().giftCode = giftCodeDataRepository;
        GameRepository.getInstance().giftCodeHistory = giftCodeHistoryDataRepository;
        GameRepository.getInstance().disciple = discipleDataRepository;
        GameRepository.getInstance().clan = clanDataRepository;
        GameRepository.getInstance().clanMember = clanMemberDataRepository;
        GameRepository.getInstance().consignmentItem = consignmentItemDataRepository;
        GameRepository.getInstance().ccuDataRepository = ccuDataRepository;
        GameRepository.getInstance().eventOpenRepository = eventOpenRepository;
        GameRepository.getInstance().dhvtSieuHangRepository = dhvtSieuHangRepository;
        GameRepository.getInstance().dhvtSieuHangRewardRepository = dhvtSieuHangRewardRepository;
        GameRepository.getInstance().gameEventRepository = gameEventRepository;
        GameRepository.getInstance().eventVQTD = eventVQTD;
        GameRepository.getInstance().eventTet = eventTet;
        GameRepository.getInstance().historyGoldBar = historyGoldBar;
        GameRepository.getInstance().historyTradeRepository = historyTradeRepository;
        GameRepository.getInstance().whisDataRepository = whisDataRepository;
        GameRepository.getInstance().dropRateRepository = dropRateRepository;
        GameRepository.getInstance().osinCheckInRepository = osinReward;
        GameRepository.getInstance().osinLixiRepository = osinLixiRepository;
        GameRepository.getInstance().statisticServerRepository = statisticServerRepository;
        GameRepository.getInstance().securityRepository = security;
        GameRepository.getInstance().mabuEggRepository = mabuEggRepository;
        GameRepository.getInstance().boMongNhiemVu = boMongNhiemVuRepository;
        GameRepository.getInstance().boMongNhiemVuConfig = boMongNhiemVuConfigRepository;
        GameRepository.getInstance().boMongBossConfig = boMongBossConfigRepository;
        GameRepository.getInstance().boMongConfig = boMongConfigRepository;
        GameRepository.getInstance().boMongMocDiem = boMongMocDiemRepository;
        GameRepository.getInstance().botConfig = botConfigRepository;
        DropRateService.load();
        if (!_HunrProvision.ConfigStudio.BO_MONG_LEGACY_MODE) {
            BoMongService.loadConfig();
        }
        MatrixChallengePC.loadPCKey();
        DragonBall.getInstance().start();
    }

    @Autowired
    OsinCheckInRepository osinReward;

    @Autowired
    OsinLixiRepository osinLixiRepository;

    @Autowired
    WhisDataRepository whisDataRepository;

    @Autowired
    UserDataRepository userDataRepository;

    @Autowired
    PlayerDataRepository playerDataRepository;

    @Autowired
    GiftCodeDataRepository giftCodeDataRepository;

    @Autowired
    GiftCodeHistoryDataRepository giftCodeHistoryDataRepository;

    @Autowired
    DiscipleDataRepository discipleDataRepository;

    @Autowired
    ClanDataRepository clanDataRepository;

    @Autowired
    ClanMemberDataRepository clanMemberDataRepository;

    @Autowired
    ConsignmentItemDataRepository consignmentItemDataRepository;

    @Autowired
    CCUDataRepository ccuDataRepository;
    @Autowired
    EventOpenRepository eventOpenRepository;
    @Autowired
    DHVTSieuHangRepository dhvtSieuHangRepository;
    @Autowired
    DhvtSieuHangRewardRepository dhvtSieuHangRewardRepository;

    @Autowired
    GameEventRepository gameEventRepository;

    @Autowired
    EventVQTD eventVQTD;

    @Autowired
    SuKienTetRepository eventTet;

    @Autowired
    HistoryGoldBarRepository historyGoldBar;
    @Autowired
    HistoryTradeRepository historyTradeRepository;

    @Autowired
    DropRateRepository dropRateRepository;

    @Autowired
    StatisticServerRepository statisticServerRepository;

    @Autowired
    SecurityRepository security;

    @Autowired
    MabuEggRepository mabuEggRepository;


    @Autowired
    BoMongNhiemVuRepository boMongNhiemVuRepository;

    @Autowired
    BoMongNhiemVuConfigRepository boMongNhiemVuConfigRepository;

    @Autowired
    BoMongBossConfigRepository boMongBossConfigRepository;

    @Autowired
    BoMongConfigRepository boMongConfigRepository;

    @Autowired
    BoMongMocDiemRepository boMongMocDiemRepository;

    @Autowired
    BotConfigRepository botConfigRepository;

    @Autowired
    DataSource dataSource;
}
