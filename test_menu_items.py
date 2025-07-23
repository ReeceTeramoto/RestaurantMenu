#!/usr/bin/env python3
"""
Simple test script to demonstrate the menu item classes work correctly

@author Python version adapted from Matthew Hino and Reece Teramoto's Java code
@version 2025
"""

from menu_item import MenuItem, Appetizer, Entree, Dessert, Drink, SpecialtyDrink


def test_menu_items():
    """Test all menu item classes"""
    print("Testing Restaurant Menu Management System - Python Version")
    print("=" * 60)
    
    # Test Appetizer
    print("\n1. Testing Appetizer:")
    appetizer = Appetizer("Buffalo Wings", "Spicy chicken wings with blue cheese", 8.99, Appetizer.THREEFOUR)
    print(appetizer)
    print()
    
    # Test Entree
    print("2. Testing Entree:")
    entree = Entree("Grilled Salmon", "Fresh Atlantic salmon with herbs", 18.99, 12.99, Entree.FISH)
    print(entree)
    print()
    
    # Test Dessert
    print("3. Testing Dessert:")
    dessert = Dessert("Chocolate Cake", "Rich chocolate layer cake", 6.99, 450)
    print(dessert)
    print()
    
    # Test Drink
    print("4. Testing Drink:")
    drink = Drink("Coca Cola", "Classic cola beverage", 2.99, True)
    print(drink)
    print()
    
    # Test SpecialtyDrink
    print("5. Testing SpecialtyDrink:")
    specialty = SpecialtyDrink("Mango Lassi", "Traditional Indian yogurt drink", 4.99, "Curry dishes")
    print(specialty)
    print()
    
    # Test equality
    print("6. Testing equality:")
    appetizer2 = Appetizer("Buffalo Wings", "Different description", 9.99, Appetizer.ONETWO)
    print(f"appetizer == appetizer2: {appetizer == appetizer2}")
    
    appetizer3 = Appetizer("Mozzarella Sticks", "Fried cheese sticks", 7.99, Appetizer.ONETWO)
    print(f"appetizer == appetizer3: {appetizer == appetizer3}")
    print()
    
    # Test specialty drink refill override
    print("7. Testing SpecialtyDrink refill override:")
    print(f"Specialty drink refill before: {specialty.refill_ok()}")
    specialty.set_refill(True)  # This should do nothing
    print(f"Specialty drink refill after set_refill(True): {specialty.refill_ok()}")
    print()
    
    print("All tests completed successfully!")


if __name__ == "__main__":
    test_menu_items()