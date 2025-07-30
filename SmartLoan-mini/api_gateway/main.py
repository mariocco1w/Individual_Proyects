from fastapi import FastAPI               
from router import auth, loan, loan_ai   

app = FastAPI()
#Routers
app.include_router(auth.router)
app.include_router(loan.router)
app.include_router(loan_ai.router)

@app.get("/")
def root():
    return{"message":"API Gateway is running"} 
