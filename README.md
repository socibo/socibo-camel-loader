cbrru-agent ![Project status](http://stillmaintained.com/dulanov/cbrru-agent.png)
=======

Проект создан для демонстрации возможностей [Apache Camel](http://camel.apache.org) в области интеграции веб-сервисов на примере получения [текущих курсов валют ЦБ РФ](http://www.cbr.ru/scripts/root.asp) через специально созданный под эти цели [Twitter-аккаунт](http://twitter.com/cbrru). Пожелания и замечания по работе сервиса присылайте на dulanov (at gmail).

Функционал
-----------------

1. Публикация текущих курсов валют ЦБ РФ с указанием изменения по сравнению с предыдущими значениями (доллар, евро).
2. *TODO* Запрос текущих значений курсов валют ЦБ РФ командой в которой присутствуют слова `"курс"` или `"rate"`: `"@cbrru ... курс[ы] ..."` или `"@cbrru ... rate[s] ..."`.
3. *TODO* Выдача краткого описания по любому сообщению акромя запроса текущих значений курсов валют (см. предыдущий пункт), например, `"@cbrru справка"` или `"@cbrru ?"`.
4. *TODO* Отправка краткого описания о работе агента всем новым фоллоуэрам.

Краткое описание
-------------------------

Отправляется автоматически всем новым фоллоуэрам и выдается в качестве справки:

`@[new_follower] Спасибо что следите за аккаунтом cbrru.`

`@[new_follower] Теперь каждый рабочий день в 15:15:31 (+0300) вы будете получать новые курсы валют ЦБ РФ.`

`@[new_follower] Чтобы запросить текущие значения курсов валют пошлите сообщение вида "@cbrru курс" или "@cbrru rate".` 

`@[new_follower] Данную краткую справку можно запросить отправив любое сообщение не содержащее слов "курс" или "rate", например, "@cbrru ?".`

`@[new_follower] Подробное описание работы агента вы найдете по адресу http://github.com/dulanov/cbrru-agent.`

EIP-архитектура
-----------------------

Реализовано в соответствии с EIP-паттернами (Enterprise Integration Patterns - интеграционные паттерны интеграции), описаными в книге Грегори Хопа, Бобби Вульфа "[Шаблоны интеграции корпоративных приложений](http://www.ozon.ru/context/detail/id/3083192/)":

![EIP-диаграмма](http://github.com/dulanov/cbrru-agent/raw/master/images/eip-architecture.png)

На диаграмме изображены две внешние системы - веб-сервисы [ЦБ РФ](http://www.cbr.ru/scripts/root.asp) и [Twitter API](http://apiwiki.twitter.com/Twitter-API-Documentation), три интеграционных процесса (рассмотрены ниже), а также каналы передачи сообщений по буфферизации входящих и исходящих сообщений Twitter.

1. **Новые курсы валют (левая ветка на EIP-диаграмме)**.
	1. Раз в сутки (15:15:31 +0300) и при запуске происходит забор курсов валют [в формате XML с сайта ЦБ РФ](http://www.cbr.ru/scripts/Root.asp?Prtid=SXML).
	2. XML-сообщение преобразуется во внутренний XML-формат приближенный к структуре объектов POJO и только для доллара, евро и йены.
	3. Сообщение обогащается текущими значениями курсов валют из переменной хранимой в оперативной памяти.
	4. Фильтрация на предмет уже опубликованных ранее значений курсов валют.
	5. Сохранение новых значений курсов валют в оперативной памяти и одновременное их помещение в канал исходящих сообщений [Twitter-аккаунта](http://twitter.com/cbrru).
2. **Текущие курсы валют (средняя ветка на EIP-диаграмме)**.
	1. Сообщение содержащее ник фоллоуэра обогащается текущими значениями курсов валют и помещается в канал исходящих сообщений [Twitter-аккаунта](http://twitter.com/cbrru).
3. **Справка (правая ветка на EIP-диаграмме)**.
	1. Сообщение содержащее ник фоллоуэра обогащается справочным сообщением (жестко прописано в конфигурационном файле).
	2. Сообщение разбивается на несколько сообщений (обход ограничения в 140 символов) и все они помещаются в канал исходящих сообщений [Twitter-аккаунта](http://twitter.com/cbrru).

Детали реализации
---------------------------

Реализовано на [языке программирования Groovy](http://groovy.codehaus.org/) средствами [Apache Camel](http://camel.apache.org).

Дополнительно использовались:

* [SLF4J](http://www.slf4j.org) и [LogBack](http://logback.qos.ch) - журналирование вместо [commons-loggings](http://commons.apache.org/logging/) и [Log4J](http://logging.apache.org/log4j/) соответственно
* [HornetQ](http://www.jboss.org/hornetq) - шина по обмену сообщениями.
* [Twitter4J](http://twitter4j.org) - поддержка [Twitter API](http://apiwiki.twitter.com/Twitter-API-Documentation).
* [Gradle](http://www.gradle.org) - сборка проекта.

**Модель предметной области ([POJO - Plain Old Java Objects](http://ru.wikipedia.org/wiki/POJO))**:

![EIP-диаграмма](http://github.com/dulanov/cbrru-agent/raw/master/images/classdiagram.png)

| Имя класса | Пакет | Описание |
|:----------------|:----------|:-------------|
| RouteBuilder | org.apache.camel.builder | Оригинальный Java-класс Apache Camel для интеграцинной логики
| GroovyRouteBuilder | org.apache.camel.language.groovy | Поддержка скриптования на Groovy (при желании можно выкинуть)
| CurrencyCode | com.github.dulanov.cbrru | Валюта
| CurrencyRate | com.github.dulanov.cbrru | Курс валюты
| CurrencyRates | com.github.dulanov.cbrru | Курсы валют
| CamelRouteBuilder | com.github.dulanov.cbrru | Реализация описанной выше в виде EIP-диаграммы интеграционной логики

**Реализация получения новых курсов валют (левая ветка на EIP-диаграмме)**:

<pre>
from('quartz://timer?cron=31+15+15+?+*+*')
.to('direct:receiveCBRRates')
.enrich('direct:previousRates', {cur, prev -> cur.in.body = cur.in.body.withPrevious(prev.in.body) ; cur} as AggregationStrategy)
.to("log:${this.class.package.name}?level=INFO&multiline=true")
.process({twitter.updateStatus(it.in.body as String)} as Processor)

from('direct:previousRates')
.to(String.format('http://www.cbr.ru/scripts/XML_dynamic.asp?date_req1=%1$td/%1$tm/%1$tY&date_req2=%2$td/%2$tm/%2$tY&VAL_NM_RQ=R01235'
    , new DateMidnight().minusDays(14).toDate(), new DateMidnight().plusDays(1).toDate()))
.transform(body(Date[].class))
.transform().groovy('exchange.in.body[-2]')
.process({it.in.setHeader(Exchange.HTTP_QUERY, String.format('date_req=%1$td/%1$tm/%1$tY', it.in.body))} as Processor)
.to('direct:receiveCBRRates')

from('direct:receiveCBRRates')
.to('http://www.cbr.ru/scripts/XML_daily.asp')
.transform(body(CurrencyRates.class))
</pre>

Срабатывание происходит раз в сутки в 15:15:31, далее запрашиваются текущие значения курсов валют в XML-формате по адресу 'http://www.cbr.ru/scripts/XML_daily.asp', после чего они преобразуются в Java-объект CurrencyRates, затем по похожему сценарию дозапрашиваются курсы валют за предыдущую дату и расчитывается разница (дельта), и, наконец, полностью сформированные значения курсов валют отправляются в журнальные файлы и публикуются в Twitter.

Структура папок
-----------------------

За основу аналогичная [стандартной структуре папок Apache Maven](http://maven.apache.org/guides/introduction/introduction-to-the-standard-directory-layout.html):

**src/main/groovy** - интеграционная логики и модель предметной области.

**src/main/resources** - конфигурационные файлы [Logback](http://logback.qos.ch) и пр.

**src/test/groovy** - модульное тестирование интеграционной логики.

**src/test/resources** - данные для проведения модульного тестирования.

**build.properties.template** - шаблон настроек для [Twitter-аккаунта](http://twitter.com/cbrru) и содержимое справки.

**README.md** - данное описание [в формате Markdown](http://daringfireball.net/projects/markdown/syntax).

**build.gradle** - файл сборки [Gradle](http://www.gradle.org).

Развертывание
---------------------

`gradle -q run`

История
------------

### Версия 1.0 (21 апреля 2010)

* Базовый функционал по публикации текущих курсов валют ЦБ РФ для доллара, евро и юаня.

### Версия 1.1 (27 мая 2010)

* Указание изменений курсов валют по сравнению с предыдущими значениями.

Лицензия
-------------

Copyright © 2010 CPM Ltd.

Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at

<http://www.apache.org/licenses/LICENSE-2.0>

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License. 
