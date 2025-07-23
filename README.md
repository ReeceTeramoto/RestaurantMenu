# Restaurant Menu Management System

A GUI application for creating and managing restaurant menu items with different categories including appetizers, entrees, desserts, and drinks. Available in both Java (Swing) and Python (tkinter) versions.

## Project Overview

This application allows users to create, manage, and display restaurant menu items through an intuitive graphical interface. The system supports different types of menu items with specific attributes for each category.

## Features

- **Menu Item Categories**: Support for appetizers, entrees, desserts, and drinks (including specialty drinks)
- **Item Management**: Add, edit, and display menu items with detailed information
- **GUI Interface**: User-friendly Swing-based interface for easy interaction
- **Data Persistence**: Save and load menu data from text files
- **Object-Oriented Design**: Implements inheritance with abstract MenuItem class and specific subclasses

## Project Structure

### Java Version (Original)

The Java version consists of several classes:

- [`MenuItem.java`](MenuItem.java) - Abstract base class for all menu items
- [`Appetizer.java`](Appetizer.java) - Appetizer-specific menu items with serving size
- [`Entree.java`](Entree.java) - Entree menu items with half-price options
- [`Dessert.java`](Dessert.java) - Dessert items with calorie information and pairings
- [`Drink.java`](Drink.java) - Basic drink menu items
- [`SpecialtyDrink.java`](SpecialtyDrink.java) - Specialty drinks with refill options
- [`Menu.java`](Menu.java) - Main GUI application class

### Python Version

The Python version consists of:

- [`menu_item.py`](menu_item.py) - Contains all menu item classes (MenuItem, Appetizer, Entree, Dessert, Drink, SpecialtyDrink)
- [`restaurant_menu_app.py`](restaurant_menu_app.py) - Main GUI application using tkinter
- [`run_menu_app.py`](run_menu_app.py) - Simple launcher script
- [`test_menu_items.py`](test_menu_items.py) - Test script to verify menu item classes work correctly
- [`demo_gui.py`](demo_gui.py) - Demo script to launch the GUI application
- [`requirements.txt`](requirements.txt) - Python dependencies (none required beyond standard library)

## How to Run

### Java Version

1. Ensure you have Java installed on your system
2. Compile all Java files:
   ```bash
   javac *.java
   ```
3. Run the main application:
   ```bash
   java Menu
   ```

### Python Version

1. Ensure you have Python 3.x installed on your system
2. Run the application using one of these methods:
   ```bash
   # Main application
   python3 restaurant_menu_app.py
   
   # Using launcher script
   python3 run_menu_app.py
   
   # Using demo script
   python3 demo_gui.py
   ```
3. To test the menu item classes without the GUI:
   ```bash
   python3 test_menu_items.py
   ```

**Note**: The Python version uses only standard library modules (tkinter, abc), so no additional packages need to be installed.

## Usage Instructions

1. **Adding Menu Items**: 
   - Select the item type from the dropdown menu
   - Fill in the item name, price, and description
   - Complete category-specific fields as they appear
   - Click "Add Item" to add to the menu

2. **Viewing Menu Items**: 
   - Added items appear in the list on the left side
   - Click on any item to view its details

3. **Saving Menu**: 
   - Use the save functionality to export your menu to [`Menu.txt`](Menu.txt)

4. **Clearing Fields**: 
   - Use the "Clear" button to reset all input fields

## Technical Details

### Java Version
- **Language**: Java
- **GUI Framework**: Swing
- **IDE Used**: NetBeans IDE 8.0 (for GUI skeleton)
- **Design Pattern**: Inheritance with method overriding (demonstrated in SpecialtyDrink class)

### Python Version
- **Language**: Python 3.x
- **GUI Framework**: tkinter (included with Python)
- **Design Pattern**: Object-oriented design with inheritance and abstract base classes
- **Dependencies**: None (uses only Python standard library)

## Known Issues

### Java Version
- Compiler warning about unchecked operations (program still runs correctly)
- Recompile with `-Xlint:unchecked` for detailed warnings

### Python Version
- None currently known

## Authors

- **Original Java Version**: Matthew Hino and Reece Teramoto (April 14, 2014)
- **Python Version**: Adapted from the original Java code (2025)
