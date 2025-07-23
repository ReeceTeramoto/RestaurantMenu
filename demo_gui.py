#!/usr/bin/env python3
"""
Demo script to show the GUI application (non-interactive for testing)

@author Python version adapted from Matthew Hino and Reece Teramoto's Java code
@version 2025
"""

import tkinter as tk
from restaurant_menu_app import RestaurantMenuApp


def demo_gui():
    """Create and show the GUI application"""
    print("Starting Restaurant Menu Management System GUI...")
    print("This will open a window with the menu management interface.")
    print("You can:")
    print("- Select item types from the dropdown")
    print("- Fill in item details")
    print("- Add items to the menu")
    print("- View added items in the list")
    print("- Save the menu to Menu.txt")
    print("\nClose the window to exit the application.")
    
    root = tk.Tk()
    app = RestaurantMenuApp(root)
    
    # Add some sample data for demonstration
    print("\nAdding sample menu items for demonstration...")
    
    # You could programmatically add items here if needed for testing
    # For now, we'll just show the empty interface
    
    root.mainloop()
    print("GUI application closed.")


if __name__ == "__main__":
    demo_gui()