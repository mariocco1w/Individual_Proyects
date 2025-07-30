from cryptography.fernet import Fernet

with open('clave.key.nueva', 'wb') as f:
    f.write(Fernet.generate_key())

print('Nueva clave generada y guardada en clave.key.nueva') 