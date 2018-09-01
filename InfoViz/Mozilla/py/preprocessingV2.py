# csv file preprocessing

import pandas as pd
import math

Moz_Data = pd.read_excel("vis2.xlsx")


def vis_2():

    # VISUALIZATION 1
    # export
    #Moz_Data_fill = Moz_Data.fillna("no response")
    vis_2 = Moz_Data.groupby(["Q1","Country","Q5"])["Country"].size()
    vis_2 = vis_2.unstack(level = 2, fill_value = 0)
    #compute total
    vis_2['Q5 total'] = vis_2['I have no fears about a more connected future'] + vis_2['Other (please specify)'] + vis_2['The loss of privacy'] + vis_2['less safe'] + vis_2['lose touch']
    # compute rate (%)
    vis_2['no fear ratio'] = vis_2['I have no fears about a more connected future'] / vis_2['Q5 total']
    vis_2['other ratio'] = vis_2['Other (please specify)'] / vis_2['Q5 total']
    vis_2['The loss of privacy ratio'] = vis_2['The loss of privacy'] / vis_2['Q5 total']
    vis_2['less safe ratio'] = vis_2['less safe'] / vis_2['Q5 total']
    vis_2['lose touch ratio'] = vis_2['lose touch'] / vis_2['Q5 total']

    
    #vis_2 = vis_2.fillna(0) # remove 2016-2017 NaN
    vis_2.to_csv("vis_2.csv")

    

'''def vis_3():

    # VISUALIZATION 3
    # attributes
    vis_3_Female_attr = ["Totals, Female: Graduated (Tot. F)", "Totals, Female: Left Institution (not graduated) (Tot. F)", "Totals, Female: Enrollment"]
    # export
    vis_3_Female = NCWIT_Data.groupby("When do students typically declare their major?")[vis_3_Female_attr].sum()
    # compute rate (%)
    vis_3_Female['Female Dropout Rate (%)'] = vis_3_Female.apply(lambda row: row[1]/(row[0]+row[1]+row[2])*100, axis = 1)

    # attributes
    vis_3_Male_attr = ["Totals, Male: Graduated (Tot. M)", "Totals, Male: Left Institution (not graduated) (Tot. M)", "Totals, Male: Enrollment"]
    # export
    vis_3_Male = NCWIT_Data.groupby("When do students typically declare their major?")[vis_3_Male_attr].sum()
    # compute rate (%)
    vis_3_Male['Male Dropout Rate (%)'] = vis_3_Male.apply(lambda row: row[1]/(row[0]+row[1]+row[2])*100, axis = 1)

    # combine Female & Male
    vis_3 = pd.concat([vis_3_Female, vis_3_Male], axis = 1)
    vis_3 = vis_3.dropna() # remove NaN
    vis_3.to_csv("data/vis_3_Dropout_rate_Declare_Major.csv")'''



if __name__ == "__main__":

    vis_2()