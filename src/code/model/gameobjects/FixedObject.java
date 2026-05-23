package code.model.gameobjects;

import code.model.Point;
import code.model.context.GameContext;

/** Modella i muri e i pavimenti delle stanze */
public class FixedObject extends GameObject
{
	/** Il tipo di oggetto fisso */
	private Type type;
	
	/** Enumerazione per il tipo di {@link FixedObject} */
	public enum Type 
	{ WALL, FLOOR }
	
	/**
	 * Costruice la classe
	 * @param type
	 * il tipo di oggetto fisso
	 * @param point
	 * la posizione
	 * @param width
	 * la larghezza
	 * @param height
	 * l'altezza
	 */
    public FixedObject(Type type, Point point, int width, int height)
    {
        super(point, width, height);
        this.type = type;
    }
    
    /**
     * Copia un {@link FixedObject} in una posizione diversa
     * @param source
     * il {@link FixedObject} da copiare
     * @param position
     * la nuova posizione
     */
    public FixedObject(FixedObject source, Point position)
    { this(source.type, position, source.getWidth(), source.getHeight()); }
    
    /** 
     * Restituisce il tipo di {@link FixedObject}
     * @return
     * il tipo di {@link FixedObject}
     */
    public Type getType()
    { return type; }

    /** Aggiorna lo stato del {@link FixedObject} (quindi non fa nulla) */
    @Override
    public void update(GameContext context)
    { /*do nothing*/}
}
