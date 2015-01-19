/**
 * This class represents a SpecialtyDrink, which is a Drink.
 * 
 * Overriding - setRefill(boolean) is overridden in this class
 *
 * @author Matthew Hino and Reece Teramoto
 * @version April 9, 2014
 **/
public class SpecialtyDrink extends Drink
{
    // Instance variable(s)
    private String pairedDish; //a dish that goes well with the drink

    /**
     * Default constructor for SpecialtyDrink
     */
    public SpecialtyDrink()
    {
        super();
        super.setRefill(false); //specialty drinks don't get free refills
        pairedDish = "";
    }

    /**
     * Constructor for SpecialtyDrink
     * 
     * @param   name, description, price, freeRefill, pairedDish
     */
    public SpecialtyDrink(String name, String description, double price, String pairedDish)
    {
        super(name, description, price, false); //sets freeRefill to false
        this.pairedDish = pairedDish;
    }

    /**
     * Specialty drinks do not get refills, so do nothing when trying to set refill 
     *   (override parent's method)
     * 
     **/
    public void setRefill(boolean status)
    {
        //do nothing since specialty drinks do not get refills
    }

    /**
     * toString for SpecialtyDrink - This method returns a String that represents a 
     *   SpecialtyDrink (name, price, no refill, description)
     **/
    public String toString()
    {
        String okRefill = "No Free Refill";
        
        String itemDescription = this.getName() + "\t\t\t$" + this.getPrice() + "\t" + okRefill + "\nDescription: " + this.getDescription();
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
        if( this.getName().equalsIgnoreCase(other.getName()) )
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
