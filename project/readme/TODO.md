## Versions
### 2.0
+ solve TODOs
+ nejake ikonky pre menu
+ release:
    - Scheduler-2.0.jar -> project/production
    - git release workflow(branch/tag/merge) + inspect code + check documentation and code adjustment (unnecessary blank rows, spaces, etc.)
    - update ABOUT_VERSION

### 2.1 
+ analytics
    - impl. & desing graphs etc.
+ edit months (order, edit name, delete)
+ solve l&f bug

### 2.x
+ impl. & design Tasks
    + (maybe migrate to andr)
        - desktop widget 
        - or just retrieve data from gcalendar/gtasks for stats
    - design & impl. day schedule panel
    - design & impl. add/edit/del task in day panel
    - vyriesit pridavanie opakujucich sa taskov (napr. menu-Task-Add Repeating Task-zaskrtnut dni(zo selectnuteho mesiaca) kedy)
    - design & impl. global panel (including month control)
    
+ check all user inputs (in dialogs) + react to them (maybe beans or something)
+ migrate strings(commands, messages, ...), colors, ... to commons/Colors Strings.Message Strings.Command etc. <- better management
+ impl. user statistics + improve logging (more info)
+ documentation/user guide

## Ideas
- moznost exportovat tasky do img/pdf na vytlacenie
- lokalizovat commands, messages
- automatizovane testy
- more features + their management


## Bugs
+ (NEW) l&f problems:
    - settings-types-DescriptionPanel repaint bug (while moving scrollbar)  
    -  alignments 
    -  "AWT-EventQueue-0" java.lang.NullPointerException after schema change





