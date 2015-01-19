import java.util.*;
import java.awt.*;
import javax.swing.*;
import java.io.*;
/**
 * This class creates a GUI that allows users to input data to create objects of type MenuItem
 * 
 * Overriding - done in setRefill method in SpecialtyDrink class
 * 
 * Known Errors - Compiler Warning: the program "uses unchecked or unsafe operations. Recompile with -Xlint:unchecked for details"
 *          (the program still runs fine)
 * 
 * NOTE: The basic skeleton of the GUI was created using the program NetBeans IDE 8.0 (http://www.netbeans.org)
 * However, implementation of all of the fields and buttons were done by us. The methods initComponents and below 
 * were created by NetBeans. Comments at the beginning of initComponents signify this.
 * 
 * @author Matthew Hino and Reece Teramoto
 * @version April 14, 2014
 */
public class Menu extends javax.swing.JFrame {
    private ArrayList<MenuItem> menuItems = new ArrayList<MenuItem>(); //array list of menu items
    private Vector<String> menuItemNameVector = new Vector<String>(1, 1); //list of names to display on left side of screen
    private final int APPETIZER = 0;
    private final int ENTREE = 1;
    private final int DESSERT = 2;
    private final int DRINK = 3;

    /**
     * Creates new form Menu
     */
    public Menu() {
        initComponents();
    }

    /**
     * Implementation for the "Clear" button sets everything to default values
     */
    private void clearAllFields() {
        itemNameTextField.setText("Item Name");
        itemPriceTextField.setText("Price(dollars)");
        appetizerServingSizeButtonGroup.clearSelection();
        dessertCalTextField.setText("");
        dessertPairingTextField.setText("Pairing");
        entreeHalfPriceTextField.setText("Half-Price");
        normalDrinkOrSpecialtyDrinkButtonGroup.clearSelection();
        itemTypeComboBox.setSelectedIndex(0);
        entreeTypeComboBox.setSelectedIndex(0);
        dessertCalLabel.setText("Calories:");
        descriptionTextArea.setText("");
        //sets everything to default
    }

    /**
     * Called when "Add" is pressed. 
     */
    private void addItem() {//GEN-FIRST:event_clearButtonActionPerformed
        if (validItem()) //checks if name, price, and description are OK
        {
            int itemType = itemTypeComboBox.getSelectedIndex(); //checks to see what kind of object to make based on drop-down menu

            if (itemType == APPETIZER && checkAppetizer()) //user has chosen Appetizer. check to make sure a serving size is checked
            {
                makeAppetizer(); //create the new appetizer
                JOptionPane.showMessageDialog(null, "Done."); //confirmation
                clearAllFields(); //clear
            }
            else if (itemType == ENTREE && checkEntree()) //user has chosen Entree. check to make sure a Mini-Price is filled in
            {
                makeEntree(); //create new Entree
                JOptionPane.showMessageDialog(null, "Done.");
                clearAllFields();
            }
            else if (itemType == DESSERT && checkDessert()) //user has chosen Dessert. make sure calorie count is filled in
            {
                makeDessert(); //create new Dessert
                JOptionPane.showMessageDialog(null, "Done.");
                clearAllFields();
            }
            else if (itemType == DRINK && checkDrink()) //user has chosen Drink. make sure Normal Drink" or "Specialty Drink" is chosen
            {
                if (specialtyDrinkButton.isSelected() && checkSpecialtyDrink()) //user wants to make a Specialty Drink; make sure
                                                                                //that a paired dish is entered
                {
                    makeSpecialtyDrink(); //make a Specialty Drink
                    JOptionPane.showMessageDialog(null, "Done.");
                    clearAllFields();
                }
                if (normalDrinkButton.isSelected()) //user wants to make a normal drink
                {
                    makeDrink(); //make a Normal Drink
                    JOptionPane.showMessageDialog(null, "Done.");
                    clearAllFields();
                }
            }       

        }
    }//GEN-LAST:event_clearButtonActionPerformed

