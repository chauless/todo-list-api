# Content

- [Content](#content)
- [Application](###application)
- [Checklist](###checklist)
- [Design patterns](###design-patterns)
- [Deployment and initialization](###deployment-and-initialization)

### Application

**Todo list / Task tracker**

A multiuser task scheduler. Users can use it as a TODO sheet. The source of inspiration for the project is Trello.

**Microservices**

A microservice approach was used in the project development. The project includes 3 microservices:

- [todo-list-api](https://github.com/chauless/todo-list-api)
- [todo-list-email-sender](https://github.com/chauless/todo-list-email-sender)
- [todo-list-scheduler](https://github.com/chauless/todo-list-scheduler)

**[todo-list-api](https://github.com/chauless/todo-list-api)**

Spring Boot application that implements REST API for working with users and tasks.
Granting access to the functionality is implemented using JWT authentication.

Working with users:

- Registration
- Authorization

Working with tasks (available only for authorized users):

- Creation
- Reading
- Editing (Changing title and description, marking task as done)
- Deleting

**[todo-list-email-sender](https://github.com/chauless/todo-list-email-sender)**

Spring Boot application with two modules - Spring Mail and Spring AMQP.

With Spring AMQP, the application connects to RabbitMQ and creates a queue, then subscribes to messages received from the scheduler and backend service.

For each received message, whose contents are deserialized into a model instance, the Spring Mail module is used to send an email.

**[todo-list-scheduler](https://github.com/chauless/todo-list-scheduler)**

Spring Boot application with two modules - Spring Scheduler and Spring AMQP.

The task of the service is to iterate all users once a day, generate reports for them about the day, and generate emails for sending. The generated emails are sent to the RabbitMQ queue.

### Checklist

| Requirement                                    | State | Description                                                                                                         |
| ---------------------------------------------- | ----- | ------------------------------------------------------------------------------------------------------------------- |
| Selection of suitable technology and language  | ✅    | Java/SpringBoot technology used                                                                                     |
| Readme in git with description of what is done | ✅    |                                                                                                                     |
| Use of a common DB                             | ✅    | PostgreSQL database used, ORM Hibernate                                                                             |
| Use of cache                                   | ✅    | Hazelcast (TaskService.java)                                                                                        |
| Use of messaging principle                     | ✅    | rabbitmq                                                                                                            |
| Security                                       | ✅    | JWT                                                                                                                 |
| Use of Interceptors                            | ✅    | CustomInterceptor.java (a request arrives and we write it to the log)                                               |
| Use of REST                                    | ✅    | API communication via REST                                                                                          |
| Deployment on a production server e.g. Heroku  | ✅    | AKS Load Balancer                                                                                                   |
| Selection of suitable architecture             | ✅    | Microservices architecture used                                                                                     |
| Initialization procedure                       | ✅    | Implemented, [described in documentation](https://github.com/JustTheWord/todo-list-iac/tree/main)                   |
| Use of Elasticsearch                           | ✅    | Implemented, [described in documentation](https://github.com/JustTheWord/todo-list-iac/tree/main/k8s-manifests/efk) |
| Use of at least 5 design patterns              | ✅    | builder, template, strategy, chain of responsibility                                                                |
| 2 use cases for each team member               | ✅    | Done                                                                                                                |
| Cloud services                                 | ✅    | Azure Cloud, Azure Kubernetes Service, Azure Container Registry                                                     |

### Design patterns

- Chain of responsibility / SecurityConfig / todo-list-api
- Builder / RegisterRequest / todo-list-api
- Template / AbstractAuthenticationService / todo-list-api
- Strategy / SummaryStrategy / todo-list-scheduler
- Chain of responsibility / MessageProcessorService / todo-list-email-sender

### Deployment and initialization

All deployment information can be found separately [here](https://github.com/JustTheWord/todo-list-iac/tree/main)
