## Versions
### 2.1 
+ analytics
    - impl. & desing graphs
    - fits frame and his comps
    - document it

+ edit months (order, edit name, delete)
    - this will need sorted months via order in MonthService.getMonthsOrder method
+ some icons for menu (also arrows)
+ solve l&f bug
+ impl. user statistics + improve logging (more info)

### 2.x
+ impl. & design Tasks
    + (maybe migrate to andr)
        - desktop widget 
        - or just retrieve data from gcalendar/gtasks/trello for stats
    - design & impl. day schedule panel
    - design & impl. add/edit/del task in day panel
    - vyriesit pridavanie opakujucich sa taskov (napr. menu-Task-Add Repeating Task-zaskrtnut dni(zo selectnuteho mesiaca) kedy)
    - type,desc -> title,desc.,type,subtype
    - design & impl. global panel (including month control)
    
+ check all user inputs (in dialogs) + react to them (maybe beans or something/hide+check+(dispose/show again))
+ migrate strings(commands, messages, ...), colors, ... to commons/Colors Strings.Message Strings.Command etc. <- better management
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





