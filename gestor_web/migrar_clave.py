import json
import os
from cryptography.fernet import Fernet

CUENTAS_PATH = 'cuentas_web.json'
OLD_KEY_PATH = 'clave.key'
NEW_KEY_PATH = 'clave.key.nueva'

# 1. Leer claves
with open(OLD_KEY_PATH, 'rb') as f:
    old_key = f.read()
with open(NEW_KEY_PATH, 'rb') as f:
    new_key = f.read()

old_cipher = Fernet(old_key)
new_cipher = Fernet(new_key)

# 2. Leer datos cifrados
with open(CUENTAS_PATH, 'r', encoding='utf-8') as f:
    cuentas = json.load(f)

# 3. Descifrar y recifrar contraseñas
cuentas_migradas = {}
errores = []
for email, datos in cuentas.items():
    try:
        password_descifrada = old_cipher.decrypt(datos['contraseña'].encode()).decode()
        nueva_password = new_cipher.encrypt(password_descifrada.encode()).decode()
        datos_migrados = datos.copy()
        datos_migrados['contraseña'] = nueva_password
        cuentas_migradas[email] = datos_migrados
    except Exception as e:
        errores.append((email, str(e)))
        print(f"[ERROR] No se pudo migrar la cuenta {email}: {e}")

if errores:
    print(f"\nSe encontraron {len(errores)} errores. Revisa los mensajes anteriores.")
    print("No se sobrescribirá el archivo original.")
    exit(1)

# 4. Confirmar antes de sobrescribir
print("\n¡ATENCIÓN! Se van a migrar todas las contraseñas a la nueva clave.")
print(f"Se va a sobrescribir {CUENTAS_PATH} y {OLD_KEY_PATH}.")
confirm = input("¿Deseas continuar? (escribe 'SI' para continuar): ")
if confirm.strip().upper() != 'SI':
    print("Operación cancelada.")
    exit(0)

# 5. Hacer respaldo
os.rename(CUENTAS_PATH, CUENTAS_PATH + '.bak')
os.rename(OLD_KEY_PATH, OLD_KEY_PATH + '.bak')

# 6. Guardar datos migrados y nueva clave
with open(CUENTAS_PATH, 'w', encoding='utf-8') as f:
    json.dump(cuentas_migradas, f, indent=4, ensure_ascii=False)
os.rename(NEW_KEY_PATH, OLD_KEY_PATH)

print("\n¡Migración completada con éxito!")
print(f"Respaldo de cuentas: {CUENTAS_PATH}.bak")
print(f"Respaldo de clave: {OLD_KEY_PATH}.bak")
