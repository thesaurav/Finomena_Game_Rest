package apiResource;

import com.codahale.metrics.annotation.Timed;
import config.FGameRestConfig;
import core.Player;
import jdbi.PlayerDAO;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by kumar on 29-10-2016.
 */
@Path("/FGame")
public class FRestApiCallLanding
{
    private FGameRestConfig config;
    private PlayerDAO dao;
    private StringBuilder selected = new StringBuilder("");
    private StringBuilder latest = new StringBuilder("");
    private int totalPlayer;
    private Map<String, Boolean> occupied;
    private int x;
    private int y;
    private int blockingTime;
    private int maxPlayer;
    private int minPlayer;
    private boolean isConfig = false;

    public FRestApiCallLanding(FGameRestConfig fGameRestConfig, PlayerDAO dao)
    {
        this.config = config;
        this.dao = dao;
        occupied = new HashMap<String, Boolean>();
    }

    @POST
    @Timed
    @Path("add")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addNewPlayer(Player player)
    {
        if(!isConfig)
        {
            return null;
        }
        StringBuilder returnStr = new StringBuilder("");

        String playerId = player.getPlayerId();
        String playerName = player.getPlayerName();
        int score = player.getScore();
        String ip = player.getIp();

        long mul = 10000000000L;
        StringBuilder color = new StringBuilder("");
        if (playerId!= null && playerId.equals(""))
        {
            playerId = playerName + (long)(Math.random() * mul);
            int temp = (int)(Math.random() * 255);
            color.append(temp);
            color.append("_");

            temp = (int)(Math.random() * 255);
            color.append(temp);
            color.append("_");

            temp = (int)(Math.random() * 255);
            color.append(temp);
        }
        dao.insertInToDB(playerId, playerName, score, ip);
        totalPlayer++;
        returnStr.append(playerId);
        returnStr.append("-");
        returnStr.append(color);
        returnStr.append(selected.toString());
        return Response.status(201).entity(returnStr.toString()).build();
    }

    @POST
    @Timed
    @Path("updateScore")
    public Response updateScore(String str)
    {
        int score = 0;
        if(occupied.get(str.substring(1, str.indexOf("&"))) == null)
        {
            if (totalPlayer >= minPlayer)
            {
                selected.append(str.substring(0, str.indexOf(":")));
                latest.append(str.substring(1, str.indexOf(":")));
                latest.append(":");
                occupied.put(str.substring(1, str.indexOf("&")), true);
                Player player = dao.findByPlayerID(str.substring((str.indexOf(":") + 1), str.length()));
                score = player.getScore() + 10;
                dao.updateScore(player.getPlayerId(), player.getScore() + 10);
            }
        }
        return Response.status(200).entity(score).build();
    }

    @GET
    @Timed
    @Path("dynamic")
    public Response getLatest(String str)
    {
        String retStr = latest.toString();
        return Response.status(200).entity(retStr).build();
    }

    @POST
    @Timed
    @Path("configure")
    public Response configure(String str)
    {
        String [] attributes = str.split(":");
        int x = Integer.parseInt(attributes[0]);
        int y = Integer.parseInt(attributes[1]);
        int min = Integer.parseInt(attributes[2]);
        int max = Integer.parseInt(attributes[3]);
        this.x = x;
        this.y = y;
        this.minPlayer = min;
        this.maxPlayer = max;
        isConfig = true;
        return Response.status(201).entity("Configured").build();
    }

    @POST
    @Timed
    @Path("tableWidth")
    public Response getTableXY(String str)
    {
        String retStr = x + ":" + y;
        return Response.status(201).entity(retStr).build();
    }
}
