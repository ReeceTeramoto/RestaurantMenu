
/**
 * Abstract class MenuItem - holds the menu item's name, description, and price
 *                          and methods to set and get these attributes
 * 
 * @author Matthew Hino and Reece Teramoto
 * @version April 9, 2014
 */
public abstract class MenuItem
{
    // instance variables
    private String name;
    private String description;
    private double price;
    
    /**
     * Default constructor for MenuItem
     */
    public MenuItem()
    {
        //sets everything to default values
        name = "";
        description = "";
        price = 0.0;
    }
    
    /**
     * Constructor for MenuItem
     * 
     * @param name, description, price 
     */
    public MenuItem(String newName, String newDescrip, double newPrice)
    {
        //sets name, description, and price equal to the arguments
        this.name = newName;
        this.description = newDescrip;
        this.price = newPrice;
    }

    /**
     * Gets the name of the MenuItem
     * 
     * @return        name of menu item
     */
    public String getName()
    {
        //returns name of menu item
        return name;
    }
    
    /**
     * Gets the description of the MenuItem
     * 
     * @return        description of menu item
     */
    public String getDescription()
    {
        //returns description of menu item
        return description;
    }
    
    /**
     * Gets the price of the MenuItem
     * 
     * @return        price of menu item
     */
    public double getPrice()
    {
        //returns price of menu item
        return price;
    }
    
    /**
     * Sets the name of the MenuItem
     * 
     * @param        name of menu item
     */
    public void setName(String newName)
    {
        //sets name of menu item
        name = newName;
    }
    
    /**
     * Sets the description of the MenuItem
     * 
     * @param        description of menu item
     */
    public void setDescription(String newDescription)
    {
        //sets the description of menu item
        description = newDescription;
    }
    
    /**
     * Sets the price of the MenuItem
     * 
     * @param        price of menu item
     */
    public void setDescription(int newPrice)
    {
        //sets the price of menu item
        price = newPrice;
    }
    
    /**
     * toString for MenuItem
     */
    public String toString()
    {
        String itemDescription = name + "\t\t\t" + price+ "\n" + description;
        return itemDescription;
    }
    
    /**
     * equals for MenuItem
     * 
     * @param   other menu item
     */
    public boolean equals(MenuItem otherMenuItem)
    {
        boolean equals;
        if( this.name.equalsIgnoreCase(otherMenuItem.getName()) )
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
