#include <math.h>
char (*f)() = log2;
int main(){ return f != log2; }
