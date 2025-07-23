function run_matlab_app()
    % RUN_MATLAB_APP Launch the Restaurant Menu Management System
    % This script starts the MATLAB GUI application
    %
    % Author: MATLAB version adapted from Matthew Hino and Reece Teramoto's Java code
    % Version: 2025
    
    fprintf('Starting Restaurant Menu Management System - MATLAB Version...\n');
    
    try
        % Create and run the application
        app = RestaurantMenuApp();
        
        fprintf('GUI application started successfully!\n');
        fprintf('Close the window to exit the application.\n');
        
    catch ME
        fprintf('Error starting application: %s\n', ME.message);
        rethrow(ME);
    end
end