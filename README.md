# vladimir-vladimirov-employees
From a given text file with employee-tasks entries returns the employees that had spent the most time working together

The task was provided as a test for my skills from a potential employer.

Provided file should be in format:

EmpID, ProjectID, DateFrom, DateTo
EmpID, ProjectID, DateFrom, DateTo
EmpID, ProjectID, DateFrom, DateTo

The file shouldn't contain any blank lines.

The assignment written in Bulgarian is the following

---------------------------------------
Двойка служители, които най-дълго време са работили в екип
Данни
Даден е текстов файл във формат:
EmpID, ProjectID, DateFrom, DateTo
Примерни данни:
143, 12, 2013-11-01, 2014-01-05
218, 10, 2012-05-16, NULL
143, 10, 2009-01-01, 2011-04-27
...
Задължителни условия
1) Да се напише приложение, което намира двойката служители, които най-дълго време са работили заедно по общи проекти.
2) DateTo приема и стойност „NULL“ (което е еквивалент на „днес“).
3) Данните да могат да се подават към програмата от текстов файл
4) Програмата да може да се пусне без да е необходимо да се правят каквито и да е промени в кода т.е. след “checkout” на кода и импорт в IDE.
5) Програмата да може да тръгне и да покаже резултата в конзола.
6) Да бъде спазен “code convention”, в зависимост на какъв език се разбработва програмата:
a. Java - (http://www.oracle.com/technetwork/java/codeconvtoc-136057.html )
b. C# - (https://docs.microsoft.com/en-us/dotnet/csharp/programming-guide/inside-a-program/coding-conventions )
c. Други виж тук (https://en.wikipedia.org/wiki/Coding_conventions )
7) Решение на задачата да бъде сложено в github
a. Repository Name да бъде „FirstName-LastName-employees (т.е. ivan-ivanov-employess)
8) Да бъде направена в рамките на един ден.
Бонус условия
1) Да се направи UI, където потребителя да може да избере файл от файловата система и след избирането на файла да види резултата в “datagrid” със следните колони Employee ID #1, Employee ID #2, Project ID, Days worked
2) Да се поддържа повече от един или всички (за „всички“ даваме много точки) формати на дати.
---------------------------------------