    /**
     * Checks to make sure the name, price, and description of an added item is OK
     * 
     * @return  true if name, price, and description are valid
     */
    private boolean validItem()
    {
        boolean result = true;

        String name = itemNameTextField.getText().trim();
        String description = descriptionTextArea.getText().trim();
        String priceString = itemPriceTextField.getText().trim();
        double price = 0;

        try //try to parse the price as a double
        {
            price = Double.parseDouble(priceString);
        }
        catch (NumberFormatException ex) //error if price is not a valid double
        {
            result = false;
            JOptionPane.showMessageDialog(null, "Price input is invalid.");
        }

        //price cannot be negative
        if (price <= 0) 
        {
            result = false;
            JOptionPane.showMessageDialog(null, "Price must be a positive number.");
        }

        //name cannot be the default or an empty string
        if (name.equals("") || name.equals("Item Name"))
        {
            result = false;
            JOptionPane.showMessageDialog(null, "Name is invalid.");
        }

        //user is not allowed to have two items of the same name
        for (int i = 0; i < menuItems.size(); ++i)
        {
            if (menuItems.get(i).getName().equalsIgnoreCase(name))
            {
                result = false;
                JOptionPane.showMessageDialog(null, "Cannot have two of the same item.");
            }
        }

        //must have a description
        if (description.equals(""))
        {
            result = false;
            JOptionPane.showMessageDialog(null, "Description is invalid.");
        }

        return result;
    }

    /**
     * Checks to make sure an appetizer is valid
     * 
     * @return  true if a serving size option is chosen
     */
    private boolean checkAppetizer()
    {
        boolean result = true;

        //one of the serving size options must be selected
        if (!servingSize1.isSelected() && !servingSize2.isSelected() && !servingSize3.isSelected())
        {
            result = false;
            JOptionPane.showMessageDialog(null, "Plese check a Serving Size option.");
        }

        return result;
    }

    /**
     * Makes an appetizer
     */
    private void makeAppetizer()
    {
        String name = itemNameTextField.getText().trim();
        double price = Double.parseDouble(itemPriceTextField.getText().trim());
        String description = descriptionTextArea.getText().trim();
        int servingSize;

        //decide what serving size to use
        if (servingSize1.isSelected())
        {
            servingSize = Appetizer.ONETWO;
        }
        else if (servingSize2.isSelected())
        {
            servingSize = Appetizer.THREEFOUR;
        }
        else 
        {
            servingSize = Appetizer.FIVESIX;
        }

        //call Appetizer constructor with parameters
        menuItems.add(new Appetizer(name, description, price, servingSize));
        //add the name to the name vector to be displayed on left
        menuItemNameVector.add(name);
        //update the left column
        menuItemList.setListData(menuItemNameVector);
    }

    /**
     * Checks to make sure an entree is valid
     * 
     * @return  true if a mini-price is given  
     */
    private boolean checkEntree()
    {
        boolean result = true;
        double miniPrice = 0;
        String priceString = entreeHalfPriceTextField.getText().trim();
        double price = Double.parseDouble(itemPriceTextField.getText().trim());
        
        //try to parse the mini-price as a double
        try
        {
            miniPrice = Double.parseDouble(priceString);
        }
        catch (NumberFormatException ex) //mini-price is not a valid double
        {
            result = false;
            JOptionPane.showMessageDialog(null, "Mini-Price input is invalid.");
        }

        //mini-price must be less than price
        if (miniPrice >= price)
        {
            result = false;
            JOptionPane.showMessageDialog(null, "Mini-Price must be less than Price.");
        }

        return result;
    }

    /**
     * Makes an entree
     */
    private void makeEntree()
    {
        String name = itemNameTextField.getText().trim();
        double price = Double.parseDouble(itemPriceTextField.getText().trim());
        String description = descriptionTextArea.getText().trim();
        double miniPrice = Double.parseDouble(entreeHalfPriceTextField.getText().trim());;
        int entreeType = entreeTypeComboBox.getSelectedIndex(); //gets the entree type (Chicken, Fish, or Meat)

        //add an Entree to the menuItems array list
        menuItems.add(new Entree(name, description, price, miniPrice, entreeType));
        //add name to name vector
        menuItemNameVector.add(name);
        //update left column
        menuItemList.setListData(menuItemNameVector);
    }

