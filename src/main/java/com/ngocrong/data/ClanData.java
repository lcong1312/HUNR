package com.ngocrong.data;

import _HunrProvision.ConfigStudio;
import com.ngocrong.user.Player;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "nr_clan")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClanData {

    private static final int MAX_ABBREVIATION_LENGTH = 10;
    private static final String DEFAULT_CLAN_ABBREVIATION = "CLAN";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Integer id;

    @Column(name = "name")
    public String name;

    @Column(name = "abbreviation", length = 10)
    public String abbreviation;

    @Column(name = "leader_id")
    public Integer leaderId;

    @Column(name = "leader_name")
    public String leaderName;

    @Column(name = "slogan")
    public String slogan;

    @Column(name = "image_id")
    public Byte imageId;

    @Column(name = "level")
    public Byte level;

    @Column(name = "clan_point")
    public Integer clanPoint;

    @Column(name = "power_point")
    public Long powerPoint;

    @Column(name = "max_member")
    public Byte maxMember;

    @Column(name = "item_box")
    public String itemBox;

    @Column(name = "create_time")
    public Timestamp createTime;

    public ClanData(String name, byte imageId, Player _player) {
        this.imageId = imageId;
        this.leaderId = _player.id;
        leaderName = _player.name;
        this.name = name;
        clanPoint = 0;
        maxMember = 10;
        powerPoint = 0L;
        level = 1;
        slogan = ConfigStudio.SLOGAN;
        abbreviation = buildDefaultAbbreviation();
        itemBox = "[]";
        createTime = new Timestamp(System.currentTimeMillis());
    }

    private static String buildDefaultAbbreviation() {
        String configured = ConfigStudio.ABBREVIATION == null ? "" : ConfigStudio.ABBREVIATION.trim();
        if (!configured.isEmpty()) {
            return configured.substring(0, Math.min(MAX_ABBREVIATION_LENGTH, configured.length()));
        }
        return DEFAULT_CLAN_ABBREVIATION;
    }
}
