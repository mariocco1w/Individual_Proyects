import random



#Bienvenida
print(" -------------------- BIENVENIDO AL JUEGO 2048 -------------------- ")
print("\n  Mario Monroy")



def juegue_pa():
    a = []
    for t in range(4):
        a.append([0] * 4)
    nuevo_bloque(a)
    return a



def nuevo_bloque(a):
    while True:
        s = random.randint(0, 3)
        m = random.randint(0, 3)
        if a[s][m] == 0:
            a[s][m] = 2
            break



def estado_juego(a):
    for t in range(4):
        for o in range(4):
            if a[t][o] == 2048:
                return "GANO"

    for t in range(4):
        for o in range(4):
            if a[t][o] == 0:
                return "AUN NO ACABA"
    for t in range(3):
        for o in range(3):
            if a[t][o] == a[t+1][o] or a[t][o] == a[t][o+1]:
                return "AUN NO ACABA"
    for o in range(3):
        if a[3][o] == a[3][o+1]:
            return "AUN NO ACABA"
    for t in range(3):
        if a[t][3] == a[t+1][3]:
            return "AUN NO ACABA"

    return "PERDIO"



def cambio_estado(a):
    estado = False
    nuevo_a = []

    for t in range(4):
        nuevo_a.append([0]*4)
        
    for t in range(4):
        pos = 0
        for o in range(4):
            if a[t][o] != 0:
                nuevo_a[t][pos] = a[t][o]
                if o != pos:
                    estado = True
                pos += 1
    return nuevo_a, estado



def juego_cambio(a):
    cambio = False
    puntos = 0
    for t in range(4):
        for o in range(3):
            if a[t][o] == a[t][o + 1] and a[t][o] != 0:
                a[t][o] = a[t][o] * 2
                a[t][o + 1] = 0
                cambio = True
                puntos += 1
    return a, cambio, puntos



def cambio_x(a):
    nuevo_a = []
    for t in range(4):
        nuevo_a.append([])
        for o in range(4):
            nuevo_a[t].append(a[t][3-o])
    return nuevo_a



def cambio_y(a):
    nuevo_a = []
    for t in range(4):
        nuevo_a.append([])
        for o in range(4):
            nuevo_a[t].append(a[o][t])
    return nuevo_a



#Moverse a la derecha
def move_left(movimiento):
    new_movimiento, cambio1 = cambio_estado(movimiento)
    new_movimiento, cambio2, puntos = juego_cambio(new_movimiento)
    alteracion = cambio1 or cambio2
    new_movimiento, _ = cambio_estado(new_movimiento)
    return new_movimiento, alteracion, puntos



#Moverse a la izquierda
def move_right(movimiento):
    new_movimiento = cambio_x(movimiento)
    new_movimiento, cambio, puntos = move_left(new_movimiento)
    new_movimiento = cambio_x(new_movimiento)
    return new_movimiento, cambio, puntos



#Moverse para arriba
def pa_arriba(movimiento):
    new_movimiento = cambio_y(movimiento)
    new_movimiento, cambio, puntos = move_left(new_movimiento)
    new_movimiento = cambio_y(new_movimiento)
    return new_movimiento, cambio, puntos



#Moverse para abajo
def pa_abajo(movimiento):
    new_movimiento = cambio_y(movimiento)
    new_movimiento, cambio, puntos = move_right(new_movimiento)
    new_movimiento = cambio_y(new_movimiento)
    return new_movimiento, cambio, puntos



#Hacer la interfaz de la tabla para el modo normal
def print_board(a):
    print("+------+------+------+------+")  
    for row in a:
        print("|", end="")
        for cell in row:
            if cell == 0:
                print("      |", end="")
            else:
                print(f"{cell:^6}|", end="")
        print("\n+------+------+------+------+")



