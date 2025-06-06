#include <stdio.h>

// 10 digitos + 26 mayusculas + 26 minusculas
#define ALPHANUMERIC 62	

// c, i, j -> variables temporales
// nAlphanumeric[ALPHANUMERIC] -> arreglo para guardar las frecuencias de cada caracter, en este orden:
//    digitos [0-9] en posiciones 0 a 9
//    letras mayusculas [A-Z] en posiciones 10 a 35
//    letras minusculas [a-z] en posiciones 36 a 61
int main(){
  // No modifique estas variables e inicilizacion
  int c, i, j;
  int nAlphanumeric[ALPHANUMERIC];
  int charr;
  for (int i = 0; i < ALPHANUMERIC; i++) {
    nAlphanumeric[i] = 0;
  }



  /* Escriba su codigo aqui abajo */
  while ((c = getchar()) != EOF) {
    if (c >= '0' && c <= '9') {
      nAlphanumeric[c-'0']++;
    } else if (c >= 'A' && c <= 'Z') {
      nAlphanumeric[c-'A'+10]++;
    } else if (c >= 'a' && c <= 'z') {
      nAlphanumeric[c-'a'+36]++;
    }
  }

  /* Escriba su codigo aqui arriba */



  // No modifique estos print
  for(i=0; i<ALPHANUMERIC;++i) {
    if (i<10) printf("%c|", (char)i+'0');
    else if (i<36) printf("%c|", (char)i-10+'A');
    else printf("%c|", (char)i-36+'a');
    for (j=0; j<nAlphanumeric[i]; printf("*"), ++j);
    printf("\n");
  }

  return 0;
}
