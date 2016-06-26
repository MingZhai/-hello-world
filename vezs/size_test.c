1 #include "priority_queue.h"
int main(void){
	int length;
	Element e ;
	e.value = 10;
	e.priority = 5;
	insert(&e);
	length = size();
	printf("size = %d\n",length);
	return 0;
}
