#include <stdio.h>

int main(){

  // utiliza putchar(x) para escribir hacia standard output
  // donde x representa un caracter

  /* Escribe tu codigo aqui abajo */
  int c;
  while ((c = getchar()) != EOF) {
    if (c == '\t') {
      putchar('\\');
      putchar('t');
    } else if (c == '\b') {
      putchar('\\');
      putchar('b');
    } else if (c == '\\') {
      putchar('\\');
      putchar('\\');
    } else {
      putchar(c);
    }
  }


  /* Escribe tu codigo aqui arriba */

  return 0;
}
