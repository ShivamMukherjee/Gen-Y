import java.util.ArrayList;

class MyNode<T>
{
	T data;
	MyNode<T> next;
	MyNode<T> prev;

	MyNode()
	{
	}

	MyNode(T data)
	{
		this.data = data;
	}
}

class MyList<T>
{
	MyNode<T> head, tail = head;
	int size = 0;

	MyList()
	{
	}

	MyList(T[] stuff)
	{
		for (T x: stuff)
		{
			this.pushBack(x);
		}
	}

	MyList(ArrayList<T> stuff)
	{
		for (T x: stuff)
		{
			this.pushBack(x);
		}
	}

	void pushBack(T data)
	{
		if (head == null)
		{
			head = new MyNode<>(data);
			tail = head;
			tail.prev = head;
			head.next = tail;
//			System.out.println("Head: " + head.data);
		}
		else
		{
			MyNode<T> temp = new MyNode<>(data);
			temp.prev = tail;
			tail.next = temp;
			tail = temp;
//			System.out.println("Tail: " + tail.data);
		}
		++size;
	}

	void pushFront(T data)
	{ 
		if (tail == null)
		{
			tail = new MyNode<>(data);
			head = tail;
			tail.prev = head;
			head.next = tail;
//			System.out.println("Tail: " + tail.data);
		}
		else
		{
			MyNode<T> temp = new MyNode<>(data);
			head.prev = temp;
			temp.next = head;
			head = temp;
//			System.out.println("Head: " + head.data);
		}
		++size;
	}

	T popBack()
	{
		T data = tail.data;
		tail = tail.prev;
		tail.next = null;
		--size;
		return data;
	}

	T popFront()
	{
		T data = head.data;
		head = head.next;
		head.prev = null;
		--size;
		return data;
	}

	@Override
	public String toString()
	{
		String str = "[";
		MyNode<T> itr = head;

		while (itr != null)
		{
			if (itr.next == null)
			{
				str += itr.data;
			}
			else
			{
				str += (itr.data + ", ");
			}
			itr = itr.next;
		}
		str += "]";

		return str;
	}

	void addAll(T[] stuff)
	{
		for (T x: stuff)
		{
			this.pushBack(x);
		}
	}

	ArrayList<T> asArrayList()
	{
		ArrayList<T> list = new ArrayList<>();
		MyNode<T> itr = head;

		while (itr != null)
		{
			list.add(itr.data);
			itr = itr.next;
		}

		return list;
	}
}

class MyListTest
{
	public static void main(String[] args)
	{
		String[] array = {"Hello", "World", "this", "is", "a", "test"};
		MyList<String> list = new MyList<>(array);

		list.pushBack("yo");
		list.pushBack("whatup");
//*/
		for (int i = 0; i < 2; ++i)
		{
			list.popFront();
			list.popBack();
		}

		list.pushFront("Lol");
//*/
//		System.out.println(list.head.data);

		System.out.println("\nsize: " + list.size);
		System.out.println("list: " + list);
	}
}