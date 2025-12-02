package model.gameobject;

import java.util.Random;

public class Furniture extends GameObject
{
    public static final int TYPE_DESK = 5;
    public static final int TYPE_TERMINAL = 6;//ferma solo i robot o resetta le piattaforme
    public static final int TYPE_BOOKSHELF = 7;
    public static final int TYPE_VENDING_MACHINE = 8; //negli altri cerchi i pezzi del puzzle

    public enum LootType{ EMPTY, PUZZLE_PIECE, ROBOT_STOP, PLATFORM_RESET}

    private LootType content;
    private boolean isSearched;

    public Furniture(int type, int x, int y, int w, int h){
        super(type, x, y, w, h);
        this.isSearched = false;
        assignLootBasedOnType();
    }

    @Override
    public void update(){}

    private void assignLootBasedOnType(){
        Random rand = new Random();
        int chance = rand.nextInt(100);

        if(type == TYPE_TERMINAL){
            if(chance < 50) this.content = LootType.ROBOT_STOP;
            else this.content = LootType.PLATFORM_RESET;
        }
        else{
            if (chance < 50) this.content = LootType.EMPTY;
            else this.content = LootType.PUZZLE_PIECE;
        }

    }

    public LootType search(){
        if (isSearched) return LootType.EMPTY;
        isSearched = true;
        return content;
    }

    public static int getTypeDesk() {
        return TYPE_DESK;
    }

    public static int getTypeTerminal() {
        return TYPE_TERMINAL;
    }

    public static int getTypeBookshelf() {
        return TYPE_BOOKSHELF;
    }

    public static int getTypeVendingMachine() {
        return TYPE_VENDING_MACHINE;
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
