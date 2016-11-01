package apiResource;

import com.codahale.metrics.annotation.Timed;
import config.FGameRestConfig;
import core.Player;
import jdbi.PlayerDAO;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kumar on 29-10-2016.
 */
@Path("/FGame")
public class FRestApiCallLanding
{
    WebSocket webSocket;
    private FGameRestConfig config;
    private PlayerDAO dao;
    private StringBuilder selected = new StringBuilder("");
    private int totalPlayer;
    private List<Socket> client;
    private ServerSocket echoServer;

    public FRestApiCallLanding(FGameRestConfig fGameRestConfig, PlayerDAO dao)
    {
        this.config = config;
        this.dao = dao;
    }

    @GET
    @Timed
    @Path("search1")
    @Produces(MediaType.APPLICATION_JSON)
    public String sayHello()
    {
        //final User value = dao.findByName(name.get());
        return "{\"id\": 238, \"content\": \"Hello, World!\"}";
    }

    @POST
    @Timed
    @Path("add")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addNewPlayer(Player player)
    {
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
        socketHandle();
        return Response.status(201).entity(returnStr.toString()).build();
    }

    @POST
    @Timed
    @Path("updateScore")
    public Response updateScore(String str)
    {
        String retCode = "Success";
        if(totalPlayer < 2)
        {
            retCode = "Please Wait";
        }
        selected.append(str);
        DataInputStream is;
        PrintStream os;
        for (Socket soc : client)
        {
            System.out.println("socket created " + soc.getPort());
            try
            {
                //is = new DataInputStream(soc.getInputStream());
                os = new PrintStream(soc.getOutputStream());
                //String line = is.readLine();
                os.println(str.substring(1, str.length()));
                //System.out.println(line);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        return Response.status(200).entity(retCode).build();
    }

    @GET
    @Path("{id}")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response getPodcastById(@PathParam("id") Long id, @QueryParam("detailed") boolean detailed)
            throws IOException {

        return Response.ok() //200
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
                .allow("OPTIONS").build();
    }


    private void socketHandle()
    {
        Thread t =new Thread(new Runnable()
        {
            public void run()
            {
                String line = "Saurav";
                DataInputStream is;
                PrintStream os;
                client = new ArrayList<Socket>();
                Socket clientSocket = null;
                try {
                    if(echoServer == null)
                    echoServer = new ServerSocket(9999);
                }
                catch (IOException e) {
                    System.out.println(e +"Jejkwkerjwkerj");
                }
                try {

                    clientSocket = echoServer.accept();
                    System.out.println("socket created " + clientSocket.getPort());
                    client.add(clientSocket);
/*                    is = new DataInputStream(clientSocket.getInputStream());
                    os = new PrintStream(clientSocket.getOutputStream());
                    while (true) {
                        line = is.readLine();
                        os.println("saurav");
                        System.out.println(line);
                    }*/
                }
                catch (IOException e) {
                    System.out.println(e);
                    e.printStackTrace();
                }
            }
        });
        t.start();
    }
}
