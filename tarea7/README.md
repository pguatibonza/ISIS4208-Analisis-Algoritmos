# Para generar los archivos de 100, 1000 y 10000 nodos

Se debe ejecutar generator.java. Este le pide la cantidad de nodos y genera en el archivo output.txt los ejes entre todos los nodos.
Con este generador se realizaron las pruebas al copiar el contenido dentro de test.in

# Para ejecutar este proyecto

Se debe abrir una instancia de CMD y pararse sobre la carpeta tarea7\src. Luego, se debe ejecutar el comando:

javac Solucion.java

Ahora se ejecuta el comando:

java Solucion < test.in > test.out

La salida de este proyecto queda en test.out de la forma:

< Algoritmo usado ><br />
< Tiempo en milisegundos ><br />
< Lista de la solución encontrada ><br />
< Tamaño de la solución encontrada >


Para modificar el archivo de pruebas test.in se debe usar el siguiente formato:

< Pareja de nodos ej: "0    1" ><br />
< Pareja de nodos ej: "1    3" ><br />
< Pareja de nodos ej: "3    2" ><br />
...<br />
< Pareja de nodos ej: "95    3" ><br />
< Algoritmo a usar (1, 2, 3 o 4)>
