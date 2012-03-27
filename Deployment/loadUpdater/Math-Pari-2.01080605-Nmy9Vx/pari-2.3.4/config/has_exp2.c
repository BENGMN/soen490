#include <math.h>
char (*f)() = exp2;
int main(){ return f != exp2; }
