#include <stdio.h>



/*
  El capitulo 3 explica una funcion llamada expand
  Esta nos sirve para expandir rangos de caracteres, por ejemplo
  a-d		  se convierte en   abcd
  4-8     se convierte en   45678
  x-z1-3  se convierte en   xyz123
  Implementela aqui
*/

/* Escriba aqui su funcion expand */
// s[] -> arreglo lo suficientemente grande para colocar su string expandido
// t[] -> arreglo de caracteres con la version compacta
void expand(char s[], char t[]) {
    int i = 0, j = 0;
    char start, end;
    
    // Skip leading hyphen
    if (t[0] == '-' && t[1] != '\0') {
        s[j++] = '-';
        i++;
    }
    
    while (t[i] != '\0') {
        if (t[i+1] == '-' && t[i+2] != '\0') {
            start = t[i];
            end = t[i+2];
            // Only expand if it's a valid range (ascending order and same type)
            if ((isalpha(start) && isalpha(end) && start <= end) ||
                (isdigit(start) && isdigit(end) && start <= end)) {
                for (char c = start; c <= end; c++) {
                    s[j++] = c;
                }
            } else {
                // Invalid range, copy characters literally
                s[j++] = start;
                s[j++] = '-';
                s[j++] = end;
            }
            i += 3;
        } else {
            s[j++] = t[i++];
        }
    }
    s[j] = '\0';
}



/* No modifique este main */
int main(){
	char a[] = "a-z0-9";
	char b[] = "G-X";
	char c[] = "0-7";
	char s[100000];

	expand(s, a);
	printf("(%s)\n", s);
	expand(s, b);
	printf("(%s)\n", s);
	expand(s, c);
	printf("(%s)\n", s);
}