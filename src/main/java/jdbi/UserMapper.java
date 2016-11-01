package jdbi;

import core.User;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by SauravKumar on 30-05-2016.
 */
public class UserMapper implements ResultSetMapper<User>
{
    public User map(int index, ResultSet r, StatementContext ctx) throws SQLException
    {
        return new User(r.getString("userName"), r.getString("userId"));
    }
}
