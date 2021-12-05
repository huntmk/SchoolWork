#include <algorithm> //for max function
#include <iostream> //output

//knapsack function
// limit - weight capacity
// value - holds value of items
// weight - holds weight of items
// n - number of items
int knapsack(int limit, int value[], int weight[], int n)
{
	//base case: if limit or weight == 0, then return 0
	if (limit == 0 || n == 0)
		return 0;

	//if the weight of the nth item is more than capacity ,
	//then knapsack function will be called again with one less item

	//Note: because of the indexing, to the nth item, we use n-1
	//Ex. n = 3, indexes of weight[0,1,2] , so the 3rd item is in index 2.
	//Hence n - 1

	if (weight[n - 1] > limit)
	{
		return knapsack(limit, value, weight, n - 1);
	}
	//we need to get the max value to put in knapsack
	//so from the above condition, if weight[n - 1] is not greater than limit
	//then include the nth item include case: value[n-1] + knapsack(limit - weight[n-1],value,weight,n-1)
	//this adds the nth value to the knapsack call for the next nth item

	//the other case is when not including the nth item: so this knapsack call just goes to the next nth item
	else
	{
		//return the max of:
		return std::max(value[n - 1] + knapsack(limit - weight[n - 1], value, weight, n - 1), knapsack(limit, value, weight, n - 1));

	}



}
int main()
{
	//declare arrays 
	int n = 5;
	int limit = 10;
	int values[] = { 40,35,20,10,5};
	int weight[] = { 1,2,3,4,5 };
	std::cout << knapsack(limit, values, weight, n);

}
