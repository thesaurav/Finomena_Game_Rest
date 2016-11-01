package jdbi;

import core.Player;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

/**
 * Created by kumar on 29-10-2016.
 */

@RegisterMapper(PlayerMapper.class)
public interface PlayerDAO
{
    @SqlQuery("select * from Player where playerId  = :playerId")
    Player findByPlayerID(@Bind("playerId") String playerId);

    @SqlUpdate("INSERT  INTO Player (playerId, playerName, score, ip) VALUES (:playerId, :playerName, :score, :ip)")
    void insertInToDB(@Bind("playerId") String playerId, @Bind("playerName") String playerName, @Bind("score") int score, @Bind("ip") String ip);

    @SqlUpdate("UPDATE  Player SET score = :score where playerId = :playerId")
    void updateScore(@Bind("playerId") String playerId, @Bind("score") int score);

}