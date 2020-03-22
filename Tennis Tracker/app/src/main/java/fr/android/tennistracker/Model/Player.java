package fr.android.tennistracker.Model;

public class Player {
    public String name;
    private int sets;

    /**
     * Constructor for a Player.
     * Initializes a player to the given name
     * and zero as the number of sets won.
     * @param name Name of the player
     */
    public Player(String name)
    {
        sets = 0;
        this.name = name;
    }

    /**
     * @return Number of sets won
     */
    public int getSet()
    {
        return sets;
    }

    /**
     * @return Name of the player
     */
    public String getName()
    {
        return name;
    }

    /**
     * Increments the number of sets won by 1
     */
    public void winSet()
    {
        sets++;
    }
}
