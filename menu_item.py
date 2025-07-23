"""
Abstract base class MenuItem - holds the menu item's name, description, and price
and methods to set and get these attributes

@author Python version adapted from Matthew Hino and Reece Teramoto's Java code
@version 2025
"""

from abc import ABC, abstractmethod


class MenuItem(ABC):
    """Abstract base class for all menu items"""
    
    def __init__(self, name="", description="", price=0.0):
        """
        Constructor for MenuItem
        
        Args:
            name (str): Name of the menu item
            description (str): Description of the menu item  
            price (float): Price of the menu item
        """
        self._name = name
        self._description = description
        self._price = price
    
    def get_name(self):
        """Returns the name of the MenuItem"""
        return self._name
    
    def get_description(self):
        """Returns the description of the MenuItem"""
        return self._description
    
    def get_price(self):
        """Returns the price of the MenuItem"""
        return self._price
    
    def set_name(self, name):
        """Sets the name of the MenuItem"""
        self._name = name
    
    def set_description(self, description):
        """Sets the description of the MenuItem"""
        self._description = description
    
    def set_price(self, price):
        """Sets the price of the MenuItem"""
        self._price = price
    
    def __str__(self):
        """String representation of MenuItem"""
        return f"{self._name}\t\t\t${self._price}\n{self._description}"
    
    def __eq__(self, other):
        """Equality comparison based on name (case insensitive)"""
        if not isinstance(other, MenuItem):
            return False
        return self._name.lower() == other.get_name().lower()


class Appetizer(MenuItem):
    """This class represents an Appetizer, which is a MenuItem."""
    
    # Serving size constants
    ONETWO = 0
    THREEFOUR = 1
    FIVESIX = 2
    
    def __init__(self, name="", description="", price=0.0, serving_size=0):
        """
        Constructor for Appetizer
        
        Args:
            name (str): Name of the appetizer
            description (str): Description of the appetizer
            price (float): Price of the appetizer
            serving_size (int): Serving size constant
        """
        super().__init__(name, description, price)
        self._serving_size = serving_size
    
    def get_serving_size(self):
        """Returns the suggested serving size as a string"""
        if self._serving_size == self.ONETWO:
            return "1-2"
        elif self._serving_size == self.THREEFOUR:
            return "3-4"
        else:
            return "5-6"
    
    def set_serving_size(self, serving_size):
        """Sets the serving size to a new value"""
        self._serving_size = serving_size
    
    def __str__(self):
        """String representation of Appetizer"""
        return (f"{self.get_name()}\t\t\t${self.get_price()}\n"
                f"Description: {self.get_description()}\tServing Size: {self.get_serving_size()}")


class Entree(MenuItem):
    """This class represents an Entree, which is a MenuItem."""
    
    # Category constants
    CHICKEN = 0
    FISH = 1
    MEAT = 2
    
    def __init__(self, name="", description="", price=0.0, mini_price=0.0, category=0):
        """
        Constructor for Entree
        
        Args:
            name (str): Name of the entree
            description (str): Description of the entree
            price (float): Full price of the entree
            mini_price (float): Mini/half price of the entree
            category (int): Category constant
        """
        super().__init__(name, description, price)
        self._mini_price = mini_price
        self._category = category
    
    def get_mini_price(self):
        """Returns the mini price"""
        return self._mini_price
    
    def get_category(self):
        """Returns the category"""
        return self._category
    
    def set_mini_price(self, mini_price):
        """Sets the mini price to a new value"""
        self._mini_price = mini_price
    
    def __str__(self):
        """String representation of Entree"""
        category_names = {
            self.CHICKEN: "Chicken",
            self.FISH: "Fish", 
            self.MEAT: "Meat"
        }
        cat = category_names.get(self._category, "Unknown")
        
        return (f"{self.get_name()}\t\t\tFull: ${self.get_price()} Mini: ${self.get_mini_price()}\n"
                f"Description: {self.get_description()}\nCategory: {cat}")


class Dessert(MenuItem):
    """This class represents a Dessert, which is a MenuItem."""
    
    def __init__(self, name="", description="", price=0.0, calorie_count=0):
        """
        Constructor for Dessert
        
        Args:
            name (str): Name of the dessert
            description (str): Description of the dessert
            price (float): Price of the dessert
            calorie_count (int): Calorie count of the dessert
        """
        super().__init__(name, description, price)
        self._calorie_count = calorie_count
    
    def get_cal_count(self):
        """Returns the calorie count"""
        return self._calorie_count
    
    def set_cal_count(self, calorie_count):
        """Sets the calorie count"""
        self._calorie_count = calorie_count
    
    def __str__(self):
        """String representation of Dessert"""
        return (f"{self.get_name()}\t\t\t${self.get_price()} Calories: {self.get_cal_count()}\n"
                f"Description: {self.get_description()}")


class Drink(MenuItem):
    """This class represents a Drink, which is a MenuItem."""
    
    def __init__(self, name="", description="", price=0.0, free_refill=False):
        """
        Constructor for Drink
        
        Args:
            name (str): Name of the drink
            description (str): Description of the drink
            price (float): Price of the drink
            free_refill (bool): Whether the drink has free refills
        """
        super().__init__(name, description, price)
        self._free_refill = free_refill
    
    def refill_ok(self):
        """Returns if the drink comes with a free refill"""
        return self._free_refill
    
    def set_refill(self, status):
        """Sets the refill status"""
        self._free_refill = status
    
    def __str__(self):
        """String representation of Drink"""
        refill_text = "Free Refill" if self.refill_ok() else "No Free Refill"
        return (f"{self.get_name()}\t\t\t${self.get_price()}\t{refill_text}\n"
                f"Description: {self.get_description()}")


class SpecialtyDrink(Drink):
    """This class represents a SpecialtyDrink, which is a Drink."""
    
    def __init__(self, name="", description="", price=0.0, paired_dish=""):
        """
        Constructor for SpecialtyDrink
        
        Args:
            name (str): Name of the specialty drink
            description (str): Description of the specialty drink
            price (float): Price of the specialty drink
            paired_dish (str): Dish that pairs well with the drink
        """
        super().__init__(name, description, price, False)  # Specialty drinks don't get free refills
        self._paired_dish = paired_dish
    
    def get_paired_dish(self):
        """Returns the paired dish"""
        return self._paired_dish
    
    def set_paired_dish(self, paired_dish):
        """Sets the paired dish"""
        self._paired_dish = paired_dish
    
    def set_refill(self, status):
        """Override: Specialty drinks do not get refills"""
        # Do nothing since specialty drinks do not get refills
        pass
    
    def __str__(self):
        """String representation of SpecialtyDrink"""
        return (f"{self.get_name()}\t\t\t${self.get_price()}\tNo Free Refill\n"
                f"Description: {self.get_description()}")