    /**
     * Checks to make sure a dessert is valid
     * 
     * @return  true if calorie count is filled out
     */
    private boolean checkDessert()
    {
        boolean result = true;
        String calorieString = dessertCalTextField.getText().trim();
        int calories = 0;

        try //calorie input must be an integer
        {
            calories = Integer.parseInt(calorieString);
        }
        catch (NumberFormatException ex)
        {
            result = false;
            JOptionPane.showMessageDialog(null, "Calorie value is not valid.");
        }

        if (calories < 0) //you can't have negative calories
        {
            result = false;
            JOptionPane.showMessageDialog(null, "Calorie value must be positive.");
        }

        return result;
    }

    /**
     * Makes a dessert
     */
    private void makeDessert()
    {
        String name = itemNameTextField.getText().trim();
        double price = Double.parseDouble(itemPriceTextField.getText().trim());
        String description = descriptionTextArea.getText().trim();
        int calorieCount = Integer.parseInt(dessertCalTextField.getText());

        //add a new Dessert to the menuItems array list
        menuItems.add(new Dessert(name, description, price, calorieCount));
        //add the name to the name vector
        menuItemNameVector.add(name);
        //update left column
        menuItemList.setListData(menuItemNameVector);
    }

    /**
     * Checks to make sure a drink is valid
     * 
     * @return  true if "Normal Drink" or "Specialy Drink" is selected
     */
    private boolean checkDrink()
    {
        boolean result = false;
        //something must be selected under Drink
        if (specialtyDrinkButton.isSelected() || normalDrinkButton.isSelected()) 
        {
            return true;
        }

        return result;
    }

    /**
     * Checks to make sure a Specialty Drink is valid (paired dish must not be empty string or default)
     * 
     * @return  true if paired dish is filled out properly
     */
    private boolean checkSpecialtyDrink()
    {
        boolean result = true;
        String pairing = dessertPairingTextField.getText().trim();
        
        //pairing cannot be empty string or default
        if (pairing.equals("") || pairing.equals("Pairing"))
        {
            result = false;
            JOptionPane.showMessageDialog(null, "You must specify a paired dish.");
        }

        return result;
    }

    /**
     * Makes a Specialty Drink
     */
    private void makeSpecialtyDrink()
    {
        String name = itemNameTextField.getText().trim();
        double price = Double.parseDouble(itemPriceTextField.getText().trim());
        String description = descriptionTextArea.getText().trim();
        String pairing = dessertPairingTextField.getText().trim();
        
        //add a new Specialty Drink to the menuItems array list
        menuItems.add(new SpecialtyDrink(name, description, price, pairing));
        //add name to name vector
        menuItemNameVector.add(name);
        //update left column
        menuItemList.setListData(menuItemNameVector);
    }

    /**
     * Makes a Drink
     */
    private void makeDrink()
    {
        String name = itemNameTextField.getText().trim();
        double price = Double.parseDouble(itemPriceTextField.getText().trim());
        String description = descriptionTextArea.getText().trim();

        //asks the user if the drink has a free refill or not
        int refillOption = JOptionPane.showConfirmDialog(null, "Does this drink have a free refill? If you close this window, that means 'No'",
                "Free Refill?", JOptionPane.YES_NO_OPTION);
        boolean refill = false;
        if (refillOption == JOptionPane.YES_OPTION) //if the drink has a free refill
        {
            refill = true;
        }
        else if (refillOption == JOptionPane.NO_OPTION) //if the drink has no free refill
        {
            refill = false;
        }
        else if (refillOption == JOptionPane.CLOSED_OPTION)
        {
            refill = false;
        }

        //create a new Drink in the menuItems array list
        menuItems.add(new Drink(name, description, price, refill));
        //add name to name vector
        menuItemNameVector.add(name);
        //update left column
        menuItemList.setListData(menuItemNameVector);
    }

