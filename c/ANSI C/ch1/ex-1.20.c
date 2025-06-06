#include <stdio.h>

// Usaremos tamano 8 para las pruebas
int largoTabs = 8;

/*
  Si la entrada es:
  hola\tadios
  hola\t\tadios

  La salida deberia ser:
  hola    adios
  hola            adios
  
  Note que no se estan insertando 8 espacios en lugar de cada tab
  Se esta alineando de 8 en 8 columnas
*/

int main(void) {

  // utiliza putchar(x) para escribir hacia standard output
  // donde x representa un caracter

  /* Escriba su codigo aqui abajo */
  int c, pos = 0;
  while((c = getchar()) != EOF) {
    if(c == '\t') {
      for(int i = 0; i < largoTabs - pos; i++) {
        putchar(' ');
      }
      pos = 0;
    } else if(c == '\n') {
      putchar(c);
      pos = 0;
    } else {
      putchar(c);
      ++pos;

      if(c == '\n') {
        pos = 0;
      }
    }
  }


  /* Escriba su codigo aqui arriba */

  return 0;
}


