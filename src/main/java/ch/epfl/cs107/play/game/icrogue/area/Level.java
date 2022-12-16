package ch.epfl.cs107.play.game.icrogue.area;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.AreaGame;
import ch.epfl.cs107.play.game.icrogue.ICRogue;
import ch.epfl.cs107.play.game.icrogue.actor.Connector;
import ch.epfl.cs107.play.game.icrogue.actor.ICRoguePlayer;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

import java.util.ArrayList;


public class Level {
    private ICRogueRoom[][] map;
    private DiscreteCoordinates spawnCoordinates;
    private DiscreteCoordinates bossPosition;
    private String startingRoomTitle;

    public Level(int width, int height, DiscreteCoordinates spawnCoordinates) {
        map = new ICRogueRoom[width][height];
        this.spawnCoordinates =  spawnCoordinates;
        this.bossPosition = new DiscreteCoordinates(0,0);
    }

    public DiscreteCoordinates getSpawnCoordinates() {
        return spawnCoordinates;
    }

    public void addRooms(AreaGame areaGame) {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if(map[i][j] != null) {
                    areaGame.addArea(map[i][j]);
                }

            }
        }
    }
    public ICRogueRoom getRoom(DiscreteCoordinates coords) {
        return map[coords.x][coords.y];
    }

    public ICRogueRoom[][] getRooms() {
        return map;
    }



    protected void setRoom(DiscreteCoordinates coords, ICRogueRoom room) {
        map[coords.x][coords.y] = room;
    }
    protected void setRoomConnectorDestination(DiscreteCoordinates coords, String destination, ConnectorInRoom connector) {
        map[coords.x][coords.y].connectors.get(connector.getIndex()).destination = destination;
    }
    protected void setRoomConnector(DiscreteCoordinates coords, String destination, ConnectorInRoom connector) {
        map[coords.x][coords.y].connectors.get(connector.getIndex()).setDestination(destination);
        map[coords.x][coords.y].connectors.get(connector.getIndex()).setState(Connector.State.CLOSED);
    }
    protected void lockRoomConnector(DiscreteCoordinates coords, ConnectorInRoom connector, int keyId) {
        map[coords.x][coords.y].connectors.get(connector.getIndex()).setKeyID(keyId);
        map[coords.x][coords.y].connectors.get(connector.getIndex()).setState(Connector.State.LOCKED);

    }
    protected void setCoordinatesToStarterRoomTitle(DiscreteCoordinates coordinates) {
        startingRoomTitle = "icrogue/level0" + coordinates.x + coordinates.y;

    }
}