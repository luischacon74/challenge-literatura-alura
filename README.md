# challenge-literatura-alura
challenge-literatura desarrollado por Luis Felipe Chacon Segura



Para el desarrollo de esta proyecto se tuvo en cuenta pasos como la definicion de los objetivos que se plantea para el desarrollo del este proyecto y sus alcances, para ello nos conectamos a la API para asi consumir los datos y analizar los datos que se obtienen en el json.

Requisitos
1. obtener y guardar los siguientes datos,Un libro debe tener los siguientes atributos:
Título;
Autor;
Idiomas;
Número de Descargas.

2. Se debe ver un listado con base en el idioma que uno o mas libros fueros escritos.

3. Búsqueda de libro por título

4. Lista de todos los libros

5. Lista de autores

6. Listar autores vivos en determinado año



para solucionar esto primero debimos almacenar cada datos del json que ibamos a obtener y los datos que deseabamos almacenar y se desarrollo con la siguiente estructura:

model: Contiene las clases que representan la estructura de los datos, como Libro y Autor.
principal: Contiene la lógica principal del sistema, incluyendo el flujo de interacción del usuario.
repository: Maneja la persistencia de datos con JPA y las consultas personalizadas.
service: Contiene los servicios que gestionan la comunicación con la API y la conversión de datos.
utils: Incluye utilidades para el manejo de datos, entrada del usuario y operaciones auxiliares.
view: Maneja la comunicación visual entre la aplicación y el usuario.


Se apoyo el desarrollo de este proyecto con la implementacion de clases de interfaces:
AutorRepository: Define métodos para realizar solicitudes a la API.
LibroRepository: Interfaz para la comunicación de la app hacia el usuario.
IComunicacionAppUsuario: Interfaz para la comunicación de la app hacia el usuario.
IIngresarDatos: Interfaz para la comunicación del usuario hacia la app.
IConvertirDatos: convierte los datos suministrados por la api a clases.

Explicacion de uso de opciones:

El aplicativo presenta las siguientes opciones para interactuar con la base de datos y la API Gutendex:

Menú de Opciones

1. Buscar libro por título
Permite buscar un libro específico en la API Gutendex por título y agregarlo a la base de datos, si no existe.

2. Buscar libro por idioma en la base de datos
Filtra y muestra los libros almacenados en la base de datos por el idioma seleccionado.

3. Buscar libros por autor en la base de datos
Busca y muestra todos los libros en la base de datos que estén relacionados con un autor específico.

4. Mostrar todos los libros existentes en la base de datos
Lista todos los libros almacenados en la base de datos.

5. Buscar autor por rango de fecha de nacimiento en la base de datos
Muestra los autores nacidos dentro de un rango de fechas específico.

6. Buscar autor por edad
Filtra autores que tengan una edad exacta especificad

7. Salir
Finaliza la ejecución del programa.


Evidencias de funcionamiento del proyecto:
Menu de incio
![image](https://github.com/user-attachments/assets/52d9620e-974f-4d2e-8638-903f3874f629)
opcion 1:
se busca el libro, si el libro ya se encuentra, no permite que se vuelva a almacenar 
![image](https://github.com/user-attachments/assets/133850bd-3429-4360-8cf0-757dc40ed62e)
opcion 2:
busqueda por idioma se creo una opcion la cual indicara los idiomas disponibles por el momento solo se tuvo con en, asi que se debe seleccionar la opcion 1 y mostrara todos los libros relacionados a ese idioma, tambien se valido de que la entrata fuera valida
![image](https://github.com/user-attachments/assets/4d85c5e2-b675-4820-9ce4-df23664b47eb)

opcion 3:
la opcion 3 consiste en buscar por medio del nombre del autor los libros que hay en la base de datos
![image](https://github.com/user-attachments/assets/e7b1b09f-b0fe-4ee2-88ad-0d4bcd5ad187)

opcion 4:
muestra todos los libros en la base de datos y algunos datos mas.
![image](https://github.com/user-attachments/assets/93aacd82-bdcb-4a79-affc-badac68f154b)

opcion 5:
se creo para buscar en un rango de fechas de nacimiento del autor y asi obtener los libros que hay
![image](https://github.com/user-attachments/assets/fb3aea87-ad41-47b7-a173-d87c494f00f8)
![image](https://github.com/user-attachments/assets/3a14b0c4-4b2c-485c-8fcb-e827b2c63432)

opcion 6:
opcion apra buscar por edad 
![image](https://github.com/user-attachments/assets/ac2546ee-8425-47a6-87cc-80f222a32351)

opcion 7:
pasa salir del programa 





