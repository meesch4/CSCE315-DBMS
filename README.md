# CSCE315-DBMS
Project 1 Database Management System for CSCE 315

## ANTLR
I already included the ANTLR runtime jar in the lib/ folder, so you really just need to install the ANTLR plugin
in IntelliJ. 

To do that, go to File->Settings(or Ctrl+Alt+S)->Plugins, then search for ANTLR v4 grammar plug in.

For learning basic ANTLR and how it works(as well as plug in stuff), go to this video that Paul provided us: https://www.youtube.com/watch?v=svEZtRjVBTY

## Phase 2 Roles
1) Parser
    - Creating grammar(ANTLR) and regex for SQL code
    - Parsing the returned Parse Tree from ANTLR into actual actions for the database (part of Internal DB as well)
2) Internal Database
    - Actually executing the parse tree given by the Parser and acting on the database accordingly
    - Determining + using data structure for storing table data
3) Saving table data to XML
    - Given internal storage of a table, save it to an XML file
    - Given XML file, load it into the Internal Database
