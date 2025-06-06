#include <stdio.h>

int main(){

  // utiliza putchar(x) para escribir hacia standard output
  // donde x representa un caracter

  /* Escribe tu codigo aqui abajo */
  int c,prev;
  while ((c = getchar()) != EOF) {
    if (c == ' ' && prev == ' ') {
      continue;
    }
    putchar(c);
    prev = c;
  }
  
  /* Escribe tu codigo aqui arriba */

  return 0;
}
