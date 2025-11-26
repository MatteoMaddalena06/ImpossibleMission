package model.room;

import java.util.List;

//inproject import
import model.GameObject;
import model.Robot;

record RoomPrototype(List<GameObject> gameObjectList, List<Robot> robotList, Room.Color color) {}
