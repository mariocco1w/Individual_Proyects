#include <stdio.h>
#include <string.h>



/*
  El capitulo 3 explica una funcion llamada itoa
  Esta es el opuesto a la funcion atoi del capitula anterior
  Es decir, esta realiza la conversion integer to ascii
*/
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

/* Escriba aqui su funcion itoa */
// Asuma que el arreglo s tiene suficiente espacio para colocar su respuesta
void miitoa(int n, char s[]){
  int i = 0, sign;
  if((sign = n) < 0){
    n = -n;
  }
  do{
    s[i++] = n % 10 + '0';
  }while((n /= 10) > 0);
  if(sign < 0){
    s[i++] = '-';
  }
  s[i] = '\0';
  reverse(s);

}



/* No modifique este main */
int main () {
  char s[1000];
  s[6] = 8;
	miitoa(345090, s);
  printf("345090 = %s\n", s);
  s[6] = 8;
  miitoa(-10002, s);
	printf("-10002 = %s\n", s);
}