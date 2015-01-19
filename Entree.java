/**
 * This class represents an Entree, which is a MenuItem.
 *
 * @author Matthew Hino and Reece Teramoto
 * @version April 9, 2014
 **/
public class Entree extends MenuItem
{
    // Different Types of 
    public static final int CHICKEN = 0;
    public static final int FISH = 1;
    public static final int MEAT = 2;
    
    // Instance variable(s)
    private double miniPrice;
    private int category;

    /**
     * Default constructor for Entree
     */
    public Entree ()
    {
        super();
        miniPrice = 0;
        category = 0;
    }

    /**
     * Constructor for Entree
     * 
     * @param name, description, price, serving size
     */
    public Entree(String name, String description, double price, double initMPrice, int initCat)
    {
        super(name, description, price);
        this.miniPrice = initMPrice;
        this.category = initCat;
    }

    /**
     * Returns the mini price
     * 
     * @return  mini price
     **/
    public double getMiniPrice()
    {
        return miniPrice;
    }

    /**
     * Returns the category
     * 
     * @return  category
     **/
    public int getCategory()
    {
        return category;
    }

    /**
     *  Sets the mini price to a new value
     *  
     *  @param  newMiniPrice
     **/
    public void setMiniPrice(double newMiniPrice)
    {
        miniPrice = newMiniPrice;
    }

    /**
     * toString for Entree
     * 
     * @return  string representation of Entree (name, price, mini-price, description, category)
     */
    public String toString()
    {
        String cat = "";
        //interpret the category
        if (category == CHICKEN)
        {
            cat = "Chicken";
        }
        else if (category == FISH)
        {
            cat = "Fish";
        }
        else if (category == MEAT)
        {
            cat = "Meat";
        }
        
        String itemDescription = this.getName() + "\t\t\t" + "Full: $" + this.getPrice() + " Mini: $" + this.getMiniPrice() 
            + "\nDescription: "  + this.getDescription() + "\nCategory: " + cat;
        return itemDescription;
    }

    /**
     * equals for Entree
     * 
     * @param   otherEntree
     * @return  true if two entrees are the same
     */
    public boolean equals(MenuItem other)
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
