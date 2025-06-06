#include <stdio.h>
#define MAXLINE 1000

// Se sugiere usar el getline() que aparece en el libro para hacer mas facil su trabajo
// Puede copiarlo tal como esta alli
int getline(char line[], int maxline);
void copy(char to[], char from[]);


int main(void) {

  // utilice un printf como en siguiente para mostrar su resultado
  // printf("%s", linea)

  /* Escriba su codigo aqui abajo */
  char line[MAXLINE];

  while(getline(line, MAXLINE ) > 0) {
    if (strlen(line) > 80) {
      printf("%s", line);
    }
  }


  /* Escriba su codigo aqui arriba */

  return 0;
}



// Se sugiere usar el getline() que aparece en el libro para hacer mas facil su trabajo
// Puede copiarlo tal como esta alli
int getline (char s[], int lim) {
  int c, i;
  for(i = 0; i < lim - 1 && (c = getchar()) != EOF && c != '\n'; ++i) {
    s[i] = c;
  }
  if(c == '\n') {
    s[i] = c;
    ++i;
  }
  s[i] = '\0';
  return i;
}
void copy(char to[], char from[]) {
  int i;
  i = 0;

  while((to[i] = from[i]) != '\0') {
    ++i;
  }

  /* Utiliza el getline del libro */

  return 0;
} 