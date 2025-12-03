package model.gameobject;

import java.util.Random;

import model.room.RoomMap;

public class Furniture extends GameObject
{
    public static final int TYPE = RoomMap.FURNITURE_ID;
    public enum LootType{ EMPTY, PUZZLE_PIECE}

    private LootType content;
    private boolean isSearched;

    public Furniture(int x, int y, int w, int h){
        super(new Point(x, y), w, h);
        this.type = TYPE;
        this.isSearched = false;
        assignRandomLoot();
    }

    private void assignRandomLoot(){
        Random rand = new Random();
        if(rand.nextBoolean()) this.content = LootType.PUZZLE_PIECE;
        else this.content = LootType.EMPTY;
    }

    @Override
    public void update(){

    }

    public LootType search(){
        if (isSearched) return LootType.EMPTY;
        isSearched = true;
        return content;
    }

    public LootType getContent() {
        return content;
    }

    public void setContent(LootType content) {
        this.content = content;
    }

    public boolean isSearched() {
        return isSearched;
    }

    public void setSearched(boolean isSearched) {
        this.isSearched = isSearched;
    }
}

