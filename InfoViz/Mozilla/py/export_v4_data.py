# -*- coding: utf-8 -*-
# export data for v4

import pandas as pd
import json

survey_data = pd.read_csv("survey.csv")

v4_data = survey_data.groupby(['Q1', 'Q5']).size().reset_index()
v4_data_list = map(list, v4_data.values)

with open("v4_data_1.json", 'w') as f:
    json.dump(v4_data_list, f)

v4_data = survey_data.groupby(['Q1', 'Q3']).size().reset_index()
v4_data_list = map(list, v4_data.values)

with open("v4_data_2.json", 'w') as f:
    json.dump(v4_data_list, f)
