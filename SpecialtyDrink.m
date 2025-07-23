classdef SpecialtyDrink < Drink
    % SPECIALTYDRINK Class for specialty drink menu items
    % Extends Drink with pairing suggestions and overrides refill behavior
    %
    % Author: MATLAB version adapted from Matthew Hino and Reece Teramoto's Java code
    % Version: 2025
    
    properties (Access = private)
        pairsWith
    end
    
    methods
        function obj = SpecialtyDrink(name, description, price, pairsWith)
            % Constructor for SpecialtyDrink
            % Specialty drinks never allow refills
            obj@Drink(name, description, price, false);
            if nargin > 3
                obj.pairsWith = pairsWith;
            else
                obj.pairsWith = 'Any dish';
            end
        end
        
        function pairsWith = getPairsWith(obj)
            pairsWith = obj.pairsWith;
        end
        
        function setPairsWith(obj, pairsWith)
            obj.pairsWith = pairsWith;
        end
        
        function setRefill(obj, ~)
            % Override: Specialty drinks never allow refills
            % This method intentionally does nothing
        end
        
        function refill = refill_ok(obj)
            % Override: Always return false for specialty drinks
            refill = false;
        end
        
        function str = toString(obj)
            str = sprintf('%-30s $%.2f   No Free Refill\nDescription: %s', ...
                obj.name, obj.price, obj.description);
        end
    end
end