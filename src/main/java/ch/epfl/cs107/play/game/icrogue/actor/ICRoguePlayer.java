package ch.epfl.cs107.play.game.icrogue.actor;

import ch.epfl.cs107.play.game.areagame.actor.MovableAreaEntity;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.icrogue.area.level0.rooms.Level0Room;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.RegionOfInterest;
import ch.epfl.cs107.play.window.Canvas;
import ch.epfl.cs107.play.game.actor.TextGraphics;
import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Button;
import ch.epfl.cs107.play.window.Canvas;
import ch.epfl.cs107.play.window.Keyboard;
import ch.epfl.cs107.play.game.icrogue.ICRogue;

import java.awt.*;
import java.util.Collections;
import java.util.List;

public class ICRoguePlayer extends ICRogueActor {
    private float hp;
    private TextGraphics message;
    private Sprite downSprite;
    private Sprite rightSprite;
    private Sprite upSprite;
    private Sprite leftSprite;

    Level0Room currentRoom;

    private ICRoguePlayer player;



    /// Animation duration in frame number
    private final static int MOVE_DURATION = 8;

    public ICRoguePlayer(Area owner, Orientation orientation, DiscreteCoordinates coordinates, String spriteName) {
        super(owner, orientation, coordinates);
        this.hp = 10;
        message = new TextGraphics(Integer.toString((int)hp), 0.4f, Color.BLUE);
        message.setParent(this);
        message.setAnchor(new Vector(-0.3f, 0.1f));
        // TODO: 06.12.22 for loop instead of repeating initilalization of sprite
        downSprite = new Sprite("zelda/player", .75f, 1.5f, this, new RegionOfInterest(0, 0, 16, 32), new Vector(.15f, -.15f));
        rightSprite = new Sprite("zelda/player", .75f, 1.5f, this, new RegionOfInterest(0, 32, 16, 32), new Vector(.15f, -.15f));
        upSprite = new Sprite("zelda/player", .75f, 1.5f, this, new RegionOfInterest(0, 64, 16, 32), new Vector(.15f, -.15f));
        leftSprite = new Sprite("zelda/player", .75f, 1.5f, this, new RegionOfInterest(0, 96, 16, 32), new Vector(.15f, -.15f));
        resetMotion();
    }

    /**
     * Center the camera on the player
     */
    public void centerCamera() {
        getOwnerArea().setViewCandidate(this);
    }

    // TODO: 06.12.22 boule de feu 
//    BOULE DE FEU  :
    private void bouleDeFeu() {
//        lancerBouleDeFeuIfPressed(keyboard.get(Keyboard.X));
//        la boule va dans la meme direction
        
    }

    @Override
    public void update(float deltaTime) {
        if (hp > 0) {
            hp -=deltaTime;
            message.setText(Integer.toString((int)hp));
        }
        if (hp < 0) hp = 0.f;
        Keyboard keyboard= getOwnerArea().getKeyboard();

        moveIfPressed(Orientation.LEFT, keyboard.get(Keyboard.LEFT));
        moveIfPressed(Orientation.UP, keyboard.get(Keyboard.UP));
        moveIfPressed(Orientation.RIGHT, keyboard.get(Keyboard.RIGHT));
        moveIfPressed(Orientation.DOWN, keyboard.get(Keyboard.DOWN));
//        resetIfPressed(keyboard.get(Keyboard.R));
        // TODO: 06.12.22 pk on pne peut pas appeler get ici (static?) alors que la methode est implementée comme move if pressed

        super.update(deltaTime);


    }

    private void resetIfPressed(Button b) {
        if(b.isDown()) {
            // TODO: 06.12.22 voir si on peut ecrire ca autrement
//            currentRoom = new Level0Room(new DiscreteCoordinates(0,0));
//            DiscreteCoordinates coords = new DiscreteCoordinates(2,2);
//            player = new ICRoguePlayer(currentRoom, Orientation.UP, coords, currentRoom.getTitle());
//            currentRoom.registerActor(player);


        }
    }

    /**
     * Orientate and Move this player in the given orientation if the given button is down
     * @param orientation (Orientation): given orientation, not null
     * @param b (Button): button corresponding to the given orientation, not null
     */
    private void moveIfPressed(Orientation orientation, Button b){
        if(b.isDown()) {
            if (!isDisplacementOccurs()) {
                orientate(orientation);
                move(MOVE_DURATION);
            }
        }
    }

    /**
     * Leave an area by unregister this player
     */
    public void leaveArea(){
        getOwnerArea().unregisterActor(this);
    }

    /**
     *
     * @param area (Area): initial area, not null
     * @param position (DiscreteCoordinates): initial position, not null
     */
    public void enterArea(Area area, DiscreteCoordinates position){
        area.registerActor(this);
        area.setViewCandidate(this);
        setOwnerArea(area);
        setCurrentPosition(position.toVector());
        resetMotion();
    }

    @Override
    // TODO: 06.12.22 verifier switch
    public void draw(Canvas canvas) {
        switch (getOrientation()){
            case UP:
                upSprite.draw(canvas);
                break;
            case DOWN:
                downSprite.draw(canvas);
                break;
            case RIGHT:
                rightSprite.draw(canvas);
                break;
            case LEFT:
                leftSprite.draw(canvas);
                break;
        }
        message.draw(canvas);
    }

    public boolean isWeak() {
        return (hp <= 0.f);
    }

    public void strengthen() {
        hp = 10;
    }

    @Override
    public List<DiscreteCoordinates> getCurrentCells() {
        return Collections.singletonList(getCurrentMainCellCoordinates());
    }

    @Override
    public boolean takeCellSpace() {
        return true;
    }

    @Override
    public boolean isCellInteractable() {
        return true;
    }

    @Override
    public boolean isViewInteractable() {
        return true;
    }

    @Override
    public void acceptInteraction(AreaInteractionVisitor v, boolean isCellInteraction) {

    }
}
