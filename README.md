# gamexis


# Pasos para hacer build en desktop

    ```
        mvn -Pdesktop package -DskipTests
        cd desktop 
        cd target 
        java -jar gamexis-desktop-1.0.0-jar-with-dependencies.jar

    ```

# Pasos para hacer build con make. 
 1. Tener make instalado. 
 2. Correr ```make help``` para ver los comando disponibles
 3. Correr ```make run ``` para hacer build sin tests y correr.