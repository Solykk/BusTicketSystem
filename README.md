# README #

Cистема покупки билетов на автобус, которая должна содержать:

1) Entity:
	Водитель
		имя
		фамилия
		номер водительского удостоверения

	Автобус
		модель
		номер
		водитель

	Билет
		номер места
		стоимость
		оплачен или нет

	Рейс
		номер
		автобус
		билеты - Set

2) Соответствующие классы Repository и Service

3) ReSTful API, который позволит:
	добавить нового водителя
	добавить автобус
	добавить рейс
	добавить билеты на рейс
	изменить автобус на рейсе
	продать билет. При этом статус билета должен меняться на "оплачен" и должен возвращать информацию: 
номер рейса, номер и модель автобуса, имя и фамилию водителя, стоимость билета и номер купленного места в автобусе.

	Создавать UI не нужно, можно передавать / получать информацию, выполняя запросы с помощью консольной утилиты 
curl (доступна в Linux или Mac). Git commit нужно делать по мере выполнения шагов в перед к решению, 
там сделали rest-конец добавить автобус --- значит комитить надо код.

Ваш программный продукт мы будем проверять дергая API утилитой curl.

Инструменты: Intellij Idea CE, JDKv8, Spring boot, BitBucket

Для работы приложения и прохождения тестов необходимо создать:

DB = busStation, busStationTest

username = postgres
password = postgres

ReSTFulAPI:

/busStation/addDriver - добавить нового водителя
/busStation/addBu - добавить автобус
/busStation/addVoyage - добавить рейс
/busStation/bus/{id}/driver/{driverId} - добавить водителя на автобус
/busStation/voyage/{id}/bus/{busId} - добавить/изменить автобус на рейс
/busStation/voyage/{id}/addTicket - добавить один билет на рейс
/busStation/voyage/{id}/addTickets - добавить билеты на рейс
/busStation/voyage/{id}/ticket/{ticketId} - продать билет

/busStation/driver/{id} - найти водителя по id
/busStation/bus/{id} - найти автобус по id
/busStation/ticket/{id} - найти билет по id
/busStation/voyage/{id} - найти рейс по id

/busStation/findAllDrivers - возвращает список водителей в БД
/busStation/findAllBuses - возвращает список автобусов в БД
/busStation/findAllTickets - возвращает список билетов в БД
/busStation/findAllVoyages - возвращает список рейсов в БД

curl query example:

curl -H "Content-type: application/json" -X POST -d '{"license":"YY0000UU", "name":"Valera", "surname":"Pupkin"}' http://localhost:8090/busStation/addDriver
curl -H "Content-type: application/json" -X POST -d '{"number":"AA9898II", "model":"Ferrari"}' http://localhost:8090/busStation/addBus
curl -H "Content-type: application/json" -X POST -d '{"number":"ERES1"}' http://localhost:8090/busStation/addVoyage
curl -H "Content-type: application/json" -X POST http://localhost:8090/busStation/bus/{id}/driver/{driverId}
curl -H "Content-type: application/json" -X POST http://localhost:8090/busStation/voyage/{id}/bus/{busId}
curl -H "Content-type: application/json" -X POST -d '{"place":1, "price":20}' http://localhost:8090/busStation/voyage/{id}/addTicket
curl -H "Content-type: application/json" -X POST -d '[{"place":1, "price":20}, {"place":2, "price":20}]' http://localhost:8090/busStation/voyage/{id}/addTickets
curl -H "Content-type: application/json" -X POST http://localhost:8090/busStation/voyage/{id}/ticket/{ticketId}

curl -H "Content-type: application/json" -X GET http://localhost:8090/busStation/driver/{id}
curl -H "Content-type: application/json" -X GET http://localhost:8090/busStation/bus/{id}
curl -H "Content-type: application/json" -X GET http://localhost:8090/busStation/ticket/{id}
curl -H "Content-type: application/json" -X GET http://localhost:8090/busStation/voyage/{id}

curl -H "Content-type: application/json" -X GET http://localhost:8090/busStation/findAllDrivers
curl -H "Content-type: application/json" -X GET http://localhost:8090/busStation/findAllBuses
curl -H "Content-type: application/json" -X GET http://localhost:8090/busStation/findAllTickets
curl -H "Content-type: application/json" -X GET http://localhost:8090/busStation/findAllVoyages

