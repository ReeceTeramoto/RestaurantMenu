classdef Appetizer < MenuItem
    % APPETIZER Class for appetizer menu items
    % Extends MenuItem with serving size information
    %
    % Author: MATLAB version adapted from Matthew Hino and Reece Teramoto's Java code
    % Version: 2025
    
    properties (Constant)
        ONETWO = '1-2'
        THREEFOUR = '3-4'
        FIVESIX = '5-6'
        LARGE = 'Large Group'
    end
    
    properties (Access = private)
        servingSize
    end
    
    methods
        function obj = Appetizer(name, description, price, servingSize)
            % Constructor for Appetizer
            obj@MenuItem(name, description, price);
            if nargin > 3
                obj.servingSize = servingSize;
            else
                obj.servingSize = Appetizer.ONETWO;
            end
        end
        
        function servingSize = getServingSize(obj)
            servingSize = obj.servingSize;
        end
        
        function setServingSize(obj, servingSize)
            obj.servingSize = servingSize;
        end
        
        function str = toString(obj)
            str = sprintf('%-30s $%.2f\nDescription: %-40s Serving Size: %s', ...
                obj.name, obj.price, obj.description, obj.servingSize);
        end
    end
end