#include "SWI-Prolog.h"
#include "SWI-Stream.h"
#include "SWI-cpp.h"
#include "Probability.h"
#include <cmath>

extern void init();


JNIEXPORT jfloat JNICALL Java_Probability_probIncident  (JNIEnv * p1,  jclass p2){
init();
term_t ansoutcome, proboutcome;
functor_t poutcome;
poutcome = PL_new_functor(PL_new_atom("outcome"), 1);
ansoutcome = PL_new_term_ref();
proboutcome = PL_new_term_ref();

module_t module_pl = PL_new_module(PL_new_atom("cplint.pl"));
PL_cons_functor(ansoutcome, poutcome, proboutcome);
PlCall("consult('cplint.pl')");
double probVal;
if (PL_call(ansoutcome, module_pl)) {
	PL_get_float(proboutcome, &probVal);
}
return (jfloat) std::ceil(probVal * 100) / 100;
}
