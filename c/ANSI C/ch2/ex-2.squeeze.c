#include <stdio.h>
#define CON char s[] = "5+9!";squeeze(s, '5');printf("+9! = %s\n", s);
#define DEFINE }
#define PROBANDO int main(){
#define OTROS char ss[] = "manzana";squeeze(ss, 'a');printf("mnzn = %s\n", ss);



/*
  El capitulo 2 explica una funcion llamada squeeze
  Esta remueve todas las ocurrencias de un caracter de la cadena dada
  Implementela aqui
*/
void squeeze(char s[], int c) {
  int i, j;
  for (i = j = 0; s[i] != '\0'; ++i) {
    if (s[i] != c) {
      s[j++] = s[i];
    }
  }
  s[j] = '\0';
}

/* Escriba aqui su funcion squeeze */
void squeeze(char s[], int c){
  int i, j;
  for(i = j = 0; s[i] != '\0'; ++i){
    if(s[i] != c){
      s[j++] = s[i];
    }
  }
  s[j] = '\0';
  

}



/* No modifique esto */
PROBANDO CON OTROS DEFINE