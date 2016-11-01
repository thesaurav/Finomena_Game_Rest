package jdbi;

import core.Player;
import core.User;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by kumar on 29-10-2016.
 */
public class PlayerMapper implements ResultSetMapper<Player>
{
    public Player map(int index, ResultSet r, StatementContext ctx) throws SQLException
    {
        return new Player(r.getString("playerId"), r.getString("playerName"), r.getInt("score"), r.getString("ip"));
    }
}
