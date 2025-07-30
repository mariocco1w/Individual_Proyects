from  flask import Flask, render_template, request, redirect, url_for, session
import json
import  os
from cryptography.fernet import Fernet

app = Flask(__name__)
app.secret_key = "clave_secreta_flask"

# Solo genera la clave si no existe
if not os.path.exists("clave.key"):
    with open("clave.key", "wb") as key_file:
        key_file.write(Fernet.generate_key())

# Lee la clave una sola vez
with open('clave.key','rb') as f:
    key = f.read()
cipher = Fernet(key)

archivo_datos  = "cuentas_web.json"

#Creacion de archivo  en  caso  que no exista
if not os.path.exists(archivo_datos):
    with open(archivo_datos, "w") as f:
        json.dump({}, f)
        
# ==========  RUTAS WEB ==========
@app.route("/", methods=["GET", "POST"])
def login():
    if request.method == "POST":
        # Cambia esto por una contraseña de admin fija
        password = request.form["password"]
        admin_password = "yMhvjTTwPlhzFF634pT520jwqVHKmDI3s2BMLYQZiNQ="  # This  is  the  only  key to log in
        if password == admin_password:
            session["logged_in"]  = True
            return redirect(url_for("panel"))
        else:
            return render_template("login.html", error="Contraseña incorrecta")
    return render_template("login.html")
#===================PANEL RUTA================================
@app.route("/panel")
def panel():
    if not session.get("logged_in"):
        return redirect(url_for("login"))
    
    cuentas = {}
    if os.path.exists(archivo_datos):
        with open(archivo_datos, "r") as f:
            try:
                datos = json.load(f)
            except json.JSONDecodeError:
                datos = {} # manejo de archivo vacio
    else:
        datos = {}
        
    descifradas = {}
    for servicio, info in  datos.items():
        try:
            desc = cipher.decrypt(info["contraseña"].encode()).decode()
            descifradas[servicio] = {
                "usuario": info["usuario"],
                "contraseña": desc
            }
        except Exception as e:
            # En caso que el archivo este corrupto
            print(f"Could not decrypt data for {servicio}: {e}")
            descifradas[servicio] = {
                "usuario": info["usuario"],
                "contraseña": "¡ERROR AL DESCIFRAR!"
            }
            
    return render_template("panel.html", cuentas=descifradas)
#================Metodo Guardado==============================   
@app.route("/guardar", methods=["POST"])
def guardar():
    if not session.get("logged_in"):
        return redirect(url_for("login"))
    servicio = request.form["servicio"]
    usuario = request.form["usuario"]
    contraseña = request.form["contraseña"]
    
    datos = {}
    if os.path.exists(archivo_datos):
        with open(archivo_datos, "r") as f:
            try:
                datos = json.load(f)
            except json.JSONDecodeError:
                datos = {} #manejo en caso que el archivo este vacio 
                
    cifrada = cipher.encrypt(contraseña.encode()).decode('utf-8')
    datos[servicio] = {"usuario": usuario, "contraseña": cifrada}
    
    with open(archivo_datos, "w") as f:
        json.dump(datos, f, indent=4)
    return redirect(url_for("panel"))
@app.route("/logout")
def logout():
    session.clear()

    return redirect(url_for("login"))
#==========Metodo  Eliminar============
@app.route("/eliminar", methods=["POST"])
def eliminar():
    if not session.get("logged_in"):
        return redirect(url_for("login"))
    servicio = request.form["servicio"]
    with open(archivo_datos, "r") as f:
        datos = json.load(f)
    if servicio in datos:
        del datos[servicio]
        with open(archivo_datos, "w") as f:
            json.dump(datos, f, indent=2)
    return redirect(url_for("panel"))
#==========Metodo Editar===================
@app.route("/editar", methods=["GET", "POST"])
def editar():
    if not session.get("logged_in"):
        return redirect(url_for("login"))
    with open(archivo_datos, "r") as f:
        datos = json.load(f)
    if request.method == "GET":
        servicio = request.args.get("servicio")
        if servicio not in datos:
            return redirect(url_for("panel"))
        desc = cipher.decrypt(datos[servicio]['contraseña'].encode()).decode()
        return render_template("editar.html", servicio=servicio, usuario=datos[servicio]['usuario'],
                               contraseña=desc)
    elif request.method == "POST":
        servicio_original = request.form["servicio_original"]
        servicio_nuevo = request.form["servicio"]
        usuario = request.form["usuario"]
        contraseña = request.form["contraseña"]
        cifrada = cipher.encrypt(contraseña.encode()).decode()
        # Actualización de datos
        if servicio_original in datos:
            del datos[servicio_original]
        datos[servicio_nuevo] = {"usuario": usuario, "contraseña": cifrada}
        with open(archivo_datos, "w") as f:
            json.dump(datos, f, indent=2)
        return redirect(url_for("panel"))

if __name__ == "__main__":
    print("🌐 Servidor iniciado en: http://127.0.0.1:5000")
    print("📱 Para acceder desde otros dispositivos: http://[tu-ip]:5000")
    print("⏹️  Presiona Ctrl+C para detener el servidor")
    app.run(debug=True, host="0.0.0.0", port=5000)
        