    /**
     * Called when "Print Menu" is pressed
     */
    private void printMenu(java.awt.event.ActionEvent evt) 
    {
        File myFile = new File("Menu.txt"); //save everything to a text file
        PrintWriter pw = null;

        try //catch FileNotFound exception
        {
            pw = new PrintWriter(myFile);
        }
        catch (FileNotFoundException ex)
        {
            System.exit(0);
        }

        
        //Go through the array list menuItems and call the toString methods
        //Display all appetizers
        System.out.println("~~~~~~APPETIZERS~~~~~~~");
        pw.println("~~~~~~APPETIZERS~~~~~~~");
        for (int i = 0; i < menuItems.size(); ++i)
        {
            if (menuItems.get(i) instanceof Appetizer)
            {
                System.out.println(menuItems.get(i).toString());
                pw.println(menuItems.get(i).toString());
            }
        }

        //display all entrees
        System.out.println("\n\n~~~~~~ENTREES~~~~~~~");
        pw.println("\r\n\r\n" + "~~~~~~ENTREES~~~~~~~");
        for (int i = 0; i < menuItems.size(); ++i)
        {
            if (menuItems.get(i) instanceof Entree)
            {
                System.out.println(menuItems.get(i).toString());
                pw.println(menuItems.get(i).toString());
            }
        }

        //display all desserts
        System.out.println("\n\n~~~~~~DESSERTS~~~~~~~");
        pw.println("\r\n\r\n" + "~~~~~~DESSERTS~~~~~~~");
        for (int i = 0; i < menuItems.size(); ++i)
        {
            if (menuItems.get(i) instanceof Dessert)
            {
                System.out.println(menuItems.get(i).toString());
                pw.println(menuItems.get(i).toString());
            }
        }

        //display all normal drinks
        System.out.println("\n\n~~~~~~NORMAL DRINKS~~~~~~~");
        pw.println("\r\n\r\n" + "~~~~~~NORMAL DRINKS~~~~~~~");
        for (int i = 0; i < menuItems.size(); ++i)
        {
            if (menuItems.get(i) instanceof Drink && !(menuItems.get(i) instanceof SpecialtyDrink))
            {
                System.out.println(menuItems.get(i).toString());
                pw.println(menuItems.get(i).toString());
            }
        }

        //display all Specialty Drinks
        System.out.println("\n\n~~~~~~SPECIALTY DRINKS~~~~~~~");
        pw.println("\r\n\r\n" + "~~~~~~SPECIALTY DRINKS~~~~~~~");
        for (int i = 0; i < menuItems.size(); ++i)
        {
            if (menuItems.get(i) instanceof SpecialtyDrink)
            {
                System.out.println(menuItems.get(i).toString());
                pw.println(menuItems.get(i).toString());
            }
        }

        pw.close(); //close the text file
    }//GEN-LAST:event_clearButtonActionPerformed

