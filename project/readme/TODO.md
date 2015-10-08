## Versions
### 2.0
+ dorobit Tasky
    - design & impl. day schedule
    - design & impl. add/edit/del task in day panel
+ (prerobit android appku(verzie pre moj,ludkin mobil+tablet)) a tu pridat moznost importovania z nej
+ solve importing data from version 1.y
+ dorobit front-end
    - vyriesit pridavanie opakujucich sa taskov (napr. menu-Task-Add Repeating Task-zaskrtnut dni(zo selectnuteho mesiaca) kedy)
    - design & impl. day preview + monthPanel(name of days in week)
    - design & impl. global panel (including month control)
    - check all user inputs!!! + react to them (maybe beans or something)
+ migrate code from GuiModel to Service (Gui Model as simple as possible + rename to config)
    - popripade rozdelit services na viac interface (a vysledna Service trieda ich bude impl + ju bude vyuzivat gui)
+ refact. action to classes (if/where possible) + tieto akcie budu pouzivat Service, Config(zmena spusti akciu, napr.DAY_UPDATED)
+ migrate strings(commands, messages, ...), colors, ... to commons/Colors Strings.Message Strings.Command etc. <- better management
+ check ServiceFailureEx. in AppFrame + react to it (event-thread)
+ solve all TODOs
+ release:
    - Scheduler-2.1.jar -> project/production
    - git release workflow(branch/tag/merge) + check documentation and code adjustment (unnecessary blank rows, spaces, etc.) 
    - update ABOUT_VERSION

### 2.1
+ ...

## Ideas
- dorobit dalsie veci ako edit month, html documentation,...
- moznost exportovat tasky do img/pdf na vytlacenie
- lokalizovat commands, messages
- automatizovane testy

## Bugs
+ DescriptionPanel repaint bug (while moving scrollbar) 
    - possible solution: repaint method to UI (like in pc4idea)
+ create Month locale (day, month names) + switch year and month name (aug 2015 -> 2015 aug)



