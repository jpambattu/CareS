from flask import Flask, request
from flask_mysqldb import MySQL
import numpy as np
import pickle

app = Flask(__name__)
model = pickle.load(open('model.pkl', 'rb'))

app.config['MYSQL_HOST'] = 'localhost'
app.config['MYSQL_USER'] = 'root'
app.config['MYSQL_PASSWORD'] = ''
app.config['MYSQL_DB'] = 'cares'

mysql = MySQL(app)

@app.route('/predict',methods=['GET', 'POST'])
def predict():
    id = int(request.args.get('id'))
    cursor = mysql.connection.cursor()
    cursor.execute("""SELECT parity, mother_age, mother_height, mid_arm_cir, workload, anemia, asthma, bad_obs_history, injection, falif, iron, 2nd_workload, 1st_convolusion, bleed1, asthma2, inject2, iron2, 2nd_convolusion, bleed, fever, BMI FROM USERS WHERE id = %s""", (id,))

    int_features = cursor.fetchone()
    final_features = [np.array(int_features)]
    prediction = model.predict(final_features)
    str_pred = str(prediction[0])
    cursor.execute(''' UPDATE users SET TARGET=%s WHERE id = %s''', (str_pred,id,))

    # Saving the Actions performed on the DB
    mysql.connection.commit()

    # Closing the cursor
    cursor.close()

    return str_pred



# Creating a connection cursor
# cursor = mysql.connection.cursor()
#
# # Executing SQL Statements
# cursor.execute(''' CREATE TABLE table_name(field1, field2...) ''')
# cursor.execute(''' INSERT INTO table_name VALUES(v1,v2...) ''')
# cursor.execute(''' DELETE FROM table_name WHERE condition ''')

if __name__ == "__main__":
    #app.run(debug=True)
    app.run(host="0.0.0.0")