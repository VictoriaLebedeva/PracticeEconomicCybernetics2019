#include "HeapNative.h";
#include <iostream>;
using namespace std;
JNIEXPORT void JNICALL Java_HeapNative_heapNative
  (JNIEnv *env, jclass class, jstring myString) {
  	HANDLE hHeap;
	hHeap = HeapCreate(0, 0x01000, 0);
	if (hHeap != NULL)
	{
		cout << "Heap was Created!" << endl;
		if (HeapDestroy(hHeap) == 0)
			cout << "Error delete Heap" << endl;
		else
			cout << "Heap was Destroyed" << endl;		
	}
	else
		cout << "Error Create Heap" << endl;
};
