#include <stdio.h>



// Escriba una funcion para convertir de Fahrenheit a Celsius
// La funcion debe llamarse fahrcel()
// Esta recibe un double como argumento
// Debe retornar un double

/* Escriba su codigo aqui abajo */
double farche1(double fahr) {
  return (5.0/9.0)*(fahr-32.0);
}
/* Escriba su codigo aqui arriba */



int main() {
  double fahr, celcius;
  double lower, upper, step;

  lower = 0;
  step = 20;
  upper = 300;

  fahr = lower;

  // No modifique estos prints
  printf ("fahr\tcelsius\n");
  while(fahr<upper){
    printf("%3.0f\t%6.1f\n", fahr, fahrcel(fahr));
    fahr = fahr + step;
  }

  return 0;
}