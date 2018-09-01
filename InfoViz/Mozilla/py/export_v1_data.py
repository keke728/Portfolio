# -*- coding: utf-8 -*-
# export data for v1

import pandas as pd

survey_data = pd.read_csv("survey.csv")
# print(survey_data)

v1_data = survey_data.groupby(['Country', 'Q1']).size()
v1_data = v1_data.unstack(level = 1, fill_value = 0)
v1_data['Q1 total'] = v1_data['Average User'] + v1_data['Luddite'] + v1_data['Technically Savvy'] + v1_data['Ultra Nerd']
v1_data['Average User ratio'] = v1_data['Average User'] / v1_data['Q1 total']
v1_data['Luddite ratio'] = v1_data['Luddite'] / v1_data['Q1 total']
v1_data['Technically Savvy ratio'] = v1_data['Technically Savvy'] / v1_data['Q1 total']
v1_data['Ultra Nerd ratio'] = v1_data['Ultra Nerd'] / v1_data['Q1 total']

v1_data.to_csv("v1_data.csv")
