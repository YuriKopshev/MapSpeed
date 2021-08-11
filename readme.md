**Краткий отчет о работе ConcurrentHashMap и SynchronizedMap**

1. При проведении операции записи обе Maps работают примерно за одно и тоже время, 
даже при кратном увеличении количества записываемых элементов.


2. При проведении операции чтения SynchronizedMap работает всегда быстрее, за постоянное время,
при любом количестве элементов.

Время не чтение:

Чтение 1000 элементов:

ConcurrentHashMap - 3

SynchronizedMap - 1

Чтение 10000 элементов:

ConcurrentHashMap - 8

SynchronizedMap - 1

Чтение 100000 элементов:

ConcurrentHashMap - 9

SynchronizedMap - 1