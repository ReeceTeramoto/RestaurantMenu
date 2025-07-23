function demo_matlab_cli()
    % DEMO_MATLAB_CLI Command-line demo of the MATLAB Restaurant Menu System
    % This script demonstrates the menu item classes in a simple CLI interface
    %
    % Author: MATLAB version adapted from Matthew Hino and Reece Teramoto's Java code
    % Version: 2025
    
    fprintf('\n=== Restaurant Menu Management System - MATLAB Version ===\n');
    fprintf('Command-Line Demo\n\n');
    
    % Create sample menu items
    menu_items = {};
    
    % Add sample items
    fprintf('Creating sample menu items...\n\n');
    
    % Appetizer
    appetizer = Appetizer('Buffalo Wings', 'Spicy chicken wings with blue cheese', 8.99, Appetizer.THREEFOUR);
    menu_items{end+1} = appetizer;
    fprintf('Added: %s\n', appetizer.getName());
    
    % Entree
    entree = Entree('Grilled Salmon', 'Fresh Atlantic salmon with herbs', 18.99, 12.99, Entree.FISH);
    menu_items{end+1} = entree;
    fprintf('Added: %s\n', entree.getName());
    
    % Dessert
    dessert = Dessert('Chocolate Cake', 'Rich chocolate layer cake', 6.99, 450);
    menu_items{end+1} = dessert;
    fprintf('Added: %s\n', dessert.getName());
    
    % Drink
    drink = Drink('Coca Cola', 'Classic cola beverage', 2.99, true);
    menu_items{end+1} = drink;
    fprintf('Added: %s\n', drink.getName());
    
    % Specialty Drink
    specialty = SpecialtyDrink('Mango Lassi', 'Traditional Indian yogurt drink', 4.99, 'Curry dishes');
    menu_items{end+1} = specialty;
    fprintf('Added: %s\n', specialty.getName());
    
    % Display complete menu
    fprintf('\n=== COMPLETE MENU ===\n\n');
    for i = 1:length(menu_items)
        fprintf('%d. %s\n\n', i, menu_items{i}.toString());
    end
    
    % Save menu to file
    filename = 'Menu_MATLAB_CLI.txt';
    fid = fopen(filename, 'w');
    
    if fid ~= -1
        fprintf(fid, 'Restaurant Menu - MATLAB Version\n');
        fprintf(fid, '================================\n\n');
        
        for i = 1:length(menu_items)
            fprintf(fid, '%d. %s\n\n', i, menu_items{i}.toString());
        end
        
        fclose(fid);
        fprintf('Menu saved to %s\n\n', filename);
    else
        fprintf('Error: Could not save menu to file.\n\n');
    end
    
    % Demonstrate polymorphism
    fprintf('=== POLYMORPHISM DEMONSTRATION ===\n\n');
    fprintf('All items are MenuItem objects, but each has different behavior:\n\n');
    
    for i = 1:length(menu_items)
        item = menu_items{i};
        fprintf('Item %d (%s): %s - $%.2f\n', i, class(item), item.getName(), item.getPrice());
    end
    
    fprintf('\nDemo completed successfully!\n');
end