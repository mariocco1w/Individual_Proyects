#include <stdio.h>

int main(){

  // utiliza putchar(x) para escribir hacia standard output
  // donde x representa un caracter

  /* Escribe tu codigo aqui abajo */
  int c, in_word;
  in_word = 0;
  while ((c = getchar()) != EOF) {
    if (c == ' ' || c == '\t' || c == '\n') {
      if (in_word) {
        putchar('\n');
        in_word = 0;
      }
    } else {
      putchar(c);
      in_word = 1;
    }
  }


  /* Escribe tu codigo aqui arriba */

  return 0;
}
