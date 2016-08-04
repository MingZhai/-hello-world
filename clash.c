#include <unistd.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <string.h>
#include <stdio.h>

#define maxargs 20

int execute(char *arglist[],char *argbuf);
void makestring(char *buf);

int numargs =0;
char **arglist;

int main() {
	
	arglist = malloc(sizeof(char*)*20);
	char buf[80];
	getcwd(buf,sizeof(buf));
/*	int maxargs = sysconf(_SC_LINE_MAX);
	printf("maximal lange einer eingabezeile: %ld\n",maxargs);
	*/

	char argbuf[maxargs];

	while(1) {
			printf("%s:",buf);
			if(fgets(argbuf,maxargs,stdin) && *argbuf != '\n') {
					 makestring(argbuf);
					 execute(arglist,argbuf);
			       		 memset(arglist,0,numargs);
					 numargs = 0;
			}
			
	}
	return 0;
}

void makestring(char *buf) {
	char *delim = " \n";
	arglist[numargs] = strtok(buf,delim);
	printf("arglist[%d] = %s\n",numargs,arglist[numargs]);
	numargs++;
	while((arglist[numargs] = strtok(NULL,delim))) {
		printf("arglist[%d] =%s\n",numargs,arglist[numargs]);
		numargs++;
	}
	arglist[numargs] = NULL;

}

int execute(char *arglist[],char *argbuf) {
		int pid,exitstatus;

		pid = fork();
		switch(pid) {
				case -1:
						perror("fork failed");
						exit(1);
				case 0:
						execvp(arglist[0],arglist);
						perror("execvp failed");
						exit(1);
				default:
						while(wait(&exitstatus) != pid)
								;
						printf("Exitstatus[");
						puts(argbuf);
						printf("] = %d\n",exitstatus);
		}
}
