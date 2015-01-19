/**
 * This class represents a Drink, which is a MenuItem.
 *
 * @author Matthew Hino and Reece Teramoto
 * @version April 9, 2014
 **/
public class Drink extends MenuItem
{
    // Instance variable(s)
    private boolean freeRefill;

    /**
     * Default constructor for Drink
     */
    public Drink()
    {
        super();
        freeRefill = false;
    }

    /**
     * Constructor for Drink
     * 
     * @param   name, description, price, freeRefill
     */
    public Drink( String name, String description, double price, boolean freeRefill )
    {
        super(name, description, price);
        this.freeRefill = freeRefill;
    }

    /**
     * refillOk is a method that returns if the drink ordered comes with a free refill
     * 
     * @return   true if drink has a free refill, false otherwise
     **/
    public boolean refillOk()
    {
        return freeRefill;
    }

    /**
     *  setRefill is a method that allows a user to change the status of free refills of a drink
     *  
     *  @param   status (true or false)
     **/
    public void setRefill(boolean status)
    {
        //sets freeRefill to true or false, depending on what was passed in
        freeRefill = status;
    }

    /**
     * toString for Drink - This method returns a String that represents a Drink (name, price, refill status, description)
     **/
    public String toString()
    {
        String okRefill;
        if(this.refillOk())
        {
            okRefill = "Free Refill";
        }
        else
        {
            okRefill = "No Free Refill";
        }
        String itemDescription = this.getName() + "\t\t\t$" + this.getPrice() + okRefill + "\nDescription: " + this.getDescription();
        return itemDescription;
    }

    /**
     * This method returns a boolean that is true if another menu item has the same name
     * as this one.
     * 
     * @return   true if two names are the same
     **/public boolean equals(MenuItem other)
    {
        boolean equals;
        if( this.getName().equalsIgnoreCase(other.getName()) ) //if the two names are equal
        {
            equals = true;
        }
        else
        {
            equals = false;
        }
        return equals;

    }
}
