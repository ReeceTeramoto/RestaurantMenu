classdef Dessert < MenuItem
    % DESSERT Class for dessert menu items
    % Extends MenuItem with calorie information
    %
    % Author: MATLAB version adapted from Matthew Hino and Reece Teramoto's Java code
    % Version: 2025
    
    properties (Access = private)
        calories
    end
    
    methods
        function obj = Dessert(name, description, price, calories)
            % Constructor for Dessert
            obj@MenuItem(name, description, price);
            if nargin > 3
                obj.calories = calories;
            else
                obj.calories = 300; % Default calories
            end
        end
        
        function calories = getCalories(obj)
            calories = obj.calories;
        end
        
        function setCalories(obj, calories)
            obj.calories = calories;
        end
        
        function str = toString(obj)
            str = sprintf('%-30s $%.2f Calories: %d\nDescription: %s', ...
                obj.name, obj.price, obj.calories, obj.description);
        end
    end
end