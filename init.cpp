#include "SWI-Prolog.h"
#include "SWI-Stream.h"
#include "SWI-cpp.h"

bool initialized = false;

void init() {
	if (!initialized) {
		const char* av[2];
		av[0] = "Caller";
		av[1] = "-q"; //don't display SWI Prolog banner

		//the following two lines are a workaround 
		const char* dir = "SWI_HOME_DIR=/usr/bin/swipl";
		putenv((char*)dir); 

		PL_initialise(2, (char**)av);

		//PlCall("consult('coin.pl')");
		initialized = true;	
	}
}
