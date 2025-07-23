classdef Drink < MenuItem
    % DRINK Class for drink menu items
    % Extends MenuItem with refill capability
    %
    % Author: MATLAB version adapted from Matthew Hino and Reece Teramoto's Java code
    % Version: 2025
    
    properties (Access = private)
        refill
    end
    
    methods
        function obj = Drink(name, description, price, refill)
            % Constructor for Drink
            obj@MenuItem(name, description, price);
            if nargin > 3
                obj.refill = refill;
            else
                obj.refill = true; % Default allows refills
            end
        end
        
        function refill = refill_ok(obj)
            refill = obj.refill;
        end
        
        function setRefill(obj, refill)
            obj.refill = refill;
        end
        
        function str = toString(obj)
            if obj.refill
                refillStr = 'Free Refill';
            else
                refillStr = 'No Free Refill';
            end
            str = sprintf('%-30s $%.2f   %s\nDescription: %s', ...
                obj.name, obj.price, refillStr, obj.description);
        end
    end
end