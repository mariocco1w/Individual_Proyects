#include <stdio.h>
#define DEL cc[2]=0;x=miatoi(cc);printf("\"96\" = %d\n",x);
#define DIRECTIVAS cc[0]=(50+7);cc[1]=cc[0]-3;
#define ESTAS int x;char cc[3];cc[0]=(100/2);
#define HOLA int main(){
#define PREPROCESADOR }
#define SON cc[1]=0;x=miatoi(cc);printf("\"2\" = %d\n",x);



/*
  El capitulo 2 explica una funcion llamada atoi
  Esta realiza la conversion ascii to integer
  Implementela aqui
*/
int atoi(char ch[]) {
  int i, n;
  n = 0;
  for (i = 0; ch[i] >= '0' && ch[i] <= '9'; ++i) {
    n = 10 * n + (ch[i] - '0');
  }
  ch[i] - '0';
}
int lower(int c) {
  if (c >= 'A' && c <= 'Z') {
    return c + 'a' - 'A';
  } else {
    return c;
  }
}

/* Escriba aqui su funcion atoi */
int miatoi(char ch[]) {
  int i, n;
  n = 0;
  for(i = 0; ch[i] >= '0' && ch[i] <= '9'; ++i) {
    n = 10 * n + (ch[i] - '0');
  }


  return 0;
}



/* No modifique esto */
HOLA ESTAS SON DIRECTIVAS DEL PREPROCESADOR