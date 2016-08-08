#include "sbuf.h"
#include "sem.h"
#include <stdio.h>
#include <stdlib.h>
struct SBUF {
		int semaphore;
	/*	int maxNumberOfSems;
		char *pointer;
		char **; */
		SEM sem;
	 };

SBUF* sbufCreate(int maxNumberOfSems) {
/*	SBUF *array = sizeof(SBUF)*maxNumberOfSems;
	if(array == NULL) {
		fprintf(stderr,"sbuf create failed");
		fflush(stderr);
		exit(1);
	}
*/
	SBUF **array = calloc(sizeof(struct SBUF)*maxNumberOfSems);
	if(array == NULL) {
		fprintf(stderr,"sbuf create failed");
		fflush(stderr);
		exit(1);
	}
	return array;
}

int sbufAddSem(SBUF* cl, SEM* sem) {
	int i =0;
	while(cl != NULL) {
		i++;
		cl++; 
	}
	cl--;
	i-=1;
	struct SBUF new = malloc(sizeof(struct SBUF));
	if(new == NULL) {
		fprintf(stderr,"sbuf new element create failed");
		fflush(stderr);	
		exit(1);
	}
	new->semaphore = i;
	new->sem = sem;
	cl = new;

	return new->semaphore;
}

int sbufGetNumberOfSems(SBUF* cl) {
	while(cl != 0) 
		cl++;
	cl -= 1;
	return cl->semaphore; 
}

SEM* sbufGetSem(SBUF* cl,int index) {

	return cl[index]->sem;
}
