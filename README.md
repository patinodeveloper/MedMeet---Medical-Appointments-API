# Sistema de Citas Médicas - API Backend

Este repositorio contiene el backend de un sistema de citas médicas desarrollado con **Spring Boot**. La API sigue una arquitectura RESTful, permitiendo la gestión de pacientes, doctores, especialidades, horarios y citas médicas. El frontend de este sistema está desarrollado con **React.js** y **Bootstrap**, y se encuentra en otro repositorio: [Frontend MedMeet](https://github.com/patinodeveloper/MedMeet-FrontEnd).

## Módulos del Sistema

La API gestiona los siguientes módulos:

- **Pacientes**: Permite gestionar los datos de los pacientes.
- **Doctores**: Gestiona la información de los médicos disponibles.
- **Especialidades**: Cada doctor puede tener asignadas una o más especialidades.
- **Horarios**: Gestiona los horarios disponibles de los doctores (actualmente en desarrollo).
- **Citas**: Permite la creación, modificación y gestión de las citas médicas.

## Características Técnicas

- **Spring Boot**: Utilizado para desarrollar la API con arquitectura REST.
- **JPA y Hibernate**: Para la interacción con la base de datos relacional (MySQL).
- **MySQL**: Base de datos relacional utilizada para almacenar la información de pacientes, doctores, especialidades, horarios y citas.
- **Swagger**: Se ha integrado Swagger para la documentación de la API, facilitando la interacción con los endpoints.
- **Dependencias**: Se utilizan varias dependencias de Spring, como Spring Web, Spring Data JPA, y MySQL Driver.

## Diagrama de Arquitectura

A continuación se muestra un diagrama de la arquitectura de la aplicación, que ilustra la interacción entre el frontend, la API y la base de datos:

![Diagrama de Arquitectura](https://res.cloudinary.com/dtncgfvxw/image/upload/v1734647154/Diagrama_de_arquitectura_-_MedMeet_oiyqwr.png)

## Estructura de la Base de Datos

El proyecto utiliza una base de datos MySQL. El dump de la base de datos se encuentra en el directorio `resources/sql/dump.sql`. Este archivo contiene la estructura inicial de las tablas necesarias para la API.

## Endpoints Principales

Los siguientes son algunos de los principales endpoints disponibles en la API. Las rutas de la API están estructuradas usando `@RequestMapping` para establecer la base común (`/medmeet/api/v1`) y luego cada módulo o sección tiene su propia ruta específica. Por ejemplo, para acceder a los **appointments (citas médicas)**, la ruta completa es `/medmeet/api/v1/appointments`.

- **GET** `/medmeet/api/v1/patients` - Obtener todos los pacientes.
- **POST** `/medmeet/api/v1/patients` - Crear un nuevo paciente.
- **GET** `/medmeet/api/v1/doctors` - Obtener todos los doctores.
- **POST** `/medmeet/api/v1/doctors` - Crear un nuevo doctor.
- **GET** `/medmeet/api/v1/specialties` - Obtener todas las especialidades.
- **POST** `/medmeet/api/v1/appointments` - Crear una nueva cita médica.

### Estructura de las Rutas

Todas las rutas de la API siguen la siguiente estructura base:
- **Base de la ruta**: `/medmeet/api/v1/`  
  Este es el prefijo común a todas las rutas, definido con la anotación `@RequestMapping("/medmeet/api/v1")`.
  
  Luego, se añaden los módulos específicos como `patients`, `doctors`, `appointments`, entre otros, para formar la ruta completa.

## Instalación

Para instalar y ejecutar la API en tu máquina local, sigue estos pasos:

1. Clona el repositorio:
```
  git clone https://github.com/patinodeveloper/MedMeet---Medical-Appointments-API.git
```

2. Configura tu base de datos MySQL y asegura que las credenciales estén correctas en el archivo `application.properties`.

3. Ejecuta la aplicación usando Maven o el IDE de tu preferencia (yo utilicé IntelliJ):
```
  mvn spring-boot:run
```

4. Accede a la API en `http://localhost:8080`.