    /**
    * THE METHODS BELOW, INCLUDING THIS ONE, HAVE BEEN CREATED BY NETBEANS IDE 8.0 (http://www.netbeans.org)
    * 
    * This method is called from within the constructor to initialize the form.
    * WARNING: Do NOT modify this code. The content of this method is always
    * regenerated by the Form Editor.
    * 
    * 
    */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        appetizerServingSizeButtonGroup = new javax.swing.ButtonGroup();
        normalDrinkOrSpecialtyDrinkButtonGroup = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        menuItemList = new javax.swing.JList();
        printMenuButton = new javax.swing.JButton();
        addItemButton = new javax.swing.JButton();
        clearButton = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        itemTypeLabel = new javax.swing.JLabel();
        itemTypeComboBox = new javax.swing.JComboBox();
        itemNameLabel = new javax.swing.JLabel();
        itemNameTextField = new javax.swing.JTextField();
        itemPriceLabel = new javax.swing.JLabel();
        itemPriceTextField = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        descriptionTextArea = new javax.swing.JTextArea();
        itemDescriptionLabel = new javax.swing.JLabel();
        appetizerLayeredPane = new javax.swing.JLayeredPane();
        appetizerPanel = new javax.swing.JPanel();
        servingSize1 = new javax.swing.JRadioButton();
        servingSize2 = new javax.swing.JRadioButton();
        servingSize3 = new javax.swing.JRadioButton();
        servingSizeLabel = new javax.swing.JLabel();
        appetizerLabel = new javax.swing.JLabel();
        entreeLayeredPane = new javax.swing.JLayeredPane();
        entreePanel = new javax.swing.JPanel();
        entreeTypeLabel = new javax.swing.JLabel();
        entreeTypeComboBox = new javax.swing.JComboBox();
        entreeHalfPriceLabel = new javax.swing.JLabel();
        entreeHalfPriceTextField = new javax.swing.JTextField();
        entreeLabel = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        dessertCalTextField = new javax.swing.JTextField();
        dessertCalLabel = new javax.swing.JLabel();
        dessertLabel = new javax.swing.JLabel();
        drinkPanel = new javax.swing.JPanel();
        dessertPairingLabel = new javax.swing.JLabel();
        dessertPairingTextField = new javax.swing.JTextField();
        normalDrinkButton = new javax.swing.JRadioButton();
        specialtyDrinkButton = new javax.swing.JRadioButton();
        drinkLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jScrollPane2.setViewportView(menuItemList);

