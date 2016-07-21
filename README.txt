A Stock and a Trade are mere containers for the data associated with them and check for its validity.
The different stock types are realized rather with a field than with inheritance as a stock might become preferred/common at a later time. With the enum, we can simply change the type at runtime without instantiating a whole new object.
The performance indicators are implemented in SuperSimpleStock, as e.g. a more advanced stock might calculate the KPI differently.
SuperSimpleStock as the main part of the Application holds stocks and corresponding trades in HashMaps (for quick lookups).
The trades corresponding to a stock are in a LinkedList that is sorted on initialization of the SuperSimpleStock, providing the most current trades first. This allows for the quickest way of filtering for relevant trades in the five minutes corridor.

Assumptions:
Stocks without a trade in the last five minutes have a price of 1.