#Hacer la interfaz de la tabla para el modo jugador vs jugador
def print_boards(a1, a2):
    for i in range(len(a1)):
        row1 = a1[i]
        row2 = a2[i]
        
        print("+------+------+------+------+" + "     " + "+------+------+------+------+")  
        print("|", end="")
        
        for cell in row1:
            if cell == 0:
                print("      |", end="")
            else:
                print(f"{cell:^6}|", end="")
        
        print("     |", end="")
        
        for cell in row2:
            if cell == 0:
                print("      |", end="")
            else:
                print(f"{cell:^6}|", end="")
        
        print("\n+------+------+------+------+" + "     " + "+------+------+------+------+")



#Modo de juego normal, es decir en solitario
def modo_normal():
    a = juegue_pa()
    puntaje = 0

    #Si es verdadero que haga los movimientos
    while True:
        x = input("\n\n Ingrese el comando:   ")
        if x == "W" or x == "w":
            a, flag, puntos = pa_arriba(a)
        elif x == "S" or x == "s":
            a, flag, puntos = pa_abajo(a)
        elif x == "A" or x == "a":
            a, flag, puntos = move_left(a)
        elif x == "D" or x == "d":
            a, flag, puntos = move_right(a)
        else:
            print("\n COMANDO INVALIDO...   ")
            continue

        puntaje += puntos
        estado = estado_juego(a)
        print_board(a)
        print("Estado:   ", estado, "   , Puntaje:   ", puntaje)
        if estado == "AUN NO ACABA":
            nuevo_bloque(a)
        else:
            break

    #Despedida
    print("\n\n ---------- GRACIAS POR USAR LA APLICACIÓN ---------- ")



#Modo de juego jugador vs jugador
def jugador_vs_jugador():
    a1 = juegue_pa()
    a2 = [row[:] for row in a1]  #Copiar el tablero inicial a2
    turno = random.choice([1, 2])
    puntaje1 = 0
    puntaje2 = 0
    print("\n\nEl jugador ", turno, " comienza primero.\n")

    while True:
        if turno == 1:
            print(" * Jugador 1 (Te mueves con A S D W)*:   ")
            x = input("Ingrese el comando:   ")
            if x == "W" or x == "w":
                a1, flag, puntos = pa_arriba(a1)
            elif x == "S" or x == "s":
                a1, flag, puntos = pa_abajo(a1)
            elif x == "A" or x == "a":
                a1, flag, puntos = move_left(a1)
            elif x == "D" or x == "d":
                a1, flag, puntos = move_right(a1)
            else:
                print("\n COMANDO INVALIDO...   ")
                continue

            puntaje1 += puntos
            estado = estado_juego(a1)
            print("Tablero Jugador 1:   ")
            print_boards(a1, a2)
            print("Estado:   ", estado, "   , Puntaje Jugador 1:   ", puntaje1, "   , Puntaje Jugador 2:   ", puntaje2)
            if estado == "AUN NO ACABA":
                nuevo_bloque(a1)
                turno = 2
            else:
                break
        else:
            print(" * Jugador 2 (Te mueves con las flechas del teclado) *:   ")
            x = input("Ingrese el comando:   ")
            if x == "\033[A":  #Arriba
                a2, flag, puntos = pa_arriba(a2)
            elif x == "\033[B":  #Abajo
                a2, flag, puntos = pa_abajo(a2)
            elif x == "\033[D":  #Izquierda
                a2, flag, puntos = move_left(a2)
            elif x == "\033[C":  #Derecha
                a2, flag, puntos = move_right(a2)
            else:
                print("\n COMANDO INVALIDO...   ")
                continue

            puntaje2 += puntos
            estado = estado_juego(a2)
            print("Tablero Jugador 2:   ")
            print_boards(a1, a2)
            print("Estado:  ", estado," , \nPuntaje Jugador 1:   ", puntaje1, "     ,  Puntaje Jugador 2:   ", puntaje2)
            if estado == "AUN NO ACABA":
                nuevo_bloque(a2)
                turno = 1
            else:
                break

    #Despedida
    print("\n\n ---------- GRACIAS POR USAR LA APLICACIÓN ---------- ")



