from fastapi import APIRouter
from pydantic import BaseModel
import pandas as pd
from sklearn.linear_model import LogisticRegression
from sklearn.preprocessing import StandardScaler
import numpy as np

router =APIRouter()

#================ SIMULACIION DE DATOS PARA ENTRENAMIENTO =================
data = pd.DataFrame({
    'ingresos':[5000,2000,7000,1200,3000,9000],
    'deuda':[1500,500,2000,300,800,2500],
    'score':[1,0,1,0,0,1]#1 = aprobado, 0= rechazado
})
#variables independientes (features) y dependiente (label)
x = data[['ingresos','deuda']]
y = data['score']
#================ Procesamiento o Escalacion de variables ==============================
scaler = StandardScaler()
X_scaled = scaler.fit_transform(x)
#================ Entrenamiento de Modelo ===========================
model = LogisticRegression()
model.fit(X_scaled,y)
#=========== entradas Esperadas ========================
class LoanApplication(BaseModel):
    ingresos: float
    deuda: float
#=========== Enndpoimt para evaluacion de la  IA
@router.post("/evaluate_loan")
def evaluate_with_ai(application: LoanApplication):
    user_data = np.array([[application.ingresos, application.deuda]])
    user_data_scaled = scaler.transform(user_data)
    #PREDICTION
    prediction = model.predict(user_data_scaled)[0]
    probability = model.predict_proba(user_data_scaled)[0][1]
#MENSJAE DE RESPUESTA
    return{
        "aprobado": bool(prediction),
        "probabilidad": float(probability)
    }


