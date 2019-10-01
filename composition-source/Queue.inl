// Honor Pledge:
//
// I pledge that I have neither given nor received any help
// on this assignment.

//
// size
//
template <typename T>
inline
size_t Queue <T>::size (void) const
{
	if (front == back == -1)
	{
		return 0;
	}
	else
	{
	return (back - front) + 1;
	}
		
}