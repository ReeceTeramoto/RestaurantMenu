/**
 * This class represents a Dessert, which is a MenuItem.
 *
 * @author Matthew Hino and Reece Teramoto
 * @version April 9, 2014
 **/
public class Dessert extends MenuItem
{
    // Instance variable
    private int calorieCount;

    /**
     * Default constructor for Dessert
     */
    public Dessert()
    {
        super();
        calorieCount = 0;
    }

    /**
     * Constructor for Dessert
     * 
     * @param   name, description, price, calorie count
     */
    public Dessert( String name, String description, double price, int initCalCount )
    {
        super(name, description, price);
        this.calorieCount = initCalCount;
    }

    /**
     * This method returns the calorieCount
     * 
     * @return   calorie count
     **/
    public int getCalCount()
    {
        return calorieCount;
    }

    /**
     * toString for Dessert
     * 
     * @return   string representation of Dessert (name, price, calorie count, description)
     **/
    public String toString()
    {
        String itemDescription = this.getName() + "\t\t\t$" + this.getPrice() + " Calories: " + this.getCalCount() 
            + "\n" + "Description: " + this.getDescription();
        return itemDescription;
    }

    /**
     * equals for Dessert
     * returns true if another MenuItem has the same name
     * @param   other menu item
     */
    public boolean equals(MenuItem otherMenuItem)
    {
        boolean equals;
        if( this.getName().equalsIgnoreCase(otherMenuItem.getName()) )
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
