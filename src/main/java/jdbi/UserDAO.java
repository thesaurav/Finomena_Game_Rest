package jdbi;

import core.User;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

/**
 * Created by SauravKumar on 30-05-2016.
 */
@RegisterMapper(UserMapper.class)
public interface UserDAO
{
    @SqlQuery("select * from user where userName = :userName")
    User findByName(@Bind("userName") String name);

    @SqlUpdate("INSERT  INTO user (userName, userId) VALUES (:userName, :userId)")
    void insertInToDB(@Bind("userName") String name, @Bind("userId") String userId);

}
