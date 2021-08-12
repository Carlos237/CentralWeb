tope = 0
movimientos = [(0,1),(1,0),(0,-1),(-1,0)] #(0,1) es moverse a la der, (1,0) es moverse abajo, (0,-1) es moverte a izq y (-1,0) es moverte arriba
distanciaMin = float("inf")
def EsFactible(tablero,fila,columna):
    if fila < 0 or fila > len(tablero)-1 or columna > len(tablero[0])-1 or columna < 0: #No me puedo ir al -1,0, al
        return False
    if tablero[fila][columna] == -1:
        return False
    if tablero[fila][columna] < -1:
        return False
    return True


def mercadona(tablero, fila, columna, pasos, producto):
    global tope
    if fila == len(tablero)-1 and columna == len(tablero[fila])-1 and tope == producto:
        global distanciaMin
        if pasos < distanciaMin:
            distanciaMin = pasos

    else:
        global movimientos
        for j in movimientos:
            #while not exito or j >= len(movimientos): Esto es para cuando el enunciado me pida la solución y me salga
            filamov = fila + j[0] #En el primer caso, j[0] sería 0
            columnamov = columna + j[1] #En el primer caso, j[1] sería 1
            if EsFactible(tablero,filamov,columnamov):
                if tablero[fila][columna] == 1:
                    producto = producto+1
                pos = tablero[fila][columna]
                tablero[fila][columna] = float('-inf')
                #exito = humoramarillo(tablero, filamov, columnamov, pasos + 1)
                mercadona(tablero, filamov, columnamov, pasos + 1, producto)
                if pos == 1:
                    producto = producto - 1
                tablero[fila][columna] = pos

tablero = dict()
filas,columnas,tope = map(int,input().strip().split())
for n in range(filas):
    tablero[n] = []
    lista = input().strip().split()
    for i in lista:
        tablero[n].append(int(i))
mercadona(tablero, 0, 0, 1, 0)
print(distanciaMin)
