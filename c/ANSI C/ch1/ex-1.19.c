#include <stdio.h>
#define MAXLINE 1000


/* 
  Si el archivo de entrada dice:
  hola buenos dias
  adios amigo

  El resultado deberia ser:
  said soneub aloh
  ogima soida
*/


int main(){

  // utiliza putchar(x) para escribir hacia standard output
  // donde x representa un caracter

  /* Escriba su codigo abajo */
  char line[MAXLINE];

  while(getline(line, MAXLINE ) > 0) {
    for(int i = 0; line[i] != '\0'; i++) {
      putchar(line[i]);
    }
  }

  int getline(char s[], int lim) { 
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
  void reverse(char s[]) {
    int i, j;
    char temp;
    for(i = 0; s[i] != '\0'; i++) {
      ;
    }
    --i;
    if(s[i] == '\n') {
      --i;
    }
    j = 0;
    while(j < i) {
      temp = s[j];
      s[j] = s[i];
      s[i] = temp;
      --i;
      ++j;
    }
  }



  /* Escriba su codigo arriba*/

  return 0;
}