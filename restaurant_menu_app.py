"""
Restaurant Menu Management System - Python GUI Application

This application creates a GUI that allows users to input data to create objects of type MenuItem
and manage a restaurant menu with different categories of items.

@author Python version adapted from Matthew Hino and Reece Teramoto's Java code
@version 2025
"""

import tkinter as tk
from tkinter import ttk, messagebox, scrolledtext
from menu_item import MenuItem, Appetizer, Entree, Dessert, Drink, SpecialtyDrink


class RestaurantMenuApp:
    """Main GUI application for restaurant menu management"""
    
    def __init__(self, root):
        """Initialize the application"""
        self.root = root
        self.root.title("Restaurant Menu Management System")
        self.root.geometry("800x600")
        
        # Data storage
        self.menu_items = []
        
        # Item type constants
        self.APPETIZER = 0
        self.ENTREE = 1
        self.DESSERT = 2
        self.DRINK = 3
        
        # Create the GUI
        self.create_widgets()
        self.clear_all_fields()
    
    def create_widgets(self):
        """Create and layout all GUI widgets"""
        # Main frame
        main_frame = ttk.Frame(self.root, padding="10")
        main_frame.grid(row=0, column=0, sticky=(tk.W, tk.E, tk.N, tk.S))
        
        # Configure grid weights
        self.root.columnconfigure(0, weight=1)
        self.root.rowconfigure(0, weight=1)
        main_frame.columnconfigure(1, weight=1)
        main_frame.rowconfigure(0, weight=1)
        
        # Left panel - Menu items list
        self.create_menu_list_panel(main_frame)
        
        # Right panel - Input form
        self.create_input_panel(main_frame)
    
    def create_menu_list_panel(self, parent):
        """Create the left panel with menu items list"""
        list_frame = ttk.LabelFrame(parent, text="Menu Items", padding="5")
        list_frame.grid(row=0, column=0, sticky=(tk.W, tk.E, tk.N, tk.S), padx=(0, 5))
        
        # Listbox for menu items
        self.menu_listbox = tk.Listbox(list_frame, width=30)
        self.menu_listbox.grid(row=0, column=0, sticky=(tk.W, tk.E, tk.N, tk.S))
        self.menu_listbox.bind('<<ListboxSelect>>', self.on_item_select)
        
        # Scrollbar for listbox
        scrollbar = ttk.Scrollbar(list_frame, orient="vertical", command=self.menu_listbox.yview)
        scrollbar.grid(row=0, column=1, sticky=(tk.N, tk.S))
        self.menu_listbox.configure(yscrollcommand=scrollbar.set)
        
        list_frame.columnconfigure(0, weight=1)
        list_frame.rowconfigure(0, weight=1)
        
        # Item details display
        details_frame = ttk.LabelFrame(parent, text="Item Details", padding="5")
        details_frame.grid(row=1, column=0, sticky=(tk.W, tk.E, tk.N, tk.S), padx=(0, 5), pady=(5, 0))
        
        self.details_text = scrolledtext.ScrolledText(details_frame, width=30, height=8, state='disabled')
        self.details_text.grid(row=0, column=0, sticky=(tk.W, tk.E, tk.N, tk.S))
        
        details_frame.columnconfigure(0, weight=1)
        details_frame.rowconfigure(0, weight=1)
    
    def create_input_panel(self, parent):
        """Create the right panel with input form"""
        input_frame = ttk.LabelFrame(parent, text="Add Menu Item", padding="10")
        input_frame.grid(row=0, column=1, rowspan=2, sticky=(tk.W, tk.E, tk.N, tk.S))
        
        row = 0
        
        # Item type selection
        ttk.Label(input_frame, text="Item Type:").grid(row=row, column=0, sticky=tk.W, pady=2)
        self.item_type_var = tk.StringVar()
        self.item_type_combo = ttk.Combobox(input_frame, textvariable=self.item_type_var, 
                                           values=["Appetizer", "Entree", "Dessert", "Drink"], 
                                           state="readonly", width=20)
        self.item_type_combo.grid(row=row, column=1, sticky=(tk.W, tk.E), pady=2)
        self.item_type_combo.bind('<<ComboboxSelected>>', self.on_item_type_change)
        row += 1
        
        # Item name
        ttk.Label(input_frame, text="Item Name:").grid(row=row, column=0, sticky=tk.W, pady=2)
        self.item_name_var = tk.StringVar()
        self.item_name_entry = ttk.Entry(input_frame, textvariable=self.item_name_var, width=25)
        self.item_name_entry.grid(row=row, column=1, sticky=(tk.W, tk.E), pady=2)
        row += 1
        
        # Item price
        ttk.Label(input_frame, text="Price ($):").grid(row=row, column=0, sticky=tk.W, pady=2)
        self.item_price_var = tk.StringVar()
        self.item_price_entry = ttk.Entry(input_frame, textvariable=self.item_price_var, width=25)
        self.item_price_entry.grid(row=row, column=1, sticky=(tk.W, tk.E), pady=2)
        row += 1
        
        # Description
        ttk.Label(input_frame, text="Description:").grid(row=row, column=0, sticky=(tk.W, tk.N), pady=2)
        self.description_text = tk.Text(input_frame, width=25, height=3)
        self.description_text.grid(row=row, column=1, sticky=(tk.W, tk.E), pady=2)
        row += 1
        
        # Separator
        ttk.Separator(input_frame, orient='horizontal').grid(row=row, column=0, columnspan=2, 
                                                            sticky=(tk.W, tk.E), pady=10)
        row += 1
        
        # Category-specific fields frame
        self.specific_frame = ttk.Frame(input_frame)
        self.specific_frame.grid(row=row, column=0, columnspan=2, sticky=(tk.W, tk.E), pady=5)
        row += 1
        
        # Buttons frame
        button_frame = ttk.Frame(input_frame)
        button_frame.grid(row=row, column=0, columnspan=2, pady=10)
        
        ttk.Button(button_frame, text="Add Item", command=self.add_item).pack(side=tk.LEFT, padx=5)
        ttk.Button(button_frame, text="Clear", command=self.clear_all_fields).pack(side=tk.LEFT, padx=5)
        ttk.Button(button_frame, text="Save Menu", command=self.save_menu).pack(side=tk.LEFT, padx=5)
        
        input_frame.columnconfigure(1, weight=1)
        
        # Initialize category-specific fields
        self.create_category_fields()
    
    def create_category_fields(self):
        """Create category-specific input fields"""
        # Clear existing fields
        for widget in self.specific_frame.winfo_children():
            widget.destroy()
        
        item_type = self.item_type_var.get()
        
        if item_type == "Appetizer":
            self.create_appetizer_fields()
        elif item_type == "Entree":
            self.create_entree_fields()
        elif item_type == "Dessert":
            self.create_dessert_fields()
        elif item_type == "Drink":
            self.create_drink_fields()
    
    def create_appetizer_fields(self):
        """Create appetizer-specific fields"""
        ttk.Label(self.specific_frame, text="Serving Size:").grid(row=0, column=0, sticky=tk.W, pady=2)
        
        self.serving_size_var = tk.IntVar()
        ttk.Radiobutton(self.specific_frame, text="1-2 people", variable=self.serving_size_var, 
                       value=Appetizer.ONETWO).grid(row=1, column=0, sticky=tk.W)
        ttk.Radiobutton(self.specific_frame, text="3-4 people", variable=self.serving_size_var, 
                       value=Appetizer.THREEFOUR).grid(row=2, column=0, sticky=tk.W)
        ttk.Radiobutton(self.specific_frame, text="5-6 people", variable=self.serving_size_var, 
                       value=Appetizer.FIVESIX).grid(row=3, column=0, sticky=tk.W)
    
    def create_entree_fields(self):
        """Create entree-specific fields"""
        # Mini price
        ttk.Label(self.specific_frame, text="Mini Price ($):").grid(row=0, column=0, sticky=tk.W, pady=2)
        self.mini_price_var = tk.StringVar()
        ttk.Entry(self.specific_frame, textvariable=self.mini_price_var, width=15).grid(row=0, column=1, sticky=tk.W, pady=2)
        
        # Category
        ttk.Label(self.specific_frame, text="Category:").grid(row=1, column=0, sticky=tk.W, pady=2)
        self.entree_category_var = tk.StringVar()
        category_combo = ttk.Combobox(self.specific_frame, textvariable=self.entree_category_var,
                                     values=["Chicken", "Fish", "Meat"], state="readonly", width=12)
        category_combo.grid(row=1, column=1, sticky=tk.W, pady=2)
    
    def create_dessert_fields(self):
        """Create dessert-specific fields"""
        ttk.Label(self.specific_frame, text="Calories:").grid(row=0, column=0, sticky=tk.W, pady=2)
        self.calories_var = tk.StringVar()
        ttk.Entry(self.specific_frame, textvariable=self.calories_var, width=15).grid(row=0, column=1, sticky=tk.W, pady=2)
        
        ttk.Label(self.specific_frame, text="Pairing:").grid(row=1, column=0, sticky=tk.W, pady=2)
        self.pairing_var = tk.StringVar()
        ttk.Entry(self.specific_frame, textvariable=self.pairing_var, width=15).grid(row=1, column=1, sticky=tk.W, pady=2)
    
    def create_drink_fields(self):
        """Create drink-specific fields"""
        # Drink type
        ttk.Label(self.specific_frame, text="Drink Type:").grid(row=0, column=0, sticky=tk.W, pady=2)
        
        self.drink_type_var = tk.StringVar()
        ttk.Radiobutton(self.specific_frame, text="Normal Drink", variable=self.drink_type_var, 
                       value="normal", command=self.on_drink_type_change).grid(row=1, column=0, sticky=tk.W)
        ttk.Radiobutton(self.specific_frame, text="Specialty Drink", variable=self.drink_type_var, 
                       value="specialty", command=self.on_drink_type_change).grid(row=2, column=0, sticky=tk.W)
        
        # Refill option (for normal drinks)
        self.refill_frame = ttk.Frame(self.specific_frame)
        self.refill_frame.grid(row=3, column=0, columnspan=2, sticky=tk.W, pady=5)
        
        self.free_refill_var = tk.BooleanVar()
        self.refill_check = ttk.Checkbutton(self.refill_frame, text="Free Refill", 
                                           variable=self.free_refill_var)
        
        # Paired dish (for specialty drinks)
        self.specialty_frame = ttk.Frame(self.specific_frame)
        self.specialty_frame.grid(row=4, column=0, columnspan=2, sticky=tk.W, pady=5)
        
        ttk.Label(self.specialty_frame, text="Paired Dish:").grid(row=0, column=0, sticky=tk.W, pady=2)
        self.paired_dish_var = tk.StringVar()
        ttk.Entry(self.specialty_frame, textvariable=self.paired_dish_var, width=20).grid(row=0, column=1, sticky=tk.W, pady=2)
    
    def on_drink_type_change(self):
        """Handle drink type selection change"""
        drink_type = self.drink_type_var.get()
        
        # Hide both frames first
        for widget in self.refill_frame.winfo_children():
            widget.grid_remove()
        for widget in self.specialty_frame.winfo_children():
            widget.grid_remove()
        
        if drink_type == "normal":
            self.refill_check.grid(row=0, column=0, sticky=tk.W)
        elif drink_type == "specialty":
            for widget in self.specialty_frame.winfo_children():
                widget.grid()
    
    def on_item_type_change(self, event=None):
        """Handle item type selection change"""
        self.create_category_fields()
    
    def on_item_select(self, event=None):
        """Handle menu item selection"""
        selection = self.menu_listbox.curselection()
        if selection:
            index = selection[0]
            if index < len(self.menu_items):
                item = self.menu_items[index]
                self.display_item_details(item)
    
    def display_item_details(self, item):
        """Display details of selected menu item"""
        self.details_text.config(state='normal')
        self.details_text.delete(1.0, tk.END)
        self.details_text.insert(1.0, str(item))
        self.details_text.config(state='disabled')
    
    def clear_all_fields(self):
        """Clear all input fields and reset to defaults"""
        self.item_type_combo.set("Appetizer")
        self.item_name_var.set("Item Name")
        self.item_price_var.set("Price(dollars)")
        self.description_text.delete(1.0, tk.END)
        
        # Clear category-specific fields
        self.create_category_fields()
        
        # Clear details display
        self.details_text.config(state='normal')
        self.details_text.delete(1.0, tk.END)
        self.details_text.config(state='disabled')
    
    def validate_item(self):
        """Validate input fields"""
        name = self.item_name_var.get().strip()
        description = self.description_text.get(1.0, tk.END).strip()
        price_str = self.item_price_var.get().strip()
        
        # Validate price
        try:
            price = float(price_str)
            if price <= 0:
                messagebox.showerror("Error", "Price must be a positive number.")
                return False
        except ValueError:
            messagebox.showerror("Error", "Price input is invalid.")
            return False
        
        # Validate name
        if not name or name == "Item Name":
            messagebox.showerror("Error", "Name is invalid.")
            return False
        
        # Check for duplicate names
        for item in self.menu_items:
            if item.get_name().lower() == name.lower():
                messagebox.showerror("Error", "Cannot have two of the same item.")
                return False
        
        # Validate description
        if not description:
            messagebox.showerror("Error", "Description is invalid.")
            return False
        
        return True
    
    def validate_appetizer(self):
        """Validate appetizer-specific fields"""
        # Check if serving size is selected (default is 0, so any value >= 0 is valid)
        return True  # Radio buttons always have a selection
    
    def validate_entree(self):
        """Validate entree-specific fields"""
        try:
            mini_price = float(self.mini_price_var.get())
            if mini_price <= 0:
                messagebox.showerror("Error", "Mini price must be a positive number.")
                return False
        except ValueError:
            messagebox.showerror("Error", "Mini price input is invalid.")
            return False
        
        if not self.entree_category_var.get():
            messagebox.showerror("Error", "Please select a category.")
            return False
        
        return True
    
    def validate_dessert(self):
        """Validate dessert-specific fields"""
        try:
            calories = int(self.calories_var.get())
            if calories < 0:
                messagebox.showerror("Error", "Calories cannot be negative.")
                return False
        except ValueError:
            messagebox.showerror("Error", "Calories input is invalid.")
            return False
        
        return True
    
    def validate_drink(self):
        """Validate drink-specific fields"""
        if not self.drink_type_var.get():
            messagebox.showerror("Error", "Please select a drink type.")
            return False
        
        return True
    
    def add_item(self):
        """Add a new menu item"""
        if not self.validate_item():
            return
        
        item_type = self.item_type_var.get()
        name = self.item_name_var.get().strip()
        description = self.description_text.get(1.0, tk.END).strip()
        price = float(self.item_price_var.get().strip())
        
        try:
            if item_type == "Appetizer":
                if not self.validate_appetizer():
                    return
                serving_size = self.serving_size_var.get()
                item = Appetizer(name, description, price, serving_size)
            
            elif item_type == "Entree":
                if not self.validate_entree():
                    return
                mini_price = float(self.mini_price_var.get())
                category_map = {"Chicken": Entree.CHICKEN, "Fish": Entree.FISH, "Meat": Entree.MEAT}
                category = category_map[self.entree_category_var.get()]
                item = Entree(name, description, price, mini_price, category)
            
            elif item_type == "Dessert":
                if not self.validate_dessert():
                    return
                calories = int(self.calories_var.get())
                item = Dessert(name, description, price, calories)
            
            elif item_type == "Drink":
                if not self.validate_drink():
                    return
                drink_type = self.drink_type_var.get()
                if drink_type == "normal":
                    free_refill = self.free_refill_var.get()
                    item = Drink(name, description, price, free_refill)
                else:  # specialty
                    paired_dish = self.paired_dish_var.get()
                    item = SpecialtyDrink(name, description, price, paired_dish)
            
            # Add item to list
            self.menu_items.append(item)
            self.menu_listbox.insert(tk.END, name)
            
            messagebox.showinfo("Success", f"{item_type} '{name}' added successfully!")
            self.clear_all_fields()
            
        except Exception as e:
            messagebox.showerror("Error", f"Failed to add item: {str(e)}")
    
    def save_menu(self):
        """Save menu to text file"""
        try:
            with open("Menu.txt", "w") as file:
                # Group items by category
                appetizers = [item for item in self.menu_items if isinstance(item, Appetizer)]
                entrees = [item for item in self.menu_items if isinstance(item, Entree)]
                desserts = [item for item in self.menu_items if isinstance(item, Dessert)]
                normal_drinks = [item for item in self.menu_items if isinstance(item, Drink) and not isinstance(item, SpecialtyDrink)]
                specialty_drinks = [item for item in self.menu_items if isinstance(item, SpecialtyDrink)]
                
                # Write appetizers
                file.write("~~~~~~APPETIZERS~~~~~~~\n")
                for item in appetizers:
                    file.write(str(item) + "\n\n")
                file.write("\n")
                
                # Write entrees
                file.write("~~~~~~ENTREES~~~~~~~\n")
                for item in entrees:
                    file.write(str(item) + "\n\n")
                file.write("\n")
                
                # Write desserts
                file.write("~~~~~~DESSERTS~~~~~~~\n")
                for item in desserts:
                    file.write(str(item) + "\n\n")
                file.write("\n")
                
                # Write normal drinks
                file.write("~~~~~~NORMAL DRINKS~~~~~~~\n")
                for item in normal_drinks:
                    file.write(str(item) + "\n\n")
                file.write("\n")
                
                # Write specialty drinks
                file.write("~~~~~~SPECIALTY DRINKS~~~~~~~\n")
                for item in specialty_drinks:
                    file.write(str(item) + "\n\n")
            
            messagebox.showinfo("Success", "Menu saved to Menu.txt successfully!")
            
        except Exception as e:
            messagebox.showerror("Error", f"Failed to save menu: {str(e)}")


def main():
    """Main function to run the application"""
    root = tk.Tk()
    app = RestaurantMenuApp(root)
    root.mainloop()


if __name__ == "__main__":
    main()