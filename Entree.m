classdef Entree < MenuItem
    % ENTREE Class for entree menu items
    % Extends MenuItem with mini price and category information
    %
    % Author: MATLAB version adapted from Matthew Hino and Reece Teramoto's Java code
    % Version: 2025
    
    properties (Constant)
        BEEF = 'Beef'
        CHICKEN = 'Chicken'
        FISH = 'Fish'
        PASTA = 'Pasta'
        VEGETARIAN = 'Vegetarian'
    end
    
    properties (Access = private)
        miniPrice
        category
    end
    
    methods
        function obj = Entree(name, description, price, miniPrice, category)
            % Constructor for Entree
            obj@MenuItem(name, description, price);
            if nargin > 3
                obj.miniPrice = miniPrice;
            else
                obj.miniPrice = price * 0.75; % Default mini price is 75% of full
            end
            if nargin > 4
                obj.category = category;
            else
                obj.category = Entree.CHICKEN;
            end
        end
        
        function miniPrice = getMiniPrice(obj)
            miniPrice = obj.miniPrice;
        end
        
        function setMiniPrice(obj, miniPrice)
            obj.miniPrice = miniPrice;
        end
        
        function category = getCategory(obj)
            category = obj.category;
        end
        
        function setCategory(obj, category)
            obj.category = category;
        end
        
        function str = toString(obj)
            str = sprintf('%-30s Full: $%.2f Mini: $%.2f\nDescription: %s\nCategory: %s', ...
                obj.name, obj.price, obj.miniPrice, obj.description, obj.category);
        end
    end
end