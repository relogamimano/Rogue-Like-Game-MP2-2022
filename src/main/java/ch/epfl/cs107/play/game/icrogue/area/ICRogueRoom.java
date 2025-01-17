package ch.epfl.cs107.play.game.icrogue.area;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.icrogue.ICRogueBehavior;
import ch.epfl.cs107.play.game.icrogue.actor.Connector;
import ch.epfl.cs107.play.game.icrogue.actor.Connector.State;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Keyboard;
import ch.epfl.cs107.play.window.Window;

import java.util.ArrayList;
import java.util.List;

public abstract class ICRogueRoom extends Area {
    final private int WEST_CONNECTOR_NB = 0;
    final private int SOUTH_CONNECTOR_NB = 1;
    final private int EAST_CONNECTOR_NB = 2;
    final private int NORTH_CONNECTOR_NB = 3;

    public enum RoomType {
        TurretRoom, StaffRoom, Boss_key, Spawn, Normal;
    }

    private State state;
    private ICRogueBehavior behavior;
    // TODO: 14.12.22 getRoomCoordinates() ou protected ? 
    final protected DiscreteCoordinates roomCoordinates;
    protected String behaviorName;

    protected List<Connector> connectors;


    public ICRogueRoom(List<DiscreteCoordinates> connectorsCoordinates, List<Orientation> orientations,
                String behaviorName, DiscreteCoordinates roomCoordinates, List<DiscreteCoordinates> directionRoomSpawnCoordinates){
        // TODO: 10.12.22 intialization douteuse 
        connectors = new ArrayList<>();

        this.roomCoordinates = roomCoordinates;
        this.behaviorName = behaviorName;
        this.state = State.INVISIBLE;

        for (int i = 0; i < connectorsCoordinates.size(); i++) {
            // TODO: 10.12.22 .opposite() well used ? 
            connectors.add(new Connector(this, orientations.get(i).opposite(),connectorsCoordinates.get(i), state, directionRoomSpawnCoordinates.get(i)));
        }
    }

    // TODO: 10.12.22 createArea() abstract ? Should we intialize connecotrs in ICRogueRooom or in Level0ROom ?
    protected abstract void createArea();

    public List<Connector> getConnectors(){
        return connectors;
    }




    @Override
    public void update(float deltaTime) {
        Keyboard keyboard = this.getKeyboard();
        if(keyboard.get(Keyboard.O).isDown()) {
            for (Connector connector: connectors) {
                connector.setState(State.OPEN);
            }
        }
        if(keyboard.get(Keyboard.L).isDown()) {
            connectors.get(WEST_CONNECTOR_NB).setState(State.LOCKED);
        }
        if(keyboard.get(Keyboard.T).isDown()) {
            for (Connector connector: connectors) {
                if (connector.getState() == State.CLOSED) {
                    connector.setState(State.OPEN);
                } else if(connector.getState() == State.OPEN) {
                    connector.setState(State.CLOSED);
                }
            }
        }
        super.update(deltaTime);
    }

    @Override
    public String getTitle() {
        return null;
    } // TODO: 06.12.22 si erreur null peut etre changé en une string par defaut

    @Override
    public float getCameraScaleFactor() {
        return 11;
    }

    @Override
    public boolean begin(Window window, FileSystem fileSystem) {
        if (super.begin(window, fileSystem)) {
            // Set the behavior map
            behavior = new ICRogueBehavior(window, behaviorName);
            setBehavior(behavior);
            createArea();
            return true;
        }
        return false;
    }


}

