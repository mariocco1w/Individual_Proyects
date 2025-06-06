#include <stdio.h>

#define ESTO int main () {
#define NOS char a[20],b[20],c[20];
#define SIRVE a[0]=104;a[3]=97;c[3]=100;c[1]=117;
#define PARA b[0]=32;c[0]=109;c[2]=c[0]+1;c[4]=c[0]+2;
#define PROBAR a[1]=c[4];a[2]=c[0]-1;
#define SUS a[4]=b[1]=c[5]=0;
#define PROGRAMAS mistrcat(a,b);mistrcat(a,c);
#define CON printf("hola mundo = %s\n",a);
#define FACILIDAD }



/*
  El capitulo 2 explica una funcion llamada strcat
  Esta concatena el segundo string dado al primero
  Como realmente estamos usando arreglos, al usarla se asume que el primer arreglo tiene tamano suficiente
  Implementela aqui
*/
void strcat(char s[], char t[]) {
  int i, j;
  i = j = 0;
  while (s[i] != '\0') {
    ++i;
  }
  while ((s[i++] = t[j++]) != '\0') {
    ;
  }
}

/* Escriba aqui su funcion strcat */
void mistrcat(char s[], char t[]) {
  int i, j;
  i = j = 0;
  while (s[i] != '\0') {
    ++i;
  }
  while ((t[j] != '\0')) {
    s[i++] = t[j++];
  }


}



/* No modifique esto */
ESTO NOS SIRVE PARA PROBAR SUS PROGRAMAS CON FACILIDAD