#Modo contra el CPU
def jugador_vs_maquina():
    print("Modo Jugador vs Máquina")

    # Crear tablero para el jugador y la máquina
    jugador = juegue_pa()
    maquina = juegue_pa()

    # Inicializar puntajes
    puntaje_jugador = 0
    puntaje_maquina = 0

    while True:
        print("\nTablero del Jugador:   ")
        print_board(jugador)
        print("Puntaje del Jugador:", puntaje_jugador)
        print("\nTablero de la Máquina:   ")
        print_board(maquina)
        print("Puntaje de la Máquina:", puntaje_maquina)

        # Turno del jugador
        print("\n* Turno del Jugador (Te mueves con W A S D) *:   ")
        x = input("Ingrese el comando:   ").strip().lower()

        if x in "wasd":
            if x == "w":
                jugador, flag, puntos = pa_arriba(jugador)
            elif x == "s":
                jugador, flag, puntos = pa_abajo(jugador)
            elif x == "a":
                jugador, flag, puntos = move_left(jugador)
            elif x == "d":
                jugador, flag, puntos = move_right(jugador)

            estado = estado_juego(jugador)
            print("\nEstado del juego del Jugador:   ", estado)
            if estado != "AUN NO ACABA":
                break

            if flag:
                puntaje_jugador += puntos

            nuevo_bloque(jugador)

            # Turno de la máquina
            print("\nTurno de la Máquina:   ")
            maquina = muevelo_ia(maquina)
            estado = estado_juego(maquina)
            print("\nEstado del juego de la Máquina:   ", estado)
            if estado != "AUN NO ACABA":
                break

            if flag:
                puntaje_maquina += puntos

            nuevo_bloque(maquina)
        else:
            print("COMANDO INVALIDO...")

    print("El juego ha terminado.")
    print("Puntaje final del Jugador:", puntaje_jugador)
    print("Puntaje final de la Máquina:", puntaje_maquina)



#Para que la máquina tome decisiones propias y aleatorias
def muevelo_ia(a):
    movimientos = [move_left, move_right, pa_arriba, pa_abajo]
    while True:
        aleatorio = random.choice(movimientos)
        new_a, cambio, puntos = aleatorio(a)
        if cambio:
            return new_a



#Mostrar ayuda
def mostrar_ayuda():
    print("\n\n*------------------- AYUDA -------------------*")
    print("\nObjetivo del juego:")
    print("Alcanzar el bloque con el valor 2048.")
    print("\nControles:")
    print("Usa las teclas W (arriba), A (izquierda), S (abajo) y D (derecha) para mover los bloques.")
    print("\nReglas:")
    print("1. Los bloques se mueven en la dirección indicada.")
    print("2. Cuando dos bloques del mismo valor chocan, se combinan en uno solo con el valor igual a la suma de los dos.")
    print("3. El juego termina cuando logras obtener el bloque con el valor 2048 (Ganas) o cuando no hay movimientos posibles (Pierdes).")
    print("\nModo Jugador vs Jugador:")
    print("1. Ambos jugadores comienzan con la misma configuración inicial del tablero.")
    print("2. Los jugadores se turnan para mover los bloques.")
    print("3. El primero en alcanzar el bloque 2048 gana.")
    print("4. Si no hay movimientos posibles para ambos jugadores, el juego termina en empate.")
    print("\n\n-----------------------------------------------")



#Interfaz del Menú
def main_menu():
    while True:
        print("\n\nSeleccione un modo de juego:")
        print("1. Modo Normal")
        print("2. Jugador vs Jugador")
        print("3. Jugador vs Máquina")
        print("4. Ayuda")
        print("5. Salir")
        
        opcion = input("Ingrese una opción:   ")
        
        if opcion == "1":
            modo_normal()
        elif opcion == "2":
            jugador_vs_jugador()
        elif opcion == "3":
            jugador_vs_maquina()
        elif opcion == "4":
            mostrar_ayuda()
        elif opcion == "5":
            print("\n\n ---------- GRACIAS POR USAR LA APLICACIÓN ---------- ")
            break
        else:
            print("Opción inválida, por favor intente nuevamente.")



#Mostrar menú principal
main_menu()