        printMenuButton.setText("Print Menu");
        printMenuButton.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    printMenuButtonActionPerformed(evt);
                }
            });

        addItemButton.setText("Add");
        addItemButton.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    addItemButtonActionPerformed(evt);
                }
            });

        clearButton.setText("Clear");
        clearButton.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {

                    clearButtonActionPerformed(evt);
                }
            });

        jPanel6.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        itemTypeLabel.setText("Item Type:");

        itemTypeComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Appetizer", "Entree", "Dessert", "Drink" }));
        itemTypeComboBox.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    itemTypeComboBoxActionPerformed(evt);
                }
            });

        itemNameLabel.setText("Name:");

        itemNameTextField.setText("Item Name");

        itemPriceLabel.setText("Price:");

        itemPriceTextField.setText("Price(dollars)");

        descriptionTextArea.setColumns(20);
        descriptionTextArea.setLineWrap(true);
        descriptionTextArea.setRows(5);
        descriptionTextArea.setDoubleBuffered(true);
        jScrollPane1.setViewportView(descriptionTextArea);

        itemDescriptionLabel.setText("Description (80 Char Max):");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 347, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(itemTypeLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(itemTypeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(itemNameLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(itemNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(itemPriceLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(itemPriceTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(itemDescriptionLabel))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(itemTypeLabel)
                    .addComponent(itemNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(itemNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(itemTypeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(itemPriceLabel)
                    .addComponent(itemPriceTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(itemDescriptionLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        appetizerPanel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        appetizerServingSizeButtonGroup.add(servingSize1);
        servingSize1.setText("1-2");

        appetizerServingSizeButtonGroup.add(servingSize2);
        servingSize2.setText("3-4");

        appetizerServingSizeButtonGroup.add(servingSize3);
        servingSize3.setText("5-6");

        servingSizeLabel.setText("Serving Size:");

        appetizerLabel.setText("Appetizer");

        javax.swing.GroupLayout appetizerPanelLayout = new javax.swing.GroupLayout(appetizerPanel);
        appetizerPanel.setLayout(appetizerPanelLayout);
        appetizerPanelLayout.setHorizontalGroup(
            appetizerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(appetizerPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(appetizerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(servingSize1)
                    .addComponent(servingSize2)
                    .addComponent(servingSize3)
                    .addComponent(appetizerLabel))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, appetizerPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(servingSizeLabel)
                .addContainerGap())
        );
        appetizerPanelLayout.setVerticalGroup(
            appetizerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(appetizerPanelLayout.createSequentialGroup()
                .addComponent(appetizerLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(servingSizeLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(servingSize1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(servingSize2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(servingSize3)
                .addGap(22, 22, 22))
        );

        javax.swing.GroupLayout appetizerLayeredPaneLayout = new javax.swing.GroupLayout(appetizerLayeredPane);
        appetizerLayeredPane.setLayout(appetizerLayeredPaneLayout);
        appetizerLayeredPaneLayout.setHorizontalGroup(
            appetizerLayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(appetizerLayeredPaneLayout.createSequentialGroup()
                .addComponent(appetizerPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        appetizerLayeredPaneLayout.setVerticalGroup(
            appetizerLayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(appetizerPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        appetizerLayeredPane.setLayer(appetizerPanel, javax.swing.JLayeredPane.DEFAULT_LAYER);

        entreePanel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        entreeTypeLabel.setText("Type:");

        entreeTypeComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Chicken", "Fish", "Meat" }));
        entreeTypeComboBox.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    entreeTypeComboBoxActionPerformed(evt);
                }
            });

        entreeHalfPriceLabel.setText("Mini-Price:");

        entreeHalfPriceTextField.setText("Mini-Price");

        entreeLabel.setText("Entree");

        javax.swing.GroupLayout entreePanelLayout = new javax.swing.GroupLayout(entreePanel);
        entreePanel.setLayout(entreePanelLayout);
        entreePanelLayout.setHorizontalGroup(
            entreePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(entreePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(entreePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(entreePanelLayout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(entreeTypeLabel))
                    .addGroup(entreePanelLayout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(entreeHalfPriceTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(entreeTypeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(entreeHalfPriceLabel)
                    .addComponent(entreeLabel))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        entreePanelLayout.setVerticalGroup(
            entreePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(entreePanelLayout.createSequentialGroup()
                .addComponent(entreeLabel)
                .addGap(18, 18, 18)
                .addComponent(entreeTypeLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(entreeTypeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(entreeHalfPriceLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(entreeHalfPriceTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout entreeLayeredPaneLayout = new javax.swing.GroupLayout(entreeLayeredPane);
        entreeLayeredPane.setLayout(entreeLayeredPaneLayout);
        entreeLayeredPaneLayout.setHorizontalGroup(
            entreeLayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(entreeLayeredPaneLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(entreePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        entreeLayeredPaneLayout.setVerticalGroup(
            entreeLayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(entreePanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        entreeLayeredPane.setLayer(entreePanel, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLabel1.setText("Please fill out the coresponding section below, based on the \"Item Type\" above");

        jPanel4.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        dessertCalTextField.setText("Calories");

        dessertCalLabel.setText("Calories:");

        dessertLabel.setText("Dessert");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(dessertCalTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(dessertCalLabel)
                    .addComponent(dessertLabel))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(dessertLabel)
                .addGap(18, 18, 18)
                .addComponent(dessertCalLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(dessertCalTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        drinkPanel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        dessertPairingLabel.setText("Pairing:");

        dessertPairingTextField.setText("Pairing");

        normalDrinkOrSpecialtyDrinkButtonGroup.add(normalDrinkButton);
        normalDrinkButton.setText("Normal Drink");
        normalDrinkButton.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    normalDrinkButtonActionPerformed(evt);
                }
            });

        normalDrinkOrSpecialtyDrinkButtonGroup.add(specialtyDrinkButton);
        specialtyDrinkButton.setText("Specialty Drink");
        specialtyDrinkButton.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    specialtyDrinkButtonActionPerformed(evt);
                }
            });

        drinkLabel.setText("Drink");

        javax.swing.GroupLayout drinkPanelLayout = new javax.swing.GroupLayout(drinkPanel);
        drinkPanel.setLayout(drinkPanelLayout);
        drinkPanelLayout.setHorizontalGroup(
            drinkPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(drinkPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(drinkPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(drinkPanelLayout.createSequentialGroup()
                        .addComponent(specialtyDrinkButton)
                        .addGap(0, 16, Short.MAX_VALUE))
                    .addGroup(drinkPanelLayout.createSequentialGroup()
                        .addGroup(drinkPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(normalDrinkButton)
                            .addGroup(drinkPanelLayout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addGroup(drinkPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(dessertPairingLabel)
                                    .addComponent(dessertPairingTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(drinkLabel))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        drinkPanelLayout.setVerticalGroup(
            drinkPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, drinkPanelLayout.createSequentialGroup()
                .addComponent(drinkLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(normalDrinkButton)
                .addGap(4, 4, 4)
                .addComponent(specialtyDrinkButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(dessertPairingLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(dessertPairingTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(appetizerLayeredPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(entreeLayeredPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(drinkPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jLabel1)))))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(printMenuButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(clearButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(addItemButton)))
                .addGap(0, 9, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(33, 33, 33)
                        .addComponent(jLabel1)
                        .addGap(30, 30, 30)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(appetizerLayeredPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(entreeLayeredPane)
                            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(drinkPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(jScrollPane2))
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(printMenuButton)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(addItemButton)
                        .addComponent(clearButton)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void specialtyDrinkButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_specialtyDrinkButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_specialtyDrinkButtonActionPerformed

    private void normalDrinkButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_normalDrinkButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_normalDrinkButtonActionPerformed

    private void itemTypeComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemTypeComboBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_itemTypeComboBoxActionPerformed

    private void entreeTypeComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_entreeTypeComboBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_entreeTypeComboBoxActionPerformed

    private void clearButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearButtonActionPerformed
        clearAllFields();
    }//GEN-LAST:event_clearButtonActionPerformed

    private void addItemButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearButtonActionPerformed
        addItem();
    }//GEN-LAST:event_clearButtonActionPerformed

    private void printMenuButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearButtonActionPerformed
        printMenu(evt);
    }//GEN-LAST:event_clearButtonActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
                public void run() {
                    new Menu().setVisible(true);
                }
            });

        /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
         * EDITS BY MATTHEW HINO AND REECE TERAMOTO BEGIN HERE
         *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
         */ 

    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addItemButton;
    private javax.swing.JLabel appetizerLabel;
    private javax.swing.JLayeredPane appetizerLayeredPane;
    private javax.swing.JPanel appetizerPanel;
    private javax.swing.ButtonGroup appetizerServingSizeButtonGroup;
    private javax.swing.JButton clearButton;
    private javax.swing.JLabel dessertCalLabel;
    private javax.swing.JTextField dessertCalTextField;
    private javax.swing.JLabel dessertLabel;
    private javax.swing.JLabel dessertPairingLabel;
    private javax.swing.JTextField dessertPairingTextField;
    private javax.swing.JLabel drinkLabel;
    private javax.swing.JPanel drinkPanel;
    private javax.swing.JLabel entreeHalfPriceLabel;
    private javax.swing.JTextField entreeHalfPriceTextField;
    private javax.swing.JLabel entreeLabel;
    private javax.swing.JLayeredPane entreeLayeredPane;
    private javax.swing.JPanel entreePanel;
    private javax.swing.JComboBox entreeTypeComboBox;
    private javax.swing.JLabel entreeTypeLabel;
    private javax.swing.JRadioButton normalDrinkButton;
    private javax.swing.ButtonGroup normalDrinkOrSpecialtyDrinkButtonGroup;
    private javax.swing.JLabel itemDescriptionLabel;
    private javax.swing.JLabel itemNameLabel;
    private javax.swing.JTextField itemNameTextField;
    private javax.swing.JLabel itemPriceLabel;
    private javax.swing.JTextArea descriptionTextArea;
    private javax.swing.JTextField itemPriceTextField;
    private javax.swing.JComboBox itemTypeComboBox;
    private javax.swing.JLabel itemTypeLabel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JList menuItemList;
    private javax.swing.JButton printMenuButton;
    private javax.swing.JRadioButton servingSize1;
    private javax.swing.JRadioButton servingSize2;
    private javax.swing.JRadioButton servingSize3;
    private javax.swing.JLabel servingSizeLabel;
    private javax.swing.JRadioButton specialtyDrinkButton;
    // End of variables declaration//GEN-END:variables
}