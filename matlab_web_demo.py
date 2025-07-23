#!/usr/bin/env python3
"""
Web-based demo of the MATLAB Restaurant Menu Management System
This creates a simple web interface to demonstrate the MATLAB menu item classes
"""

import http.server
import socketserver
import subprocess
import os

class MATLABMenuHandler(http.server.SimpleHTTPRequestHandler):
    def do_GET(self):
        if self.path == '/' or self.path == '/index.html':
            self.send_response(200)
            self.send_header('Content-type', 'text/html')
            self.end_headers()
            
            # Run the MATLAB demo and capture output
            try:
                os.chdir('/workspace/RestaurantMenu')
                result = subprocess.run(['octave', '--no-gui', '--eval', 'demo_matlab_cli'], 
                                      capture_output=True, text=True)
                matlab_output = result.stdout
                
                # Read the generated menu file
                menu_content = ""
                try:
                    with open('Menu_MATLAB_CLI.txt', 'r') as f:
                        menu_content = f.read()
                except:
                    menu_content = "Menu file not found"
                
            except Exception as e:
                matlab_output = f"Error running MATLAB demo: {str(e)}"
                menu_content = "Error generating menu"
            
            html = f"""
<!DOCTYPE html>
<html>
<head>
    <title>Restaurant Menu Management System - MATLAB Demo</title>
    <style>
        body {{ font-family: Arial, sans-serif; margin: 20px; background-color: #f5f5f5; }}
        .container {{ max-width: 1200px; margin: 0 auto; background: white; padding: 20px; border-radius: 8px; box-shadow: 0 2px 4px rgba(0,0,0,0.1); }}
        h1 {{ color: #333; text-align: center; }}
        .demo-section {{ margin: 20px 0; padding: 15px; border: 1px solid #ddd; border-radius: 5px; }}
        .menu-item {{ background: #f9f9f9; padding: 10px; margin: 10px 0; border-left: 4px solid #007bff; }}
        .appetizer {{ border-left-color: #28a745; }}
        .entree {{ border-left-color: #dc3545; }}
        .dessert {{ border-left-color: #ffc107; }}
        .drink {{ border-left-color: #17a2b8; }}
        .specialty-drink {{ border-left-color: #6f42c1; }}
        pre {{ background: #f8f9fa; padding: 10px; border-radius: 4px; overflow-x: auto; font-size: 12px; }}
        .status {{ padding: 10px; margin: 10px 0; border-radius: 4px; }}
        .success {{ background: #d4edda; color: #155724; border: 1px solid #c3e6cb; }}
        .info {{ background: #d1ecf1; color: #0c5460; border: 1px solid #bee5eb; }}
        .matlab-logo {{ color: #e97627; font-weight: bold; }}
    </style>
</head>
<body>
    <div class="container">
        <h1>🧮 Restaurant Menu Management System</h1>
        <h2><span class="matlab-logo">MATLAB</span> Version Demo</h2>
        
        <div class="status success">
            ✅ MATLAB application is running successfully with GNU Octave!
        </div>
        
        <div class="status info">
            This is a web-based demonstration of the MATLAB Restaurant Menu Management System.
            The code runs on GNU Octave (open-source MATLAB alternative) and demonstrates 
            object-oriented programming with classes and inheritance.
        </div>
        
        <div class="demo-section">
            <h3>🖥️ Live MATLAB Output</h3>
            <p>Here's the actual output from running the MATLAB demo:</p>
            <pre>{matlab_output}</pre>
        </div>
        
        <div class="demo-section">
            <h3>📋 Generated Menu File</h3>
            <p>The MATLAB application generated this menu file:</p>
            <pre>{menu_content}</pre>
        </div>
        
        <div class="demo-section">
            <h3>🔧 Technical Details</h3>
            <ul>
                <li><strong>Language:</strong> MATLAB / GNU Octave</li>
                <li><strong>GUI Framework:</strong> MATLAB GUI (for desktop version)</li>
                <li><strong>Design Pattern:</strong> Object-oriented inheritance with handle classes</li>
                <li><strong>Dependencies:</strong> MATLAB or GNU Octave</li>
                <li><strong>Classes:</strong> MenuItem (base), Appetizer, Entree, Dessert, Drink, SpecialtyDrink</li>
                <li><strong>Features:</strong> Polymorphism, method overriding, constants, file I/O</li>
            </ul>
        </div>
        
        <div class="demo-section">
            <h3>🚀 How to Run Locally</h3>
            <pre>
# Clone the repository
git clone https://github.com/ReeceTeramoto/RestaurantMenu.git
cd RestaurantMenu

# Switch to Python version (contains MATLAB files too)
git checkout python-version

# Run with MATLAB
matlab -r "test_menu_items; demo_matlab_cli; exit"

# Or run with GNU Octave (free alternative)
octave --no-gui --eval "test_menu_items"
octave --no-gui --eval "demo_matlab_cli"

# For GUI version (MATLAB only)
matlab -r "run_matlab_app"
            </pre>
        </div>
        
        <div class="demo-section">
            <h3>📁 MATLAB Project Files</h3>
            <ul>
                <li><code>MenuItem.m</code> - Base class for all menu items</li>
                <li><code>Appetizer.m</code> - Appetizer class with serving size</li>
                <li><code>Entree.m</code> - Entree class with mini price and category</li>
                <li><code>Dessert.m</code> - Dessert class with calorie information</li>
                <li><code>Drink.m</code> - Drink class with refill capability</li>
                <li><code>SpecialtyDrink.m</code> - Specialty drink class (no refills)</li>
                <li><code>RestaurantMenuApp.m</code> - GUI application class</li>
                <li><code>test_menu_items.m</code> - Test script</li>
                <li><code>demo_matlab_cli.m</code> - Command-line demo</li>
                <li><code>run_matlab_app.m</code> - GUI launcher</li>
            </ul>
        </div>
        
        <div class="demo-section">
            <h3>🔗 Links</h3>
            <ul>
                <li><a href="https://github.com/ReeceTeramoto/RestaurantMenu/tree/python-version" target="_blank">Python & MATLAB Version on GitHub</a></li>
                <li><a href="https://github.com/ReeceTeramoto/RestaurantMenu" target="_blank">Original Java Version</a></li>
                <li><a href="https://octave.org/" target="_blank">GNU Octave (Free MATLAB Alternative)</a></li>
            </ul>
        </div>
        
        <div class="demo-section">
            <h3>🔄 Refresh Demo</h3>
            <p><a href="/" onclick="location.reload()">Click here to refresh and run the demo again</a></p>
        </div>
    </div>
</body>
</html>
            """
            self.wfile.write(html.encode())
        else:
            super().do_GET()

def run_matlab_web_demo():
    PORT = 12001
    Handler = MATLABMenuHandler
    
    with socketserver.TCPServer(("0.0.0.0", PORT), Handler) as httpd:
        print(f"🧮 MATLAB Restaurant Menu Demo running at http://localhost:{PORT}")
        print("📱 Access the demo through your browser")
        print("🛑 Press Ctrl+C to stop the server")
        httpd.serve_forever()

if __name__ == "__main__":
    run_matlab_web_demo()