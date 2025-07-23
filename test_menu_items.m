function test_menu_items()
    % TEST_MENU_ITEMS Test script for MATLAB menu item classes
    % This script demonstrates all menu item classes work correctly
    %
    % Author: MATLAB version adapted from Matthew Hino and Reece Teramoto's Java code
    % Version: 2025
    
    fprintf('Testing Restaurant Menu Management System - MATLAB Version\n');
    fprintf('============================================================\n\n');
    
    % Test Appetizer
    fprintf('1. Testing Appetizer:\n');
    appetizer = Appetizer('Buffalo Wings', 'Spicy chicken wings with blue cheese', 8.99, Appetizer.THREEFOUR);
    fprintf('%s\n\n', appetizer.toString());
    
    % Test Entree
    fprintf('2. Testing Entree:\n');
    entree = Entree('Grilled Salmon', 'Fresh Atlantic salmon with herbs', 18.99, 12.99, Entree.FISH);
    fprintf('%s\n\n', entree.toString());
    
    % Test Dessert
    fprintf('3. Testing Dessert:\n');
    dessert = Dessert('Chocolate Cake', 'Rich chocolate layer cake', 6.99, 450);
    fprintf('%s\n\n', dessert.toString());
    
    % Test Drink
    fprintf('4. Testing Drink:\n');
    drink = Drink('Coca Cola', 'Classic cola beverage', 2.99, true);
    fprintf('%s\n\n', drink.toString());
    
    % Test SpecialtyDrink
    fprintf('5. Testing SpecialtyDrink:\n');
    specialty = SpecialtyDrink('Mango Lassi', 'Traditional Indian yogurt drink', 4.99, 'Curry dishes');
    fprintf('%s\n\n', specialty.toString());
    
    % Test equality
    fprintf('6. Testing equality:\n');
    appetizer2 = Appetizer('Buffalo Wings', 'Different description', 9.99, Appetizer.ONETWO);
    fprintf('appetizer == appetizer2: %s\n', mat2str(appetizer == appetizer2));
    
    appetizer3 = Appetizer('Mozzarella Sticks', 'Fried cheese sticks', 7.99, Appetizer.ONETWO);
    fprintf('appetizer == appetizer3: %s\n\n', mat2str(appetizer == appetizer3));
    
    % Test specialty drink refill override
    fprintf('7. Testing SpecialtyDrink refill override:\n');
    fprintf('Specialty drink refill before: %s\n', mat2str(specialty.refill_ok()));
    specialty.setRefill(true);  % This should do nothing
    fprintf('Specialty drink refill after setRefill(true): %s\n\n', mat2str(specialty.refill_ok()));
    
    fprintf('All tests completed successfully!\n');
end