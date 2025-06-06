#include <stdio.h>



/*
  El capitulo 4 explica una funcion llamada swap
  Esta recibe un arreglo y dos indices, e intercambia el contenido de esas casillas
  Implemente su funcion aqui
*/
void swap(int v[], int i, int j) {
  int temp = v[i];
  v[i] = v[j];
  v[j] = temp;
	
}



/* No modifique el main */
int main() {
  int a[3]; a[0]=22; a[1]=11; a[2]=33;
  swap(a, 0, 1); printf("11 22 33 = %d %d %d\n", a[0], a[1],a[2]);
  swap(a, 2, 0); printf("33 22 11 = %d %d %d\n", a[0], a[1],a[2]);
}