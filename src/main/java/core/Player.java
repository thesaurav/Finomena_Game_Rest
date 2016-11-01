package core;

/**
 * Created by kumar on 29-10-2016.
 */
public class Player
{
    private String  playerId;
    private String  playerName;
    private int score ;
    private String ip ;

//    public Player(String playerName, String ip)
//    {
//        this.playerId = playerName + "" +(Math.random() * 10000) ;
//        this.playerName = playerName;
//        this.score = 0;
//        this.ip = ip;
//    }

    public Player()
    {
    }
    public Player(String playerId, String playerName, int score, String ip)
    {
        this.playerId = playerId;
        this.playerName = playerName;
        this.score = score;
        this.ip = ip;
    }

    public String getPlayerId()
    {
        return playerId;
    }

    public String getPlayerName()
    {
        return playerName;
    }

    public int getScore()
    {
        return score;
    }

    public String getIp()
    {
        return ip;
    }

    public void setPlayerId(String playerId)
    {
        this.playerId = playerId;
    }

    public void setPlayerName(String playerName)
    {
        this.playerName = playerName;
    }

    public void setScore(int score)
    {
        this.score = score;
    }

    public void setIp(String ip)
    {
        this.ip = ip;
    }
}
