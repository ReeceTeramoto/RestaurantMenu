classdef RestaurantMenuApp < matlab.apps.AppBase

    % Properties that correspond to app components
    properties (Access = public)
        UIFigure                     matlab.ui.Figure
        GridLayout                   matlab.ui.container.GridLayout
        LeftPanel                    matlab.ui.container.Panel
        MenuItemsListBox             matlab.ui.control.ListBox
        MenuItemsListBoxLabel        matlab.ui.control.Label
        PrintMenuButton              matlab.ui.control.Button
        RightPanel                   matlab.ui.container.Panel
        ItemTypeDropDownLabel        matlab.ui.control.Label
        ItemTypeDropDown             matlab.ui.control.DropDown
        NameEditFieldLabel           matlab.ui.control.Label
        NameEditField                matlab.ui.control.EditField
        PriceEditFieldLabel          matlab.ui.control.Label
        PriceEditField               matlab.ui.control.NumericEditField
        DescriptionTextAreaLabel     matlab.ui.control.Label
        DescriptionTextArea          matlab.ui.control.TextArea
        
        % Item-specific panels
        AppetizerPanel               matlab.ui.container.Panel
        ServingSizeButtonGroup       matlab.ui.container.ButtonGroup
        ServingSize12Button          matlab.ui.control.RadioButton
        ServingSize34Button          matlab.ui.control.RadioButton
        ServingSize56Button          matlab.ui.control.RadioButton
        
        EntreePanel                  matlab.ui.container.Panel
        EntreeTypeDropDownLabel      matlab.ui.control.Label
        EntreeTypeDropDown           matlab.ui.control.DropDown
        MiniPriceEditFieldLabel      matlab.ui.control.Label
        MiniPriceEditField           matlab.ui.control.NumericEditField
        
        DessertPanel                 matlab.ui.container.Panel
        CaloriesEditFieldLabel       matlab.ui.control.Label
        CaloriesEditField            matlab.ui.control.NumericEditField
        
        DrinkPanel                   matlab.ui.container.Panel
        DrinkTypeButtonGroup         matlab.ui.container.ButtonGroup
        NormalDrinkButton            matlab.ui.control.RadioButton
        SpecialtyDrinkButton         matlab.ui.control.RadioButton
        PairingEditFieldLabel        matlab.ui.control.Label
        PairingEditField             matlab.ui.control.EditField
        
        AddButton                    matlab.ui.control.Button
        ClearButton                  matlab.ui.control.Button
    end

    % Properties for storing menu data
    properties (Access = private)
        MenuItems = {}  % Cell array of structs containing menu item data
    end

    % Callbacks that handle component events
    methods (Access = private)

        % Code that executes after component creation
        function startupFcn(app)
            % Initialize the app
            app.AppetizerPanel.Visible = 'on';
            app.EntreePanel.Visible = 'off';
            app.DessertPanel.Visible = 'off';
            app.DrinkPanel.Visible = 'off';
        end

        % Value changed function: ItemTypeDropDown
        function ItemTypeDropDownValueChanged(app, event)
            value = app.ItemTypeDropDown.Value;
            
            % Hide all panels first
            app.AppetizerPanel.Visible = 'off';
            app.EntreePanel.Visible = 'off';
            app.DessertPanel.Visible = 'off';
            app.DrinkPanel.Visible = 'off';
            
            % Show the relevant panel
            switch value
                case 'Appetizer'
                    app.AppetizerPanel.Visible = 'on';
                case 'Entree'
                    app.EntreePanel.Visible = 'on';
                case 'Dessert'
                    app.DessertPanel.Visible = 'on';
                case 'Drink'
                    app.DrinkPanel.Visible = 'on';
            end
        end

        % Button pushed function: AddButton
        function AddButtonPushed(app, event)
            % Validate basic fields
            if ~app.validateBasicFields()
                return;
            end
            
            itemType = app.ItemTypeDropDown.Value;
            
            switch itemType
                case 'Appetizer'
                    if app.validateAppetizer()
                        app.addAppetizer();
                    end
                case 'Entree'
                    if app.validateEntree()
                        app.addEntree();
                    end
                case 'Dessert'
                    if app.validateDessert()
                        app.addDessert();
                    end
                case 'Drink'
                    if app.validateDrink()
                        app.addDrink();
                    end
            end
        end

        % Button pushed function: ClearButton
        function ClearButtonPushed(app, event)
            app.clearAllFields();
        end

        % Button pushed function: PrintMenuButton
        function PrintMenuButtonPushed(app, event)
            app.printMenu();
        end
    end

    % Helper methods
    methods (Access = private)
        
        function valid = validateBasicFields(app)
            valid = true;
            name = strtrim(app.NameEditField.Value);
            description = strtrim(strjoin(app.DescriptionTextArea.Value, ' '));
            price = app.PriceEditField.Value;
            
            % Check name
            if isempty(name) || strcmp(name, 'Item Name')
                uialert(app.UIFigure, 'Name is invalid.', 'Error');
                valid = false;
                return;
            end
            
            % Check for duplicate names
            for i = 1:length(app.MenuItems)
                if strcmpi(app.MenuItems{i}.Name, name)
                    uialert(app.UIFigure, 'Cannot have two items with the same name.', 'Error');
                    valid = false;
                    return;
                end
            end
            
            % Check price
            if price <= 0
                uialert(app.UIFigure, 'Price must be a positive number.', 'Error');
                valid = false;
                return;
            end
            
            % Check description
            if isempty(description)
                uialert(app.UIFigure, 'Description is invalid.', 'Error');
                valid = false;
                return;
            end
        end
        
        function valid = validateAppetizer(app)
            valid = true;
            if isempty(app.ServingSizeButtonGroup.SelectedObject)
                uialert(app.UIFigure, 'Please select a serving size option.', 'Error');
                valid = false;
            end
        end
        
        function valid = validateEntree(app)
            valid = true;
            miniPrice = app.MiniPriceEditField.Value;
            price = app.PriceEditField.Value;
            
            if miniPrice >= price
                uialert(app.UIFigure, 'Mini-Price must be less than Price.', 'Error');
                valid = false;
            end
        end
        
        function valid = validateDessert(app)
            valid = true;
            calories = app.CaloriesEditField.Value;
            
            if calories < 0
                uialert(app.UIFigure, 'Calorie value must be positive.', 'Error');
                valid = false;
            end
        end
        
        function valid = validateDrink(app)
            valid = true;
            if isempty(app.DrinkTypeButtonGroup.SelectedObject)
                uialert(app.UIFigure, 'Please select Normal Drink or Specialty Drink.', 'Error');
                valid = false;
                return;
            end
            
            % If specialty drink, check pairing
            if strcmp(app.DrinkTypeButtonGroup.SelectedObject.Text, 'Specialty Drink')
                pairing = strtrim(app.PairingEditField.Value);
                if isempty(pairing) || strcmp(pairing, 'Pairing')
                    uialert(app.UIFigure, 'You must specify a paired dish.', 'Error');
                    valid = false;
                end
            end
        end
        
        function addAppetizer(app)
            item.Type = 'Appetizer';
            item.Name = strtrim(app.NameEditField.Value);
            item.Price = app.PriceEditField.Value;
            item.Description = strtrim(strjoin(app.DescriptionTextArea.Value, ' '));
            item.ServingSize = app.ServingSizeButtonGroup.SelectedObject.Text;
            
            app.MenuItems{end+1} = item;
            app.MenuItemsListBox.Items{end+1} = item.Name;
            uialert(app.UIFigure, 'Appetizer added successfully!', 'Success');
            app.clearAllFields();
        end
        
        function addEntree(app)
            item.Type = 'Entree';
            item.Name = strtrim(app.NameEditField.Value);
            item.Price = app.PriceEditField.Value;
            item.Description = strtrim(strjoin(app.DescriptionTextArea.Value, ' '));
            item.EntreeType = app.EntreeTypeDropDown.Value;
            item.MiniPrice = app.MiniPriceEditField.Value;
            
            app.MenuItems{end+1} = item;
            app.MenuItemsListBox.Items{end+1} = item.Name;
            uialert(app.UIFigure, 'Entree added successfully!', 'Success');
            app.clearAllFields();
        end
        
        function addDessert(app)
            item.Type = 'Dessert';
            item.Name = strtrim(app.NameEditField.Value);
            item.Price = app.PriceEditField.Value;
            item.Description = strtrim(strjoin(app.DescriptionTextArea.Value, ' '));
            item.Calories = app.CaloriesEditField.Value;
            
            app.MenuItems{end+1} = item;
            app.MenuItemsListBox.Items{end+1} = item.Name;
            uialert(app.UIFigure, 'Dessert added successfully!', 'Success');
            app.clearAllFields();
        end
        
        function addDrink(app)
            item.Type = 'Drink';
            item.Name = strtrim(app.NameEditField.Value);
            item.Price = app.PriceEditField.Value;
            item.Description = strtrim(strjoin(app.DescriptionTextArea.Value, ' '));
            
            if strcmp(app.DrinkTypeButtonGroup.SelectedObject.Text, 'Specialty Drink')
                item.DrinkType = 'Specialty';
                item.Pairing = strtrim(app.PairingEditField.Value);
                item.FreeRefill = false;
            else
                item.DrinkType = 'Normal';
                % Ask about free refill
                answer = uiconfirm(app.UIFigure, 'Does this drink have a free refill?', 'Free Refill?', ...
                    'Options', {'Yes', 'No'}, 'DefaultOption', 2);
                item.FreeRefill = strcmp(answer, 'Yes');
            end
            
            app.MenuItems{end+1} = item;
            app.MenuItemsListBox.Items{end+1} = item.Name;
            uialert(app.UIFigure, 'Drink added successfully!', 'Success');
            app.clearAllFields();
        end
        
        function clearAllFields(app)
            app.NameEditField.Value = '';
            app.PriceEditField.Value = 0;
            app.DescriptionTextArea.Value = '';
            
            % Clear appetizer fields
            if ~isempty(app.ServingSizeButtonGroup.SelectedObject)
                app.ServingSizeButtonGroup.SelectedObject = [];
            end
            
            % Clear entree fields
            app.EntreeTypeDropDown.Value = 'Chicken';
            app.MiniPriceEditField.Value = 0;
            
            % Clear dessert fields
            app.CaloriesEditField.Value = 0;
            
            % Clear drink fields
            if ~isempty(app.DrinkTypeButtonGroup.SelectedObject)
                app.DrinkTypeButtonGroup.SelectedObject = [];
            end
            app.PairingEditField.Value = '';
            
            % Reset to appetizer view
            app.ItemTypeDropDown.Value = 'Appetizer';
            app.ItemTypeDropDownValueChanged([]);
        end
        
        function printMenu(app)
            if isempty(app.MenuItems)
                uialert(app.UIFigure, 'No items to print. Add items first.', 'Error');
                return;
            end
            
            % Create output file
            filename = fullfile(pwd, 'Menu.txt');
            fid = fopen(filename, 'w');
            
            % Print appetizers
            fprintf(fid, '~~~~~~APPETIZERS~~~~~~~\n');
            for i = 1:length(app.MenuItems)
                if strcmp(app.MenuItems{i}.Type, 'Appetizer')
                    fprintf(fid, '%s\t\t\t$%.2f\n', app.MenuItems{i}.Name, app.MenuItems{i}.Price);
                    fprintf(fid, 'Description: %s\tServing Size: %s\n\n', ...
                        app.MenuItems{i}.Description, app.MenuItems{i}.ServingSize);
                end
            end
            
            % Print entrees
            fprintf(fid, '\n\n~~~~~~ENTREES~~~~~~~\n');
            for i = 1:length(app.MenuItems)
                if strcmp(app.MenuItems{i}.Type, 'Entree')
                    fprintf(fid, '%s\t\t\tFull: $%.2f Mini: $%.2f\n', ...
                        app.MenuItems{i}.Name, app.MenuItems{i}.Price, app.MenuItems{i}.MiniPrice);
                    fprintf(fid, 'Description: %s\nCategory: %s\n\n', ...
                        app.MenuItems{i}.Description, app.MenuItems{i}.EntreeType);
                end
            end
            
            % Print desserts
            fprintf(fid, '\n\n~~~~~~DESSERTS~~~~~~~\n');
            for i = 1:length(app.MenuItems)
                if strcmp(app.MenuItems{i}.Type, 'Dessert')
                    fprintf(fid, '%s\t\t\t$%.2f Calories: %d\n', ...
                        app.MenuItems{i}.Name, app.MenuItems{i}.Price, app.MenuItems{i}.Calories);
                    fprintf(fid, 'Description: %s\n\n', app.MenuItems{i}.Description);
                end
            end
            
            % Print normal drinks
            fprintf(fid, '\n\n~~~~~~NORMAL DRINKS~~~~~~~\n');
            for i = 1:length(app.MenuItems)
                if strcmp(app.MenuItems{i}.Type, 'Drink') && strcmp(app.MenuItems{i}.DrinkType, 'Normal')
                    refillStr = 'No Free Refill';
                    if app.MenuItems{i}.FreeRefill
                        refillStr = 'Free Refill';
                    end
                    fprintf(fid, '%s\t\t\t$%.2f\t%s\n', ...
                        app.MenuItems{i}.Name, app.MenuItems{i}.Price, refillStr);
                    fprintf(fid, 'Description: %s\n\n', app.MenuItems{i}.Description);
                end
            end
            
            % Print specialty drinks
            fprintf(fid, '\n\n~~~~~~SPECIALTY DRINKS~~~~~~~\n');
            for i = 1:length(app.MenuItems)
                if strcmp(app.MenuItems{i}.Type, 'Drink') && strcmp(app.MenuItems{i}.DrinkType, 'Specialty')
                    fprintf(fid, '%s\t\t\t$%.2f\tNo Free Refill\n', ...
                        app.MenuItems{i}.Name, app.MenuItems{i}.Price);
                    fprintf(fid, 'Description: %s\nPaired with: %s\n\n', ...
                        app.MenuItems{i}.Description, app.MenuItems{i}.Pairing);
                end
            end
            
            fclose(fid);
            
            uialert(app.UIFigure, sprintf('Menu saved to: %s', filename), 'Success');
        end
    end

    % Component initialization
    methods (Access = private)

        % Create UIFigure and components
        function createComponents(app)

            % Create UIFigure and hide until all components are created
            app.UIFigure = uifigure('Visible', 'off');
            app.UIFigure.Position = [100 100 900 600];
            app.UIFigure.Name = 'Restaurant Menu Manager';

            % Create GridLayout
            app.GridLayout = uigridlayout(app.UIFigure);
            app.GridLayout.ColumnWidth = {'1x', '3x'};
            app.GridLayout.RowHeight = {'1x'};

            % Create LeftPanel
            app.LeftPanel = uipanel(app.GridLayout);
            app.LeftPanel.Title = 'Menu Items';
            app.LeftPanel.Layout.Row = 1;
            app.LeftPanel.Layout.Column = 1;

            % Create MenuItemsListBoxLabel
            app.MenuItemsListBoxLabel = uilabel(app.LeftPanel);
            app.MenuItemsListBoxLabel.Position = [10 530 100 22];
            app.MenuItemsListBoxLabel.Text = 'Added Items:';

            % Create MenuItemsListBox
            app.MenuItemsListBox = uilistbox(app.LeftPanel);
            app.MenuItemsListBox.Items = {};
            app.MenuItemsListBox.Position = [10 70 180 450];

            % Create PrintMenuButton
            app.PrintMenuButton = uibutton(app.LeftPanel, 'push');
            app.PrintMenuButton.ButtonPushedFcn = createCallbackFcn(app, @PrintMenuButtonPushed, true);
            app.PrintMenuButton.Position = [10 20 180 40];
            app.PrintMenuButton.Text = 'Print Menu';

            % Create RightPanel
            app.RightPanel = uipanel(app.GridLayout);
            app.RightPanel.Title = 'Add Menu Item';
            app.RightPanel.Layout.Row = 1;
            app.RightPanel.Layout.Column = 2;

            % Create ItemTypeDropDownLabel
            app.ItemTypeDropDownLabel = uilabel(app.RightPanel);
            app.ItemTypeDropDownLabel.Position = [20 530 70 22];
            app.ItemTypeDropDownLabel.Text = 'Item Type:';

            % Create ItemTypeDropDown
            app.ItemTypeDropDown = uidropdown(app.RightPanel);
            app.ItemTypeDropDown.Items = {'Appetizer', 'Entree', 'Dessert', 'Drink'};
            app.ItemTypeDropDown.ValueChangedFcn = createCallbackFcn(app, @ItemTypeDropDownValueChanged, true);
            app.ItemTypeDropDown.Position = [100 530 100 22];
            app.ItemTypeDropDown.Value = 'Appetizer';

            % Create NameEditFieldLabel
            app.NameEditFieldLabel = uilabel(app.RightPanel);
            app.NameEditFieldLabel.Position = [220 530 50 22];
            app.NameEditFieldLabel.Text = 'Name:';

            % Create NameEditField
            app.NameEditField = uieditfield(app.RightPanel, 'text');
            app.NameEditField.Position = [280 530 150 22];

            % Create PriceEditFieldLabel
            app.PriceEditFieldLabel = uilabel(app.RightPanel);
            app.PriceEditFieldLabel.Position = [450 530 50 22];
            app.PriceEditFieldLabel.Text = 'Price ($):';

            % Create PriceEditField
            app.PriceEditField = uieditfield(app.RightPanel, 'numeric');
            app.PriceEditField.Position = [510 530 80 22];

            % Create DescriptionTextAreaLabel
            app.DescriptionTextAreaLabel = uilabel(app.RightPanel);
            app.DescriptionTextAreaLabel.Position = [20 470 200 22];
            app.DescriptionTextAreaLabel.Text = 'Description (80 Char Max):';

            % Create DescriptionTextArea
            app.DescriptionTextArea = uitextarea(app.RightPanel);
            app.DescriptionTextArea.Position = [20 410 570 50];

            % Create AppetizerPanel
            app.AppetizerPanel = uipanel(app.RightPanel);
            app.AppetizerPanel.Title = 'Appetizer Details';
            app.AppetizerPanel.Position = [20 250 270 150];

            % Create ServingSizeButtonGroup
            app.ServingSizeButtonGroup = uibuttongroup(app.AppetizerPanel);
            app.ServingSizeButtonGroup.Title = 'Serving Size:';
            app.ServingSizeButtonGroup.Position = [10 10 240 100];

            % Create ServingSize12Button
            app.ServingSize12Button = uiradiobutton(app.ServingSizeButtonGroup);
            app.ServingSize12Button.Text = '1-2';
            app.ServingSize12Button.Position = [10 60 60 22];

            % Create ServingSize34Button
            app.ServingSize34Button = uiradiobutton(app.ServingSizeButtonGroup);
            app.ServingSize34Button.Text = '3-4';
            app.ServingSize34Button.Position = [10 35 60 22];

            % Create ServingSize56Button
            app.ServingSize56Button = uiradiobutton(app.ServingSizeButtonGroup);
            app.ServingSize56Button.Text = '5-6';
            app.ServingSize56Button.Position = [10 10 60 22];

            % Create EntreePanel
            app.EntreePanel = uipanel(app.RightPanel);
            app.EntreePanel.Title = 'Entree Details';
            app.EntreePanel.Position = [310 250 270 150];
            app.EntreePanel.Visible = 'off';

            % Create EntreeTypeDropDownLabel
            app.EntreeTypeDropDownLabel = uilabel(app.EntreePanel);
            app.EntreeTypeDropDownLabel.Position = [10 90 50 22];
            app.EntreeTypeDropDownLabel.Text = 'Type:';

            % Create EntreeTypeDropDown
            app.EntreeTypeDropDown = uidropdown(app.EntreePanel);
            app.EntreeTypeDropDown.Items = {'Chicken', 'Fish', 'Meat'};
            app.EntreeTypeDropDown.Position = [70 90 120 22];
            app.EntreeTypeDropDown.Value = 'Chicken';

            % Create MiniPriceEditFieldLabel
            app.MiniPriceEditFieldLabel = uilabel(app.EntreePanel);
            app.MiniPriceEditFieldLabel.Position = [10 50 80 22];
            app.MiniPriceEditFieldLabel.Text = 'Mini-Price ($):';

            % Create MiniPriceEditField
            app.MiniPriceEditField = uieditfield(app.EntreePanel, 'numeric');
            app.MiniPriceEditField.Position = [100 50 90 22];

            % Create DessertPanel
            app.DessertPanel = uipanel(app.RightPanel);
            app.DessertPanel.Title = 'Dessert Details';
            app.DessertPanel.Position = [20 80 270 150];
            app.DessertPanel.Visible = 'off';

            % Create CaloriesEditFieldLabel
            app.CaloriesEditFieldLabel = uilabel(app.DessertPanel);
            app.CaloriesEditFieldLabel.Position = [10 90 60 22];
            app.CaloriesEditFieldLabel.Text = 'Calories:';

            % Create CaloriesEditField
            app.CaloriesEditField = uieditfield(app.DessertPanel, 'numeric');
            app.CaloriesEditField.Position = [80 90 100 22];

            % Create DrinkPanel
            app.DrinkPanel = uipanel(app.RightPanel);
            app.DrinkPanel.Title = 'Drink Details';
            app.DrinkPanel.Position = [310 80 270 150];
            app.DrinkPanel.Visible = 'off';

            % Create DrinkTypeButtonGroup
            app.DrinkTypeButtonGroup = uibuttongroup(app.DrinkPanel);
            app.DrinkTypeButtonGroup.Title = 'Drink Type:';
            app.DrinkTypeButtonGroup.Position = [10 50 240 70];

            % Create NormalDrinkButton
            app.NormalDrinkButton = uiradiobutton(app.DrinkTypeButtonGroup);
            app.NormalDrinkButton.Text = 'Normal Drink';
            app.NormalDrinkButton.Position = [10 30 120 22];

            % Create SpecialtyDrinkButton
            app.SpecialtyDrinkButton = uiradiobutton(app.DrinkTypeButtonGroup);
            app.SpecialtyDrinkButton.Text = 'Specialty Drink';
            app.SpecialtyDrinkButton.Position = [10 5 120 22];

            % Create PairingEditFieldLabel
            app.PairingEditFieldLabel = uilabel(app.DrinkPanel);
            app.PairingEditFieldLabel.Position = [10 20 120 22];
            app.PairingEditFieldLabel.Text = 'Pairing (Specialty):';

            % Create PairingEditField
            app.PairingEditField = uieditfield(app.DrinkPanel, 'text');
            app.PairingEditField.Position = [140 20 110 22];

            % Create AddButton
            app.AddButton = uibutton(app.RightPanel, 'push');
            app.AddButton.ButtonPushedFcn = createCallbackFcn(app, @AddButtonPushed, true);
            app.AddButton.Position = [490 20 100 40];
            app.AddButton.Text = 'Add';
            app.AddButton.FontSize = 14;
            app.AddButton.FontWeight = 'bold';

            % Create ClearButton
            app.ClearButton = uibutton(app.RightPanel, 'push');
            app.ClearButton.ButtonPushedFcn = createCallbackFcn(app, @ClearButtonPushed, true);
            app.ClearButton.Position = [370 20 100 40];
            app.ClearButton.Text = 'Clear';

            % Show the figure after all components are created
            app.UIFigure.Visible = 'on';
        end
    end

    % App creation and deletion
    methods (Access = public)

        % Construct app
        function app = RestaurantMenuApp

            % Create UIFigure and components
            createComponents(app)

            % Register the app with App Designer
            registerApp(app, app.UIFigure)

            % Execute the startup function
            runStartupFcn(app, @startupFcn)

            if nargout == 0
                clear app
            end
        end

        % Code that executes before app deletion
        function delete(app)

            % Delete UIFigure when app is deleted
            delete(app.UIFigure)
        end
    end
end
