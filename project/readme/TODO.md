## Versions
### 2.0
+ rework frontend
    - rework AccountingPanel + ItemService
    - rework initMenuBar
    - check GlobalPanel func.
    - try DayPreview without fixed size
    - check DayPreview func.
    - check former MonthPanel func.        
    
+ (prerobit android appku(an.19) a tu pridat moznost importovania z nej
+ solve importing data from version 1.y
+ dorobit front-end
    - design & impl. day preview + monthPanel(name of days in week)
    - check all user inputs (in dialogs) + react to them (maybe beans or something)
+ check ServiceFailureEx. in AppFrame + react to it (event-thread)
+ solve TODOs
+ release:
    - Scheduler-2.0.jar -> project/production
    - git release workflow(branch/tag/merge) + check documentation and code adjustment (unnecessary blank rows, spaces, etc.) 
    - update ABOUT_VERSION

### 2.1 
+ analytics
    - impl. & desing graphs etc.

### 2.x
+ impl. & design Tasks
    - design & impl. day schedule panel
    - design & impl. add/edit/del task in day panel
    - vyriesit pridavanie opakujucich sa taskov (napr. menu-Task-Add Repeating Task-zaskrtnut dni(zo selectnuteho mesiaca) kedy)
    - design & impl. global panel (including month control)
+ migrate strings(commands, messages, ...), colors, ... to commons/Colors Strings.Message Strings.Command etc. <- better management

## Ideas
- dorobit dalsie veci ako edit month, html documentation,...
- moznost exportovat tasky do img/pdf na vytlacenie
- lokalizovat commands, messages
- automatizovane testy
- more features + their management


## Bugs
+ (NEW) DescriptionPanel repaint bug (while moving scrollbar) 
    - possible solution: repaint method to UI (like in pc4idea)





