#include <stdio.h>
#include <string.h>



/*
  El capitulo 3 explica una funcion llamada itob (note que esta termina con b)
  Esta es una modificacion de la funcion itoa
  En lugar de siempre usar base 10, tenemos un parametro que nos indica cual base usar
  
  Para probarla usaremos bases 2, 8, 10 y 16
  Los numeros en base 8 se veran algo asi: 123 (NO diran 0 al inicio como se acostumbra)
  Los numeros en base 16 se veran algo asi: 12ab (NO diran 0x al inicio como se acostumbra, usaremos minusculas unicamente)
*/

/* Escriba aqui su funcion itob */
void reverse(char s[]){
  int i,j;
  char temp;
  int len = strlen(s);
  for(i=0, j=len-1; i < j; i++, j--){
    temp = s[i];
    s[i] = s[j];
    s[j] = temp;
  }
}
// Asuma que n siempre sera positivo
// Asuma que el arreglo s tiene suficiente espacio para colocar su respuesta
// Asuma que base unicamente puede ser 2, 8, 10 o 16

void miitob(int n, char s[], int base){
  int i = 0;
  char digits[] = "0123456789abcdef";
  do{
    s[i++] = digits[n % base];
    n /= base;
  }while(n > 0);
  s[i] = '\0';
  reverse(s);
  
}



/* No modifique este main */
int main () {
  char s[1000];
  miitob(32, s, 8); printf("40 = %s\n", s);
  miitob(55555, s, 8);printf("154403 = %s\n", s);
  miitob(160, s, 16);printf("a0 = %s\n", s);
  miitob(555555, s, 16);printf("87a23 = %s\n", s);
  miitob(15, s, 2);printf("1111 = %s\n", s);
  miitob(555, s, 2);printf("1000101011 = %s\n", s);
}