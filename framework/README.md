# io.thorib.dashboard

Este proyecto implementa el use case "Dashboard" utilizando la arquitectura propuesta por el framework Thoribio.

## Estructura del Proyecto

La estructura del proyecto se organiza en los siguientes módulos:

Estructura del proyecto
```plantuml
@startuml
!define AWSPUML https://raw.githubusercontent.com/awslabs/aws-icons-for-plantuml/v14.0/Legacy/


package root as "rootProject"
package app
package core
package "dashboard-domain" as domain
package "dashboard-data" as data {
    package "dashboard-service" as service
    package "dashboard-repository" as repository
}
package "dashboard-presentation" as presentation {
    package "dashboard-statereducer" as statereducer
    package "dashboard-sideeffects" as sideeffects
}

root --> app
root --> core
root --> domain
root --> data
root --> presentation
data --> service
data --> repository
presentation --> statereducer
presentation --> sideeffects
@enduml
```
## Diagrama de Clases

El diagrama de clases para el use case "Dashboard" es el siguiente:

Diagrama de clases
```plantuml
@startuml
!define AWSPUML https://raw.githubusercontent.com/awslabs/aws-icons-for-plantuml/v14.0/Legacy/

class DashboardFeature << (F, #FFB648) >> {
  +use(action: () -> DashboardAction): Boolean
  +state: StateFlow<DashboardState>
  +stop()
}

class DashboardStateReducer << (R, #FFB648) >> {
  -reduce(action: DashboardAction, state: DashboardState): DashboardState
}

class DashboardSideEffect << (S, #FFB648) >> {
  -execute(action: DashboardAction)
}

class DashboardService << (S, #FFB648) >> {
  -use(action: DashboardAction): Result<DashboardState>
}

class DashboardRepository << (R, #FFB648) >> {
  -use(action: DashboardAction): DashboardDTO
}

DashboardFeature --> DashboardStateReducer: Uses
DashboardFeature --> DashboardService: Uses
DashboardService --> DashboardRepository: Uses
DashboardStateReducer --> DashboardSideEffect: Executes side effects

note right of DashboardFeature
  The DashboardFeature is the main
  entry point for the Dashboard
  use case. It handles actions,
  updates state, and manages
  the flow of data.
end note

note right of DashboardStateReducer
  The DashboardStateReducer is
  responsible for updating the
  state based on the provided
  action.
end note

note right of DashboardSideEffect
  The DashboardSideEffect is used
  to handle side effects that can
  occur during state transitions,
  such as logging, analytics or
  navigation.
end note

note right of DashboardService
  The DashboardService is responsible
  for interacting with the data layer,
  calling the appropriate repository
  methods based on the provided action.
end note

note right of DashboardRepository
  The DashboardRepository is responsible
  for fetching data from remote or local
  sources based on the provided action.
end note

@enduml

```
## Diagrama de Secuencia

El diagrama de secuencia para el use case "Dashboard" es el siguiente:

Diagrama de secuencia
```plantuml
@startuml

actor User
participant DashboardFeature as DF
participant DashboardAction as DA
participant DashboardStateReducer as DSR
participant DashboardSideEffect as DSE
participant DashboardService as DS
participant DashboardRepository as DR

User -> DF : send(action: LoadDashboard)
activate DF

DF -> DA : Create LoadDashboard action
activate DA
DA --> DF : Return LoadDashboard action
deactivate DA

DF -> DSR : reduce(LoadDashboard, currentState)
activate DSR

DSR -> DSE : execute(LoadDashboard)
activate DSE
note right of DSE
  Side effects can include
  logging, analytics or
  navigation events, like
  showing a loading screen.
end note
DSE --> DSR : Side effect executed
deactivate DSE

DSR --> DF : Return new state (Loading)
deactivate DSR

DF -> DS : use(LoadDashboard)
activate DS

DS -> DR : use(LoadDashboard)
activate DR

note right of DR
  The repository fetches data
  from remote or local sources.
end note

DR --> DS : Return DashboardDTO
deactivate DR

note right of DS
  The service maps the DTO
  to a new Dashboard state.
end note

DS --> DF : Return new state (Success)
deactivate DS

note right of DF
  The feature updates the state
  and notifies the UI.
end note

deactivate DF

@enduml
```
