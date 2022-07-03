import warnings
warnings.filterwarnings('ignore')

import pandas as pd
import numpy as np
import pickle

from sklearn.model_selection import train_test_split

from sklearn.ensemble import RandomForestClassifier

# read dataset
lbw_data = pd.read_csv(r'C:\Users\Jpamb\Desktop\Low-Birth-Weight-Prediction-main\Total_data.csv')

# Used drop() function for removing unnecessary features
lbw_data = lbw_data.drop(['Unnamed: 0','id','lda','relg','mwt','mwt2','mwt3'], axis = 'columns', inplace = False)

# Used rename() function for renaming feature names
lbw_data.rename(columns = {
    'childwt': 'child_weight', 'logit': 'target', 'mage':'mother_age', 'mht': 'mother_height', 'mdarm': 'mid_arm_cir', 'head':'head_cir',
       'occup':'occupation', 'educ':'education', 'f11':'proteinTime_12th_week',
       'wm18':'adult_wom_18', 'ageyc':'age_at_pregnancy', 'boh':'bad_obs_history', 'ecostat':'econoic_status',
       'belly1':'abdominal_girth_20wk', 'fand1':'Fundal_height_20wk', 'sis1':'1st_systolic', 'dis1':'1st_diastolic', 'inject':'injection', 'worklo':'2nd_workload',
       'conv1':'1st_convolusion', 'belly2':'abdominal_girth_28wk',
       'fand2':'Fundal_height_28wk', 'sis2':'2nd_systolic', 'dia2':'2nd_diastolic', 'workl':'3rd_workload', 'f13':'proteinTime_28th_week',
       'conv2':'2nd_convolusion'
}, inplace = True)


# Used simpleImputer for imputing null values and missing data which return most frequent data and replace them.
from sklearn.impute import SimpleImputer

impt = SimpleImputer(missing_values = np.nan, strategy = 'most_frequent')
lbw_data = impt.fit_transform(lbw_data)

# create dataframe from array
lbw_data = pd.DataFrame(lbw_data, columns=[
                                           'parity', 'child_weight', 'target', 'mother_age', 'mother_height',
       'mid_arm_cir', 'head_cir', 'habit', 'occupation', 'education',
       'workload', 'proteinTime_12th_week', 'f21', 'f31', 'f41', 'anemia',
       'asthma', 'adult_wom_18', 'age_at_pregnancy', 'bad_obs_history',
       'econoic_status', 'abdominal_girth_20wk', 'Fundal_height_20wk',
       '1st_systolic', '1st_diastolic', 'injection', 'falif', 'iron',
       '2nd_workload', 'f12', 'f22', 'f32', 'f42', 'rest', '1st_convolusion',
       'd21', 'bleed1', 'blddef1', 'asthma2', 'fever1', 'lomot1', 'contab1',
       'db1', 'abdominal_girth_28wk', 'Fundal_height_28wk', '2nd_systolic',
       '2nd_diastolic', 'inject2', 'falif2', 'iron2', '3rd_workload',
       'proteinTime_28th_week', 'f23', 'f33', 'f43', 'rest1',
       '2nd_convolusion', 'd', 'bleed', 'blddef', 'fever', 'lomot',
       'contab', 'db', 'wg1', 'wg2', 'BMI'
])


from sklearn.preprocessing import StandardScaler

standardScaler = StandardScaler()
columns_to_scale = ['mother_age',
                    
                    'mid_arm_cir',
                    'head_cir',
                    
                    'abdominal_girth_20wk',
                    'Fundal_height_20wk',
                    '1st_systolic',
                    '1st_diastolic',
                    
                    'abdominal_girth_28wk',
                    'Fundal_height_28wk',
                    '2nd_systolic',
                    '2nd_diastolic',
                    'BMI']
lbw_data[columns_to_scale] = standardScaler.fit_transform(lbw_data[columns_to_scale])

# Flittering out the unnecessary columns & store the new dataset which is lbw_data_sub
lbw_data_sub = lbw_data[[  'parity', 'target', 'mother_age', 'mother_height', 'mid_arm_cir','workload',
                               'anemia', 'asthma', 'bad_obs_history', 'injection', 'falif','iron', '2nd_workload', '1st_convolusion',
                               'bleed1','asthma2','inject2', 'iron2', '2nd_convolusion', 'bleed','fever','BMI']]

# segregating dataset into features i.e., X and target variables i.e., y
X = lbw_data_sub.drop(['target'],axis=1)
y = lbw_data_sub['target']

X_train, X_test, y_train, y_test = train_test_split(X, y, stratify=y, test_size=0.25,shuffle=True, random_state=5)

# from sklearn.model_selection import train_test_split # split train and test data
y = lbw_data_sub['target']
X = lbw_data_sub.drop('target', axis=1)
X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.25, random_state=1)

rf_ent = RandomForestClassifier(criterion='entropy',n_estimators=100)
rf_ent.fit(X_train, y_train)
y_pred_rfe = rf_ent.predict(X_test)
print(X.columns)
print(type(X_test))

data = [3,30,1.45,0.4,1.0,1.0,1.0,1.0,0.0,1.0,0.0,0.0,0.0,1.0,1.0,0.0,0.0,1.0,1.0,1.0,22.3]


nparray = [np.array(data)]

pickle.dump(rf_ent, open('model.pkl','wb'))

# Loading model to compare the results
model = pickle.load(open('model.pkl','rb'))
print(model.predict(nparray))
