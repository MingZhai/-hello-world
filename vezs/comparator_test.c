#include "priority_queue.h"

int main(void){
	Element e1, e2;
	e1.value = 5;
	e1.priority = 2;
	e2.value = 10;
	e2.priority = 5;
	comparator( &e1, &e2);
	return 0;
}
