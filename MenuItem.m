classdef MenuItem < handle
    % MENUITEM Abstract base class for all menu items
    % This class defines the common interface for all menu items
    % 
    % Author: MATLAB version adapted from Matthew Hino and Reece Teramoto's Java code
    % Version: 2025
    
    properties (Access = protected)
        name
        description
        price
    end
    
    methods
        function obj = MenuItem(name, description, price)
            % Constructor for MenuItem
            if nargin > 0
                obj.name = name;
                obj.description = description;
                obj.price = price;
            end
        end
        
        function name = getName(obj)
            name = obj.name;
        end
        
        function setName(obj, name)
            obj.name = name;
        end
        
        function description = getDescription(obj)
            description = obj.description;
        end
        
        function setDescription(obj, description)
            obj.description = description;
        end
        
        function price = getPrice(obj)
            price = obj.price;
        end
        
        function setPrice(obj, price)
            obj.price = price;
        end
        
        function result = eq(obj, other)
            % Override equality operator
            if isa(other, 'MenuItem')
                result = strcmp(obj.name, other.name);
            else
                result = false;
            end
        end
    end
    
    methods
        function str = toString(obj)
            % Default toString implementation
            str = sprintf('%-30s $%.2f\nDescription: %s', ...
                obj.name, obj.price, obj.description);
        end
    end
end