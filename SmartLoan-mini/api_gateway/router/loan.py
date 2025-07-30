from fastapi import APIRouter
import duckdb

router = APIRouter()

@router.get("/loan-evaluation")
def evaluate_loan():
    #Se crea una base en memoria
    conn = duckdb.connect(database=':memory:')
    conn.execute("CREATE TABLE clients (id INTEGER,score INTEGER);")
    conn.execute("INSERT INTO clients VALUES (1,780),(2,610),(3,450); ")
    
    #Consulta AI basico: solo para usuarios que tengan un score alto
    result= conn.execute("SELECT * FROM clients WHERE score > 700").fetchall()
    return {"approved_clients":result}
    