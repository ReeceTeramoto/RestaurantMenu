#!/usr/bin/env python3
"""
Simple launcher script for the Restaurant Menu Management System

@author Python version adapted from Matthew Hino and Reece Teramoto's Java code
@version 2025
"""

import sys
import os

# Add current directory to path to ensure imports work
sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))

from restaurant_menu_app import main

if __name__ == "__main__":
    main()