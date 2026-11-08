# Framework de Automatización PetStore Rest Assured

Framework de automatización de pruebas de API para la [API REST Petstore](https://petstore.swagger.io/) construido con Rest Assured, Java y TestNG, siguiendo un diseño en capas (constructor de peticiones → modelos → pruebas).

## Descripción general

Este proyecto automatiza seis escenarios principales contra la API Petstore:

- **Obtener mascota por ID**: recuperar una mascota por su identificador y validar el esquema y los datos de la respuesta.
- **Obtener mascotas por estado**: consultar mascotas filtradas por estado (`available`, `pending`, `sold`) y validar el contenido de la lista.
- **Crear orden**: registrar una orden en la tienda y verificar la confirmación de la API.
- **Crear usuario**: registrar un nuevo usuario y verificar la respuesta de la API.
- **Login de usuario**: autenticar con credenciales válidas y validar la respuesta de la sesión.
- **Logout de usuario**: finalizar la sesión y validar la confirmación del cierre.

## Stack tecnológico

| Herramienta | Versión | Propósito |
|---|---|---|
| Java | 17 | Lenguaje de programación |
| Maven | 3.x | Compilación y gestión de dependencias |
| Rest Assured | 5.x | Pruebas de API REST |
| TestNG | 7.10.2 | Ejecutor de pruebas y anotaciones |
| Jackson | 2.x | Serialización/deserialización JSON |

## Estructura del proyecto

```
PetStoreRestAssured/
├── src/
│   └── test/java/com/globant/automation/
│       ├── config/
│       │   └── TestRunner.java       # Setup de la suite, carga de config.properties
│       ├── model/                    # POJOs mapeados desde/hacia JSON
│       │   ├── ApiResponse.java
│       │   ├── Category.java
│       │   ├── Order.java
│       │   ├── Pet.java
│       │   ├── Tag.java
│       │   └── User.java
│       ├── request/
│       │   └── RequestBuilder.java   # Especificación de peticiones Rest Assured
│       └── test/
│           ├── GetPetByIdTest.java
│           ├── GetPetsByStatusTest.java
│           ├── CreateOrderTest.java
│           ├── CreateUserTest.java
│           ├── LoginUserTest.java
│           └── LogoutUserTest.java
├── src/test/resources/
│   └── config.properties             # Configuración de la URL base
├── testng.xml
├── pom.xml
└── .gitignore
```

## Decisiones de diseño

- **Arquitectura en capas**: `RequestBuilder` centraliza la especificación de Rest Assured (URI base, content type, logging); los POJOs en `model/` manejan el mapeo JSON; las clases de pruebas contienen solo aserciones y validaciones de negocio.
- **URL base externalizada**: la URL de la API objetivo se lee de `src/test/resources/config.properties`, manteniendo los entornos configurables sin cambios de código.
- **Validación declarativa**: las respuestas se deserializan a POJOs y se validan con aserciones de TestNG para obtener mensajes de fallo legibles.

## Cómo ejecutar

Clona el repositorio y ejecuta:

```bash
mvn clean test
```

### Prerrequisitos

- JDK 17 o superior
- Maven 3.6+
- Acceso a internet hacia `https://petstore.swagger.io/v2`

## Pipeline CI/CD

La suite corre automáticamente en GitHub Actions en cada push/PR a `main`,
manualmente (workflow_dispatch) y **a diario a las 06:00 UTC (01:00 Colombia)**
vía cron. Cada ejecución produce:

| Artefacto | Descripción |
|---|---|
| `surefire-reports-api` | Reportes XML de Surefire |
| `allure-results-api` | Resultados crudos de Allure |
| `allure-report-api` | Reporte HTML navegable de Allure |

### Publicación automática al dashboard

Después de cada ejecución (incluso con fallos), el pipeline parsea los XML de
Surefire y publica los resultados consolidados en
[Proyecto-grado-dashboard](https://github.com/AngelicaJuliethSP/Proyecto-grado-dashboard),
que alimenta el dashboard público de QA:
**https://proyecto-grado-dashboard.streamlit.app**

Esto hace que el dashboard se actualice de forma **100 % automática** — sin
pasos manuales. Requiere el secreto `DASHBOARD_TOKEN` (PAT fine-grained con
acceso de escritura al repo del dashboard). Si el secreto no existe, el paso
se omite de forma controlada y la ejecución no se ve afectada.

## Autora

Angélica Julieth Silva Pérez — College Trainee, Globant
