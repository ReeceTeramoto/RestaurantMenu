classdef RestaurantMenuApp < handle
    % RESTAURANTMENUAPP MATLAB GUI application for restaurant menu management
    % This class creates a GUI interface for managing restaurant menu items
    %
    % Author: MATLAB version adapted from Matthew Hino and Reece Teramoto's Java code
    % Version: 2025
    
    properties (Access = private)
        fig
        menuItems
        
        % GUI Components
        itemTypeDropdown
        nameField
        descriptionField
        priceField
        
        % Dynamic fields that change based on item type
        dynamicPanel
        dynamicFields
        
        addButton
        clearButton
        saveButton
        menuList
        
        % Labels for dynamic fields
        dynamicLabels
    end
    
    methods
        function obj = RestaurantMenuApp()
            % Constructor - creates the GUI
            obj.menuItems = {};
            obj.dynamicFields = {};
            obj.dynamicLabels = {};
            obj.createGUI();
        end
        
        function createGUI(obj)
            % Create the main GUI window
            obj.fig = figure('Name', 'Restaurant Menu Management System - MATLAB', ...
                           'Position', [100, 100, 800, 600], ...
                           'Resize', 'off', ...
                           'MenuBar', 'none', ...
                           'ToolBar', 'none');
            
            % Title
            uicontrol('Style', 'text', ...
                     'String', 'Restaurant Menu Management System', ...
                     'Position', [250, 550, 300, 30], ...
                     'FontSize', 14, ...
                     'FontWeight', 'bold');
            
            % Item Type Selection
            uicontrol('Style', 'text', ...
                     'String', 'Item Type:', ...
                     'Position', [50, 500, 80, 20], ...
                     'HorizontalAlignment', 'left');
            
            obj.itemTypeDropdown = uicontrol('Style', 'popupmenu', ...
                                           'String', {'Appetizer', 'Entree', 'Dessert', 'Drink', 'SpecialtyDrink'}, ...
                                           'Position', [140, 500, 150, 25], ...
                                           'Callback', @obj.onItemTypeChanged);
            
            % Basic Fields
            uicontrol('Style', 'text', ...
                     'String', 'Name:', ...
                     'Position', [50, 460, 80, 20], ...
                     'HorizontalAlignment', 'left');
            
            obj.nameField = uicontrol('Style', 'edit', ...
                                    'Position', [140, 460, 200, 25]);
            
            uicontrol('Style', 'text', ...
                     'String', 'Description:', ...
                     'Position', [50, 420, 80, 20], ...
                     'HorizontalAlignment', 'left');
            
            obj.descriptionField = uicontrol('Style', 'edit', ...
                                           'Position', [140, 420, 200, 25]);
            
            uicontrol('Style', 'text', ...
                     'String', 'Price ($):', ...
                     'Position', [50, 380, 80, 20], ...
                     'HorizontalAlignment', 'left');
            
            obj.priceField = uicontrol('Style', 'edit', ...
                                     'Position', [140, 380, 100, 25]);
            
            % Dynamic panel for item-specific fields
            obj.dynamicPanel = uipanel('Position', [0.06, 0.35, 0.44, 0.25], ...
                                     'Title', 'Item-Specific Fields');
            
            % Buttons
            obj.addButton = uicontrol('Style', 'pushbutton', ...
                                    'String', 'Add Item', ...
                                    'Position', [50, 50, 100, 30], ...
                                    'Callback', @obj.onAddItem);
            
            obj.clearButton = uicontrol('Style', 'pushbutton', ...
                                      'String', 'Clear', ...
                                      'Position', [170, 50, 100, 30], ...
                                      'Callback', @obj.onClear);
            
            obj.saveButton = uicontrol('Style', 'pushbutton', ...
                                     'String', 'Save Menu', ...
                                     'Position', [290, 50, 100, 30], ...
                                     'Callback', @obj.onSaveMenu);
            
            % Menu List
            uicontrol('Style', 'text', ...
                     'String', 'Menu Items:', ...
                     'Position', [450, 500, 100, 20], ...
                     'HorizontalAlignment', 'left');
            
            obj.menuList = uicontrol('Style', 'listbox', ...
                                   'Position', [450, 100, 300, 400], ...
                                   'Max', 1);
            
            % Initialize dynamic fields for Appetizer (default)
            obj.onItemTypeChanged();
        end
        
        function onItemTypeChanged(obj, ~, ~)
            % Handle item type selection change
            
            % Clear existing dynamic fields
            for i = 1:length(obj.dynamicFields)
                if isvalid(obj.dynamicFields{i})
                    delete(obj.dynamicFields{i});
                end
            end
            for i = 1:length(obj.dynamicLabels)
                if isvalid(obj.dynamicLabels{i})
                    delete(obj.dynamicLabels{i});
                end
            end
            obj.dynamicFields = {};
            obj.dynamicLabels = {};
            
            % Get selected item type
            itemTypes = {'Appetizer', 'Entree', 'Dessert', 'Drink', 'SpecialtyDrink'};
            selectedType = itemTypes{obj.itemTypeDropdown.Value};
            
            % Create appropriate dynamic fields
            switch selectedType
                case 'Appetizer'
                    obj.createAppetizerFields();
                case 'Entree'
                    obj.createEntreeFields();
                case 'Dessert'
                    obj.createDessertFields();
                case 'Drink'
                    obj.createDrinkFields();
                case 'SpecialtyDrink'
                    obj.createSpecialtyDrinkFields();
            end
        end
        
        function createAppetizerFields(obj)
            % Create fields specific to Appetizer
            label = uicontrol('Parent', obj.dynamicPanel, ...
                            'Style', 'text', ...
                            'String', 'Serving Size:', ...
                            'Position', [10, 80, 100, 20], ...
                            'HorizontalAlignment', 'left');
            obj.dynamicLabels{end+1} = label;
            
            field = uicontrol('Parent', obj.dynamicPanel, ...
                            'Style', 'popupmenu', ...
                            'String', {'1-2', '3-4', '5-6', 'Large Group'}, ...
                            'Position', [120, 80, 120, 25]);
            obj.dynamicFields{end+1} = field;
        end
        
        function createEntreeFields(obj)
            % Create fields specific to Entree
            label1 = uicontrol('Parent', obj.dynamicPanel, ...
                             'Style', 'text', ...
                             'String', 'Mini Price ($):', ...
                             'Position', [10, 100, 100, 20], ...
                             'HorizontalAlignment', 'left');
            obj.dynamicLabels{end+1} = label1;
            
            field1 = uicontrol('Parent', obj.dynamicPanel, ...
                             'Style', 'edit', ...
                             'Position', [120, 100, 100, 25]);
            obj.dynamicFields{end+1} = field1;
            
            label2 = uicontrol('Parent', obj.dynamicPanel, ...
                             'Style', 'text', ...
                             'String', 'Category:', ...
                             'Position', [10, 60, 100, 20], ...
                             'HorizontalAlignment', 'left');
            obj.dynamicLabels{end+1} = label2;
            
            field2 = uicontrol('Parent', obj.dynamicPanel, ...
                             'Style', 'popupmenu', ...
                             'String', {'Beef', 'Chicken', 'Fish', 'Pasta', 'Vegetarian'}, ...
                             'Position', [120, 60, 120, 25]);
            obj.dynamicFields{end+1} = field2;
        end
        
        function createDessertFields(obj)
            % Create fields specific to Dessert
            label = uicontrol('Parent', obj.dynamicPanel, ...
                            'Style', 'text', ...
                            'String', 'Calories:', ...
                            'Position', [10, 80, 100, 20], ...
                            'HorizontalAlignment', 'left');
            obj.dynamicLabels{end+1} = label;
            
            field = uicontrol('Parent', obj.dynamicPanel, ...
                            'Style', 'edit', ...
                            'Position', [120, 80, 100, 25]);
            obj.dynamicFields{end+1} = field;
        end
        
        function createDrinkFields(obj)
            % Create fields specific to Drink
            label = uicontrol('Parent', obj.dynamicPanel, ...
                            'Style', 'text', ...
                            'String', 'Free Refill:', ...
                            'Position', [10, 80, 100, 20], ...
                            'HorizontalAlignment', 'left');
            obj.dynamicLabels{end+1} = label;
            
            field = uicontrol('Parent', obj.dynamicPanel, ...
                            'Style', 'checkbox', ...
                            'Value', 1, ...
                            'Position', [120, 80, 20, 20]);
            obj.dynamicFields{end+1} = field;
        end
        
        function createSpecialtyDrinkFields(obj)
            % Create fields specific to SpecialtyDrink
            label = uicontrol('Parent', obj.dynamicPanel, ...
                            'Style', 'text', ...
                            'String', 'Pairs With:', ...
                            'Position', [10, 80, 100, 20], ...
                            'HorizontalAlignment', 'left');
            obj.dynamicLabels{end+1} = label;
            
            field = uicontrol('Parent', obj.dynamicPanel, ...
                            'Style', 'edit', ...
                            'Position', [120, 80, 150, 25]);
            obj.dynamicFields{end+1} = field;
        end
        
        function onAddItem(obj, ~, ~)
            % Handle add item button click
            try
                % Get basic field values
                name = obj.nameField.String;
                description = obj.descriptionField.String;
                priceStr = obj.priceField.String;
                
                % Validate basic fields
                if isempty(name) || isempty(description) || isempty(priceStr)
                    msgbox('Please fill in all required fields.', 'Error', 'error');
                    return;
                end
                
                price = str2double(priceStr);
                if isnan(price) || price <= 0
                    msgbox('Please enter a valid price.', 'Error', 'error');
                    return;
                end
                
                % Create appropriate menu item based on type
                itemTypes = {'Appetizer', 'Entree', 'Dessert', 'Drink', 'SpecialtyDrink'};
                selectedType = itemTypes{obj.itemTypeDropdown.Value};
                
                menuItem = obj.createMenuItem(selectedType, name, description, price);
                
                if ~isempty(menuItem)
                    obj.menuItems{end+1} = menuItem;
                    obj.updateMenuList();
                    obj.onClear();
                    msgbox('Item added successfully!', 'Success', 'help');
                end
                
            catch ME
                msgbox(['Error adding item: ' ME.message], 'Error', 'error');
            end
        end
        
        function menuItem = createMenuItem(obj, itemType, name, description, price)
            % Create a menu item based on the selected type
            menuItem = [];
            
            switch itemType
                case 'Appetizer'
                    servingSizes = {'1-2', '3-4', '5-6', 'Large Group'};
                    servingSize = servingSizes{obj.dynamicFields{1}.Value};
                    menuItem = Appetizer(name, description, price, servingSize);
                    
                case 'Entree'
                    miniPriceStr = obj.dynamicFields{1}.String;
                    if isempty(miniPriceStr)
                        miniPrice = price * 0.75;
                    else
                        miniPrice = str2double(miniPriceStr);
                        if isnan(miniPrice)
                            msgbox('Please enter a valid mini price.', 'Error', 'error');
                            return;
                        end
                    end
                    
                    categories = {'Beef', 'Chicken', 'Fish', 'Pasta', 'Vegetarian'};
                    category = categories{obj.dynamicFields{2}.Value};
                    menuItem = Entree(name, description, price, miniPrice, category);
                    
                case 'Dessert'
                    caloriesStr = obj.dynamicFields{1}.String;
                    if isempty(caloriesStr)
                        calories = 300;
                    else
                        calories = str2double(caloriesStr);
                        if isnan(calories)
                            msgbox('Please enter valid calories.', 'Error', 'error');
                            return;
                        end
                    end
                    menuItem = Dessert(name, description, price, calories);
                    
                case 'Drink'
                    refill = logical(obj.dynamicFields{1}.Value);
                    menuItem = Drink(name, description, price, refill);
                    
                case 'SpecialtyDrink'
                    pairsWith = obj.dynamicFields{1}.String;
                    if isempty(pairsWith)
                        pairsWith = 'Any dish';
                    end
                    menuItem = SpecialtyDrink(name, description, price, pairsWith);
            end
        end
        
        function updateMenuList(obj)
            % Update the menu list display
            listItems = {};
            for i = 1:length(obj.menuItems)
                item = obj.menuItems{i};
                listItems{i} = sprintf('%s - $%.2f', item.getName(), item.getPrice());
            end
            obj.menuList.String = listItems;
        end
        
        function onClear(obj, ~, ~)
            % Clear all input fields
            obj.nameField.String = '';
            obj.descriptionField.String = '';
            obj.priceField.String = '';
            
            % Clear dynamic fields
            for i = 1:length(obj.dynamicFields)
                if strcmp(obj.dynamicFields{i}.Style, 'edit')
                    obj.dynamicFields{i}.String = '';
                elseif strcmp(obj.dynamicFields{i}.Style, 'checkbox')
                    obj.dynamicFields{i}.Value = 1;
                elseif strcmp(obj.dynamicFields{i}.Style, 'popupmenu')
                    obj.dynamicFields{i}.Value = 1;
                end
            end
        end
        
        function onSaveMenu(obj, ~, ~)
            % Save menu to file
            try
                if isempty(obj.menuItems)
                    msgbox('No menu items to save.', 'Warning', 'warn');
                    return;
                end
                
                filename = 'Menu_MATLAB.txt';
                fid = fopen(filename, 'w');
                
                if fid == -1
                    msgbox('Error opening file for writing.', 'Error', 'error');
                    return;
                end
                
                fprintf(fid, 'Restaurant Menu - MATLAB Version\n');
                fprintf(fid, '================================\n\n');
                
                for i = 1:length(obj.menuItems)
                    fprintf(fid, '%s\n\n', obj.menuItems{i}.toString());
                end
                
                fclose(fid);
                msgbox(['Menu saved to ' filename], 'Success', 'help');
                
            catch ME
                msgbox(['Error saving menu: ' ME.message], 'Error', 'error');
            end
        end
    end
end