//Sort.h
#ifndef NEWSORT_H
#define NEWSORT_H
class newSort
{
	public: 
		//pure virtual sort method
		virtual void sort(int*, int,int)= 0;
		//destructor
		virtual ~newSort(){}
};

#endif//NEWSORT_H