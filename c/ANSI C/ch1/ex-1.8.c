#include <stdio.h>

// nb -> espacios
// nt -> tabs
// nl -> newlines
int main(){ 
  long nb = 0;
  long nt = 0;
  long nl = 0;

  /* Escribe tu codigo aqui abajo */
  int c;
  while ((c = getchar()) != EOF) {
    if (c == ' ') {
      nb++;
    } else if (c == '\t') {
      nt++;
    } else if (c == '\n') {
      nl++;
    }
  }
  /* Escribe tu codigo aqui arriba */

  // No modifiques el print
  printf("blanks: %ld tabs: %ld newlines: %ld", nb, nt, nl);
  return 0;
}
