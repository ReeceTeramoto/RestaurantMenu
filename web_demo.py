#!/usr/bin/env python3
"""
Web-based demo of the Restaurant Menu Management System
This creates a simple web interface to demonstrate the menu item classes
"""

import http.server
import socketserver
import json
from urllib.parse import parse_qs, urlparse
from menu_item import MenuItem, Appetizer, Entree, Dessert, Drink, SpecialtyDrink

class MenuHandler(http.server.SimpleHTTPRequestHandler):
    def __init__(self, *args, **kwargs):
        self.menu_items = []
        super().__init__(*args, **kwargs)
    
    def do_GET(self):
        if self.path == '/' or self.path == '/index.html':
            self.send_response(200)
            self.send_header('Content-type', 'text/html')
            self.end_headers()
            
            html = """
<!DOCTYPE html>
<html>
<head>
    <title>Restaurant Menu Management System - Python Demo</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 20px; background-color: #f5f5f5; }
        .container { max-width: 1200px; margin: 0 auto; background: white; padding: 20px; border-radius: 8px; box-shadow: 0 2px 4px rgba(0,0,0,0.1); }
        h1 { color: #333; text-align: center; }
        .demo-section { margin: 20px 0; padding: 15px; border: 1px solid #ddd; border-radius: 5px; }
        .menu-item { background: #f9f9f9; padding: 10px; margin: 10px 0; border-left: 4px solid #007bff; }
        .appetizer { border-left-color: #28a745; }
        .entree { border-left-color: #dc3545; }
        .dessert { border-left-color: #ffc107; }
        .drink { border-left-color: #17a2b8; }
        .specialty-drink { border-left-color: #6f42c1; }
        pre { background: #f8f9fa; padding: 10px; border-radius: 4px; overflow-x: auto; }
        .status { padding: 10px; margin: 10px 0; border-radius: 4px; }
        .success { background: #d4edda; color: #155724; border: 1px solid #c3e6cb; }
        .info { background: #d1ecf1; color: #0c5460; border: 1px solid #bee5eb; }
    </style>
</head>
<body>
    <div class="container">
        <h1>🍽️ Restaurant Menu Management System</h1>
        <h2>Python Version Demo</h2>
        
        <div class="status success">
            ✅ Python application is running successfully!
        </div>
        
        <div class="status info">
            This is a web-based demonstration of the Python Restaurant Menu Management System.
            The actual GUI application uses tkinter and would run as a desktop application.
        </div>
        
        <div class="demo-section">
            <h3>📋 Sample Menu Items</h3>
            <p>Here are examples of each menu item type created using the Python classes:</p>
            
            <div class="menu-item appetizer">
                <h4>🥗 Appetizer: Buffalo Wings</h4>
                <p><strong>Price:</strong> $8.99</p>
                <p><strong>Description:</strong> Spicy chicken wings with blue cheese</p>
                <p><strong>Serving Size:</strong> 3-4 people</p>
            </div>
            
            <div class="menu-item entree">
                <h4>🐟 Entree: Grilled Salmon</h4>
                <p><strong>Full Price:</strong> $18.99 | <strong>Mini Price:</strong> $12.99</p>
                <p><strong>Description:</strong> Fresh Atlantic salmon with herbs</p>
                <p><strong>Category:</strong> Fish</p>
            </div>
            
            <div class="menu-item dessert">
                <h4>🍰 Dessert: Chocolate Cake</h4>
                <p><strong>Price:</strong> $6.99</p>
                <p><strong>Description:</strong> Rich chocolate layer cake</p>
                <p><strong>Calories:</strong> 450</p>
            </div>
            
            <div class="menu-item drink">
                <h4>🥤 Drink: Coca Cola</h4>
                <p><strong>Price:</strong> $2.99</p>
                <p><strong>Description:</strong> Classic cola beverage</p>
                <p><strong>Refills:</strong> ✅ Free Refill</p>
            </div>
            
            <div class="menu-item specialty-drink">
                <h4>🍹 Specialty Drink: Mango Lassi</h4>
                <p><strong>Price:</strong> $4.99</p>
                <p><strong>Description:</strong> Traditional Indian yogurt drink</p>
                <p><strong>Refills:</strong> ❌ No Free Refill</p>
                <p><strong>Pairs with:</strong> Curry dishes</p>
            </div>
        </div>
        
        <div class="demo-section">
            <h3>🔧 Technical Details</h3>
            <ul>
                <li><strong>Language:</strong> Python 3.x</li>
                <li><strong>GUI Framework:</strong> tkinter (for desktop version)</li>
                <li><strong>Design Pattern:</strong> Object-oriented inheritance</li>
                <li><strong>Dependencies:</strong> Python standard library only</li>
                <li><strong>Classes:</strong> MenuItem (abstract), Appetizer, Entree, Dessert, Drink, SpecialtyDrink</li>
            </ul>
        </div>
        
        <div class="demo-section">
            <h3>🚀 How to Run Locally</h3>
            <pre>
# Clone the repository
git clone https://github.com/ReeceTeramoto/RestaurantMenu.git
cd RestaurantMenu

# Switch to Python version
git checkout python-version

# Run the GUI application
python3 restaurant_menu_app.py

# Or run tests
python3 test_menu_items.py
            </pre>
        </div>
        
        <div class="demo-section">
            <h3>📁 Project Files</h3>
            <ul>
                <li><code>menu_item.py</code> - Menu item classes with inheritance</li>
                <li><code>restaurant_menu_app.py</code> - Main tkinter GUI application</li>
                <li><code>run_menu_app.py</code> - Launcher script</li>
                <li><code>test_menu_items.py</code> - Test script</li>
                <li><code>demo_gui.py</code> - GUI demo script</li>
                <li><code>requirements.txt</code> - Dependencies (none needed)</li>
            </ul>
        </div>
        
        <div class="demo-section">
            <h3>🔗 Links</h3>
            <ul>
                <li><a href="https://github.com/ReeceTeramoto/RestaurantMenu/tree/python-version" target="_blank">Python Version on GitHub</a></li>
                <li><a href="https://github.com/ReeceTeramoto/RestaurantMenu" target="_blank">Original Java Version</a></li>
            </ul>
        </div>
    </div>
</body>
</html>
            """
            self.wfile.write(html.encode())
        else:
            super().do_GET()

def run_web_demo():
    PORT = 12000
    Handler = MenuHandler
    
    with socketserver.TCPServer(("0.0.0.0", PORT), Handler) as httpd:
        print(f"🌐 Restaurant Menu Demo running at http://localhost:{PORT}")
        print("📱 Access the demo through your browser")
        print("🛑 Press Ctrl+C to stop the server")
        httpd.serve_forever()

if __name__ == "__main__":
    run_web_demo()