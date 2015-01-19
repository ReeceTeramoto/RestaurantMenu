/**
 * This class represents an Appetizer, which is a MenuItem.
 *
 * @author Matthew Hino and Reece Teramoto
 * @version April 9, 2014
 **/
public class Appetizer extends MenuItem
{
    // recommended serving size
    public static final int ONETWO = 0;
    public static final int THREEFOUR = 1;
    public static final int FIVESIX = 2;
    
    // Instance variable(s)
    private int servingSize;

    /**
     * Default constructor for appetizer
     */
    public Appetizer ()
    {
        super();
        servingSize = 0;
    }

    /**
     * Constructor for appetizer
     * 
     * @param name, description, price, serving size
     */
    public Appetizer(String name, String description, double price, int initServingSize )
    {
        super(name, description, price);
        this.servingSize = initServingSize;
    }

    /**
     * Returns the suggested serving size as a string instead of the defualt int
     * 
     * @return  serving size
     **/
    public String getServingSize()
    {
        String recommendedServingSize;
        if(this.servingSize == ONETWO)
        {
            recommendedServingSize = "1-2";
        }
        else if (this.servingSize == THREEFOUR)
        {
            recommendedServingSize = "3-4";
        }
        else
        {
            recommendedServingSize = "5-6";
        }
        return recommendedServingSize;
    }

    /**
     *  Sets the serving size to a new value
     *  
     *  @param  newServingSize
     **/
    public void setServingSize(int newServingSize)
    {
        this.servingSize = newServingSize;
    }

    /**
     * toString for Appetizer
     * 
     * @return  string representation of Appetizer (name, price, description, serving size)
     */
    public String toString()
    {
        return (this.getName() + "\t\t\t$" + this.getPrice() + "\nDescription: " + this.getDescription() + "\tServing Size: " 
                    + this.getServingSize());
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


