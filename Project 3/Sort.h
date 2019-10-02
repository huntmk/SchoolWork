//Sort.h
#ifndef SORT_H
#define SORT_H
class Sort
{
	public: 
		//pure virtual sort method
		virtual void sort(int*, int)= 0;
		//destructor
		virtual ~Sort(){}
};

#endif//SORT_H