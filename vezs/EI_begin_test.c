#include "priority_queue.h"

int main(void){
	Elements *self;
	El_Iterator * ret = El_begin(Elements *self);
	if(*ret->position == *self->elements){
		printf("EI_begin successful\n");
	}
	return 0;
}
