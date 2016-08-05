#include <unistd.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <string.h>
#include <stdio.h>

#define maxargs 50

int bg_execute(char *arglist[],char *argbuf);
int execute(char *arglist[],char *argbuf);
void makestring(char *buf);
void mychdir();
int numargs =0;
char **arglist;

int main() {
	
	arglist = calloc(20,sizeof(char*));
	char buf[80];
/*	int maxargs = sysconf(_SC_LINE_MAX);
	printf("maximal lange einer eingabezeile: %ld\n",maxargs);
	*/
	int ch=0;
	char argbuf[maxargs];
	while(1) {
			getcwd(buf,sizeof(buf));
			printf("%s:",buf);
			buf[0] = '\0';
			ch = fgets(argbuf,maxargs,stdin);
			if(ch == EOF)
				break;				
			if(ch && *argbuf != '\n') {
 					 makestring(argbuf);
					 if(strcmp(arglist[numargs-1],"&")==0) {
							bg_execute(arglist,argbuf);
					 }
					 else if(strcmp(arglist[0],"cd")==0) {
						    mychdir();
				     }
					 else {
					//		printf("point 3");
						 	execute(arglist,argbuf);
				     } 
       		memset(arglist,0,numargs);
			numargs = 0;
			}
	}
	return 0;
}

void mychdir() {
	if(arglist[1]==NULL)
		chdir("/home/cip/nf2015/jy43jiry/");	
	chdir(arglist[1]);
}

void makestring(char *buf) {
	char *delim = " \n";
	arglist[numargs] = strtok(buf,delim);
	printf("arglist[%d] =%s\n",numargs,arglist[numargs]);
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
						for(int i=0;i<numargs;i++) {
							printf("%s%s",arglist[i]," ");
						}
						printf("] = %d\n",exitstatus);
		}
}

int bg_execute(char *arglist[],char *argbuf) {
		int pid,exitstatus;
		arglist[numargs-1]= NULL;
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
						;
					/*	while(wait(&exitstatus) != pid)
						printf("Exitstatus[");
						for(int i=0;i<numargs;i++) {
							printf("%s%s",arglist[i]," ");
						}
						printf("] = %d\n",exitstatus); */
		}
}
