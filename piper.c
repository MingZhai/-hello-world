#include <pthread.h>
#include <stdio.h>

void *mywrite(void *pipe);
int main(int argc, char *argv[]) {
		pthread_t tids[100];
		int running =1;
		char buf[100];
		while(running) {
			fgets(buf,100,stdin);
			for(int i =1;i<argc-1;i++) {
					pthread_create(&tids[i-1], NULL, mywrite, &argv[i]);
			}
			running =0;
		}
		return 0;
}

void *mywrite(void *pipe) {
//	FILE 
	fopen(pipe,"w");
	fprintf(stderr,"There are currently 0 clients");
	fflush(stderr);
	return NULL;
}
