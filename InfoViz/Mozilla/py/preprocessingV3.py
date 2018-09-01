# csv file preprocessing

import pandas as pd
import math

Moz_Data = pd.read_excel("vis3_data.xlsx")


def vis_3():

    # VISUALIZATION 3
    Moz_Data_fill = Moz_Data.fillna(0)
    vis_3_attr = ["Country", "Q8-1"]
    vis_3 = Moz_Data_fill.groupby(["Country","Q8-1"])["Country"].count()
    vis_3 = vis_3.unstack(level = 1, fill_value = 0)
    #compute total
    #vis_2['Q5 total'] = vis_2['I have no fears about a more connected future'] + vis_2['Other (please specify)'] + vis_2['The loss of privacy'] + vis_2['less safe'] + vis_2['lose touch']
    # compute rate (%)
    #vis_2['no fear ratio'] = vis_2['I have no fears about a more connected future'] / vis_2['Q5 total']
    #vis_2['other ratio'] = vis_2['Other (please specify)'] / vis_2['Q5 total']
    #vis_2['The loss of privacy ratio'] = vis_2['The loss of privacy'] / vis_2['Q5 total']
    #vis_2['less safe ratio'] = vis_2['less safe'] / vis_2['Q5 total']
    #vis_2['lose touch ratio'] = vis_2['lose touch'] / vis_2['Q5 total']

    
    vis_3 = vis_3.fillna(0) # remove 2016-2017 NaN
    vis_3.to_csv("vis_31.csv")



if __name__ == "__main__":

    vis_3()