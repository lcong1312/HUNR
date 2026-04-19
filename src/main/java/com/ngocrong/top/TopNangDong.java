package com.ngocrong.top;

import com.ngocrong.server.mysql.MySQLConnect;
import org.apache.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class TopNangDong extends Top {

    private static final Logger logger = Logger.getLogger(TopNangDong.class);

    public TopNangDong(int id, byte type, String name, byte limit) {
        super(id, type, name, limit);
    }

    @Override
    public void load() {
        try {
            PreparedStatement ps = MySQLConnect.getConnection().prepareStatement(
                    "SELECT np.id, np.name, np.head2, np.body, np.leg, COALESCE(nu.nang_dong, 0) AS nang_dong "
                            + "FROM nr_player np "
                            + "INNER JOIN nr_user nu ON nu.id = np.user_id "
                            + "ORDER BY nang_dong DESC, np.id ASC "
                            + "LIMIT ?");
            ps.setInt(1, limit);
            ResultSet rs = ps.executeQuery();
            try {
                while (rs.next()) {
                    TopInfo info = new TopInfo();
                    info.playerID = rs.getInt("id");
                    info.name = rs.getString("name");
                    info.head = rs.getInt("head2");
                    info.body = rs.getShort("body");
                    info.leg = rs.getShort("leg");
                    info.info = "";
                    info.info2 = rs.getLong("nang_dong") + " Điểm";
                    info.score = rs.getLong("nang_dong");
                    elements.add(info);
                }
            } finally {
                rs.close();
                ps.close();
            }
            update();
            updateLowestScore();
        } catch (Exception ex) {
            logger.debug("failed", ex);
        }
    }
}
