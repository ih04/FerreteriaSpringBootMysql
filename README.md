# Ferretería Spring Boot + MySQL

Aplicación web de ejemplo para gestionar el inventario de artículos de una ferretería. El proyecto combina una API REST desarrollada con **Java y Spring Boot**, persistencia mediante **Spring Data JPA/Hibernate**, una base de datos **MySQL** y una interfaz web sencilla construida con **HTML, CSS y JavaScript/jQuery**.

> **Alcance actual:** el código implementa operaciones CRUD para artículos (crear, consultar, actualizar y eliminar). Aunque la interfaz muestra enlaces a Proveedores, Ventas, Usuarios, Almacén y Reportes, esas secciones son elementos visuales y no tienen funcionalidad implementada en el código revisado.

## Índice

- [Funcionalidades](#funcionalidades)
- [Tecnologías](#tecnologías)
- [Arquitectura](#arquitectura)
- [Estructura del proyecto](#estructura-del-proyecto)
- [Modelo de datos](#modelo-de-datos)
- [API REST](#api-rest)
- [Cómo funciona una petición](#cómo-funciona-una-petición)
- [Interfaz web](#interfaz-web)
- [Configuración y ejecución](#configuración-y-ejecución)
- [Ejecución con Docker](#ejecución-con-docker)
- [Pruebas](#pruebas)
- [Limitaciones y mejoras recomendadas](#limitaciones-y-mejoras-recomendadas)

## Funcionalidades

La aplicación permite trabajar con artículos de inventario:

- **Listar artículos:** obtiene todos los registros guardados.
- **Consultar por identificador:** busca un artículo concreto mediante su ID.
- **Crear artículos:** guarda el nombre, el precio y las existencias.
- **Actualizar artículos:** modifica los datos de un artículo existente.
- **Eliminar artículos:** borra un artículo por su ID.
- **Persistencia en MySQL:** los datos se almacenan en una tabla relacional.
- **Conversión entre entidad y DTO:** separa el modelo persistido de los datos que expone la API.

## Tecnologías

| Tecnología | Uso |
|---|---|
| Java 25 | Lenguaje y versión configurada para compilar el proyecto |
| Spring Boot 4.0.1 | Arranque y configuración de la aplicación |
| Spring Web | Controladores y endpoints HTTP/REST |
| Spring Data JPA | Acceso a datos mediante repositorios |
| Hibernate | Implementación ORM para mapear clases Java a tablas |
| MySQL 8.0 | Base de datos relacional |
| Maven Wrapper | Compilación y ejecución con Maven |
| Lombok 1.18.40 | Generación de métodos comunes de la entidad |
| HTML, CSS y JavaScript | Interfaz de usuario |
| jQuery 3.6.0 | Peticiones AJAX y manipulación del HTML |
| SweetAlert2 11 | Ventanas de diálogo y mensajes |
| Docker / Docker Compose | Contenedorización de la aplicación y la base de datos |

Las dependencias y versiones configuradas se encuentran en `pom.xml`. La configuración exacta del entorno Java debe respetar la versión requerida por el proyecto.

## Arquitectura

El backend está organizado por responsabilidades:

```text
Navegador
   |
   | HTTP / JSON
   v
index.html (interfaz)
   |
   v
ArticuloController
   |
   v
ArticuloService
   |
   +--> ArticuloMapper <--> ArticulosDTO
   |
   v
Repo_articulos (Spring Data JPA)
   |
   v
Hibernate / JPA
   |
   v
MySQL: db_ferreteria.articulos
```

### Responsabilidad de cada capa

1. **Interfaz (`index.html`)**: muestra los controles de inventario, recoge los datos introducidos por el usuario y llama a la API mediante AJAX.
2. **Controlador (`ArticuloController`)**: publica las rutas HTTP y delega el trabajo en el servicio.
3. **Servicio (`ArticuloService`)**: contiene la lógica de consulta, alta, modificación y borrado.
4. **DTO (`ArticulosDTO`)**: representa los datos que entran y salen de la API.
5. **Mapper (`ArticuloMapper`)**: convierte entre el DTO y la entidad JPA.
6. **Repositorio (`Repo_articulos`)**: utiliza los métodos CRUD que proporciona `JpaRepository`.
7. **Entidad (`Articulos`)**: define el mapeo Java de la tabla `articulos`.
8. **MySQL**: almacena de forma persistente los registros.

## Estructura del proyecto

La aplicación Maven se encuentra dentro de la carpeta `ferreteria/`.

```text
FerreteriaSpringBootMysql/
├── .vscode/
│   └── settings.json
└── ferreteria/
    ├── .mvn/wrapper/
    │   └── maven-wrapper.properties
    ├── src/
    │   ├── main/
    │   │   ├── java/com/hibernate/
    │   │   │   ├── FerreteriaApplication.java
    │   │   │   ├── Controller/
    │   │   │   │   └── ArticuloController.java
    │   │   │   ├── DTO/
    │   │   │   │   └── ArticulosDTO.java
    │   │   │   ├── Mapper/
    │   │   │   │   └── ArticuloMapper.java
    │   │   │   ├── Repository/
    │   │   │   │   └── Repo_articulos.java
    │   │   │   ├── Service/
    │   │   │   │   └── ArticuloService.java
    │   │   │   └── entity/
    │   │   │       └── Articulos.java
    │   │   └── resources/
    │   │       ├── application.properties
    │   │       └── static/
    │   │           └── index.html
    │   └── test/
    │       └── java/com/hibernate/ferreteria/
    │           └── FerreteriaApplicationTests.java
    ├── Dockerfile
    ├── docker-compose.yml
    ├── init.sql
    ├── mvnw
    ├── mvnw.cmd
    └── pom.xml
```

### Archivos principales

- `FerreteriaApplication.java`: punto de entrada. Arranca Spring Boot y, después del inicio, consulta los artículos y los imprime en la consola.
- `Controller/ArticuloController.java`: define los endpoints REST bajo `/articulos`.
- `Service/ArticuloService.java`: implementa las operaciones CRUD y coordina repositorio y mapper.
- `Repository/Repo_articulos.java`: interfaz que extiende `JpaRepository<Articulos, Long>`, por lo que hereda operaciones como `findAll`, `findById`, `save`, `existsById` y `deleteById`.
- `entity/Articulos.java`: entidad JPA que se corresponde con la tabla `articulos`.
- `DTO/ArticulosDTO.java`: objeto de transferencia usado por el controlador y el servicio.
- `Mapper/ArticuloMapper.java`: contiene las conversiones estáticas `toDTO` y `toEntity`.
- `resources/application.properties`: propiedades de Spring, puerto y configuración de JPA/Hibernate.
- `resources/static/index.html`: página web servida como contenido estático por Spring Boot.
- `init.sql`: definición SQL inicial de la tabla.
- `Dockerfile`: crea la imagen de la aplicación Java a partir del JAR compilado.
- `docker-compose.yml`: define los servicios de MySQL y la aplicación.
- `pom.xml`: dependencias, versión de Java y configuración de compilación Maven.
- `FerreteriaApplicationTests.java`: prueba básica de carga del contexto de Spring.

## Modelo de datos

La entidad `Articulos` se mapea a la tabla `articulos`.

| Campo | Tipo Java | Tipo SQL definido en `init.sql` | Descripción |
|---|---|---|---|
| `id` | `Long` | `BIGINT` | Identificador único, autoincremental y clave primaria |
| `nombreArticulo` | `String` | `VARCHAR(50)` | Nombre del artículo |
| `precio` | `Double` | `DECIMAL(6,2)` | Precio del artículo |
| `existencia` | `Integer` | `INT` | Unidades disponibles |

La entidad utiliza `@Entity` y `@Table(name = "articulos")`. El campo `id` usa `@Id` y `@GeneratedValue(strategy = GenerationType.IDENTITY)`, de modo que el identificador lo genera la base de datos. Lombok, mediante `@Data`, `@NoArgsConstructor` y `@AllArgsConstructor`, genera métodos habituales como getters, setters, `toString`, `equals` y constructores.

El script `init.sql` crea la tabla con los cuatro campos obligatorios y establece `id` como clave primaria. El proyecto también configura `spring.jpa.hibernate.ddl-auto=update`, que permite a Hibernate actualizar el esquema al iniciar; conviene elegir una estrategia explícita para cada entorno y utilizar migraciones versionadas en proyectos de producción.

## API REST

Todas las rutas del controlador están bajo el prefijo `/articulos`. El servidor está configurado en el puerto `8080`.

| Método | Ruta | Función | Resultado previsto |
|---|---|---|---|
| `GET` | `/articulos` | Lista todos los artículos | Lista JSON de DTO |
| `GET` | `/articulos/{id}` | Busca por ID | Un DTO o error si no existe |
| `POST` | `/articulos` | Crea un artículo | DTO guardado |
| `PUT` | `/articulos/{id}` | Actualiza un artículo existente | DTO actualizado o error si no existe |
| `DELETE` | `/articulos/{id}` | Elimina por ID | Mensaje de resultado |

### Ejemplos de uso

Consultar todos los artículos:

```bash
curl http://localhost:8080/articulos
```

Consultar un artículo concreto:

```bash
curl http://localhost:8080/articulos/1
```

Crear un artículo (el nombre de la propiedad JSON debe coincidir con el que exponga el DTO; revisa la nota de compatibilidad de nombres en [Limitaciones y mejoras recomendadas](#limitaciones-y-mejoras-recomendadas)):

```bash
curl -X POST http://localhost:8080/articulos \
  -H "Content-Type: application/json" \
  -d '{"nombreArticulo":"Martillo","precio":12.50,"existencia":20}'
```

Actualizar un artículo:

```bash
curl -X PUT http://localhost:8080/articulos/1 \
  -H "Content-Type: application/json" \
  -d '{"nombreArticulo":"Martillo reforzado","precio":15.00,"existencia":18}'
```

Eliminar un artículo:

```bash
curl -X DELETE http://localhost:8080/articulos/1
```

Los ejemplos representan el contrato esperado; la ejecución real puede requerir resolver primero la configuración de seguridad y la correspondencia del nombre del campo descritas más adelante.

## Cómo funciona una petición

### Ejemplo: listar artículos

1. El usuario pulsa **Listar Productos** en `index.html`.
2. JavaScript llama mediante jQuery a `GET /articulos`.
3. `ArticuloController.listar()` recibe la petición y delega en `ArticuloService.serv_consulta()`.
4. El servicio ejecuta `repo.findAll()`.
5. Spring Data JPA y Hibernate consultan la tabla de MySQL y construyen objetos `Articulos`.
6. `ArticuloMapper.toDTO()` convierte cada entidad en `ArticulosDTO`.
7. Spring serializa la lista como JSON y la devuelve al navegador.
8. `mostrarTabla(data)` genera una tabla HTML con ID, nombre, precio y existencias.

### Ejemplo: crear un artículo

1. El usuario abre el formulario de inserción y completa nombre, precio y existencia.
2. `insertarProducto()` construye un objeto JavaScript y envía una petición `POST` con contenido JSON.
3. `ArticuloController.insertaArticulo()` recibe el cuerpo como `ArticulosDTO`.
4. `ArticuloService.serv_inserta()` transforma el DTO en una entidad mediante `ArticuloMapper.toEntity()`.
5. `repo.save(articulo)` persiste el registro.
6. MySQL genera el ID si la inserción se ha completado correctamente.
7. El servicio convierte la entidad guardada de nuevo a DTO y el controlador lo devuelve.
8. La interfaz muestra un mensaje de éxito y presenta el artículo recibido.

El proceso de actualización es similar, pero primero busca el registro por ID y modifica sus campos. El borrado comprueba si existe el ID antes de eliminarlo.

## Interfaz web

La interfaz se encuentra en `src/main/resources/static/index.html`. Spring Boot sirve los recursos estáticos de esa carpeta, por lo que la página normalmente puede abrirse en `http://localhost:8080/`.

La página contiene:

- Una cabecera y una barra de navegación.
- Botones para listar, buscar, actualizar, eliminar e insertar artículos.
- Formularios que se generan dinámicamente.
- Una tabla de resultados.
- Mensajes de confirmación o error mediante SweetAlert2.
- Peticiones AJAX con jQuery hacia `http://localhost:8080/articulos`.

Los enlaces de navegación de Proveedores, Ventas, Usuarios, Almacén y Reportes son actualmente marcadores (`href="#"`); no representan módulos funcionales implementados.

## Configuración y ejecución

### Requisitos

- JDK compatible con **Java 25**, de acuerdo con `pom.xml`.
- MySQL accesible, o Docker con Docker Compose.
- Terminal o IDE compatible con Maven.

### Configurar MySQL local

`application.properties` contiene la configuración del puerto `8080`, el driver de MySQL y propiedades JPA, pero las propiedades `spring.datasource.url`, `username` y `password` están comentadas. Para ejecutar la aplicación fuera de Docker, configura una conexión válida. Por ejemplo, en un entorno local:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/db_ferreteria
spring.datasource.username=TU_USUARIO
spring.datasource.password=TU_PASSWORD
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.hibernate.naming.physical-strategy=org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl
```

Sustituye las credenciales por las de tu entorno. No publiques contraseñas reales en Git.

### Compilar y arrancar

Desde la carpeta `ferreteria/`:

En Linux/macOS:

```bash
./mvnw clean package
./mvnw spring-boot:run
```

En Windows:

```bat
mvnw.cmd clean package
mvnw.cmd spring-boot:run
```

Si MySQL está configurado y accesible, abre `http://localhost:8080/`.

## Ejecución con Docker

El archivo `docker-compose.yml` define dos servicios:

- **`mysql`**: usa MySQL 8.0, crea la base de datos `db_ferreteria`, publica el puerto `3306` y monta `init.sql` para la inicialización.
- **`app`**: construye la imagen con el `Dockerfile`, publica el puerto `8080` y utiliza variables de entorno para conectarse al host de base de datos `mysql`.

Desde `ferreteria/`, compila primero el JAR, ya que el `Dockerfile` espera encontrar `target/ferreteria-0.0.1-SNAPSHOT.jar`:

```bash
./mvnw clean package -DskipTests
docker compose up --build
```

En Windows, sustituye `./mvnw` por `mvnw.cmd`.

Después, abre `http://localhost:8080/`. Para detener los contenedores:

```bash
docker compose down
```

Para borrar también los datos persistidos en el volumen de MySQL (acción destructiva):

```bash
docker compose down -v
```

**Importante:** las variables de ejemplo del `docker-compose.yml` (`tusuario` y `123`) son credenciales de desarrollo y deben cambiarse antes de cualquier despliegue real. Además, `depends_on` establece el orden de arranque, pero no garantiza por sí solo que MySQL ya esté listo para aceptar conexiones cuando arranca Spring Boot. Se recomienda configurar un `healthcheck` y una política de espera/reintento.

## Pruebas

El proyecto incluye `FerreteriaApplicationTests`, con una prueba `contextLoads()` destinada a comprobar que el contexto de Spring puede arrancar. No se observan pruebas específicas de las operaciones CRUD, del controlador, del servicio ni de integración con MySQL en los archivos revisados.

Ejecutar las pruebas:

```bash
./mvnw test
```

## Limitaciones y mejoras recomendadas

Los siguientes puntos se identifican a partir del código actual; conviene resolverlos antes de considerar la aplicación lista para producción.

1. **Configuración de Spring Security.** `pom.xml` incluye `spring-boot-starter-security`, pero no se observa una configuración de seguridad propia. La configuración automática de Spring Security puede exigir autenticación y proteger las rutas, lo que puede impedir que la interfaz AJAX funcione tal como está. Hay que definir explícitamente las reglas de acceso y el método de autenticación deseado. No es recomendable desactivar la seguridad indiscriminadamente en un entorno público.
2. **Nombre del campo en el DTO.** `ArticulosDTO` declara `getNombreAticulo()` y `setNombreAticulo()` (con una errata en “Articulo”). Jackson suele deducir de esos métodos una propiedad `nombreAticulo`, mientras que el HTML envía `nombrearticulo` en minúsculas. Esa diferencia puede impedir que el nombre se deserialice como se espera. Conviene corregir el nombre de los métodos a `getNombreArticulo()` y `setNombreArticulo()` y enviar `nombreArticulo` de forma consistente, o fijar explícitamente el nombre JSON con anotaciones.
3. **Manejo de errores HTTP.** El servicio lanza `RuntimeException` cuando no encuentra un artículo, pero no existe un manejador global de excepciones. Es preferible devolver estados HTTP coherentes (`404 Not Found`, `400 Bad Request`, etc.) mediante excepciones específicas y `@ControllerAdvice`.
4. **Validación de entrada.** No se aprecian validaciones para impedir nombres vacíos, precios negativos o existencias negativas. Se pueden incorporar anotaciones de Bean Validation y `@Valid`.
5. **Tipos monetarios.** Para dinero, `BigDecimal` suele ser más apropiado que `Double`, evitando imprecisiones binarias en cálculos decimales.
6. **CORS y seguridad.** El controlador usa `@CrossOrigin(origins = "*")`. Si la interfaz se sirve desde la misma aplicación, puede que no sea necesario permitir cualquier origen. En producción debe limitarse a los orígenes autorizados y coordinarse con la configuración de seguridad.
7. **Credenciales y configuración por entorno.** Las credenciales no deben almacenarse en archivos versionados. Se recomienda usar variables de entorno o un gestor de secretos.
8. **Consistencia del esquema.** La propiedad `spring.jpa.database-platrform` parece tener un error tipográfico; debe revisarse si se pretendía configurar `spring.jpa.database-platform`. La configuración también repite `spring.jpa.show-sql=true`. Como Hibernate usa `ddl-auto=update`, conviene evaluar migraciones controladas para evitar cambios de esquema inesperados.
9. **Arranque de la aplicación.** `FerreteriaApplication` consulta todos los artículos al iniciar y los imprime en consola. Es útil para desarrollo, pero puede generar mucho registro y cargar innecesariamente todos los datos cuando la tabla crezca.
10. **Interfaz y experiencia de uso.** Los formularios no muestran validaciones completas; tras eliminar un artículo no se actualiza automáticamente la tabla y los enlaces de otras secciones todavía no implementan navegación funcional.
11. **Pruebas automatizadas.** Añadir pruebas unitarias del servicio y pruebas de integración de los endpoints ayudaría a verificar altas, consultas, cambios, borrados y casos de error.
12. **Compatibilidad de versiones.** El proyecto declara Spring Boot `4.0.1`, Java `25` y una dependencia de Spring Security con versión `3.3.4`. Conviene comprobar la compatibilidad entre las versiones de los starters y mantener una línea de versiones coherente.

## Resumen

Este repositorio implementa una aplicación básica de inventario de ferretería con una interfaz web y una API REST para gestionar artículos. Su principal valor didáctico es mostrar la separación por capas entre controlador, servicio, DTO, mapper, repositorio y entidad, junto con la persistencia relacional mediante Spring Data JPA y MySQL. Para evolucionar hacia una aplicación más robusta, las prioridades serían resolver la correspondencia del campo del nombre, definir correctamente la seguridad y los errores HTTP, añadir validaciones y pruebas, y externalizar la configuración sensible.
